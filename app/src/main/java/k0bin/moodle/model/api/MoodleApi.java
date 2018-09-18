package k0bin.moodle.model.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import k0bin.moodle.model.MoodleException;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class MoodleApi {
    @NonNull
    private final String siteUrl;
    @NonNull
    private final Gson gson;
    @NonNull
    private final OkHttpClient okHttpClient;

    public MoodleApi(@NonNull String siteUrl) {
        this.siteUrl = siteUrl;
        this.gson = new GsonBuilder().create();
        okHttpClient = new OkHttpClient.Builder().build();
    }

    @NonNull
    private <T> Request buildAjaxRequest(int index, @NonNull String method, @Nullable T args) {
        final Request.Builder builder = new Request.Builder();
        builder.url(siteUrl + "/lib/ajax/service.php");
        String parametersJson;
        if (args != null) {
            AjaxParameters[] parameters = new AjaxParameters[] { new AjaxParameters<>(0, method, args) };
            parametersJson = gson.toJson(parameters, new TypeToken<AjaxParameters<T>[]>() {}.getType());
        } else {
            AjaxParameters[] parameters = new AjaxParameters[] { new AjaxParameters<>(0, method, new Object()) };
            parametersJson = gson.toJson(parameters, new TypeToken<AjaxParameters<Object>[]>() {}.getType());
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parametersJson);
        builder.method("POST", body);
        return builder.build();
    }

    @NonNull
    private <T> Request buildRestRequest(@NonNull String method, @Nullable T args, @Nullable HashMap<String, String> queryParameters) {
        final Request.Builder builder = new Request.Builder();
        final HttpUrl url = HttpUrl.parse(siteUrl + "/webservice/rest/server.php");
        final HttpUrl.Builder urlBuilder = new HttpUrl.Builder();
        urlBuilder.scheme(url.scheme())
        .host(url.host())
        .port(url.port())
        .addEncodedPathSegments("webservice/rest/server.php")
        .setQueryParameter("wsfunction", method)
        .setQueryParameter("moodlewsrestformat", "json");
        if (queryParameters != null) {
            for (HashMap.Entry<String, String> entry : queryParameters.entrySet()) {
                urlBuilder.setQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        final HttpUrl urlWithParams = urlBuilder.build();

        builder.url(urlWithParams);
        RequestBody body;
        if (args != null) {
            String parametersJson = gson.toJson(args, new TypeToken<T>() {}.getType());
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parametersJson);
            builder.method("POST", body); //TODO
        }
        return builder.build();
    }

    @Nullable
    private <T, R> R callAjax(@NonNull Type resultType, @NonNull String method, @Nullable T args, @Nullable String token) throws IOException, MoodleException {
        final Request request = buildAjaxRequest(0, method, args);
        final Response response = okHttpClient.newCall(request).execute();
        if (response.body() == null) {
            return null;
        }

        final Type parameterizedType = TypeToken.getParameterized(
                List.class,
                TypeToken.getParameterized(AjaxResponse.class, resultType).getType()
        ).getType();
        final List<AjaxResponse<R>> ajaxResponse = gson.fromJson(response.body().charStream(), parameterizedType);
        if (ajaxResponse != null && ajaxResponse.size() > 0) {
            return ajaxResponse.get(0).getData();
        } else {
            return null;
        }
    }

    @Nullable
    private <T, R> R callRest(@NonNull Type resultType, @NonNull String method, @Nullable T body, @Nullable HashMap<String, String> queryParameters) throws IOException, MoodleException {
        final Request request = buildRestRequest(method, body, queryParameters);
        final Response response = okHttpClient.newCall(request).execute();
        if (response.body() == null) {
            return null;
        }

        String bodyText = response.body().string();

        if (!bodyText.startsWith("[")) {
            final RestError error = gson.fromJson(bodyText, RestError.class);
            if (!error.isSuccessful()) {
                throw MoodleException.parse(error);
            }
        }
        final R result = gson.fromJson(bodyText, resultType);
        if (result != null) {
            return result;
        }
        return null;
    }

    @Nullable
    public final PublicConfig getPublicConfig() throws IOException, MoodleException {
        return callAjax(PublicConfig.class, "tool_mobile_get_public_config", null, null);
    }

    public final SiteInfo getSiteInfo(@NonNull String token) throws IOException, MoodleException {
        final HashMap<String, String> args = new HashMap<>();
        args.put("wstoken", token);
        return callRest(SiteInfo.class, "core_webservice_get_site_info", null, args);
    }

    public final List<Course> getCourses(@NonNull String token, long userId) throws IOException, MoodleException {
        final HashMap<String, String> args = new HashMap<>();
        args.put("wstoken", token);
        args.put("userid", Long.toString(userId, 10));
        final Type parameterizedType = TypeToken.getParameterized(List.class, Course.class).getType();
        return callRest(parameterizedType, "core_enrol_get_users_courses", null, args);
    }

    public final CourseContents getCourseContents(@NonNull String token, long courseId) throws IOException, MoodleException {
        final HashMap<String, String> args = new HashMap<>();
        args.put("wstoken", token);
        args.put("courseid", Long.toString(courseId, 10));
        return callRest(CourseContents.class, "core_enrol_get_users_courses", null, args);
    }
}
