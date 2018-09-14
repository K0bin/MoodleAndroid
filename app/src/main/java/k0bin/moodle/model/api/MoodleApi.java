package k0bin.moodle.model.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class MoodleApi {
    private final String siteUrl;
    private final Gson gson;
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
    private <T> Request buildRestRequest(@NonNull String method, @Nullable T args, @Nullable String token) {
        final Request.Builder builder = new Request.Builder();
        final HttpUrl url = HttpUrl.parse(siteUrl + "/webservice/rest/server.php");
        final HttpUrl urlWithParams = new HttpUrl.Builder()
                .scheme(url.scheme())
                .host(url.host())
                .port(url.port())
                .addEncodedPathSegment("/webservice/rest/server.php")
                .setQueryParameter("wsfunction", method)
                .setQueryParameter("wsToken", token != null ? token : "")
                .setQueryParameter("moodlewsrestformat", "json")
                .build();

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
    private <T, R> R call(@NonNull Type resultType, @NonNull String method, boolean useAjax, @NonNull T args, @Nullable String token) {
        Request request = useAjax ? buildAjaxRequest(0, method, args) : buildRestRequest(method, args, token);
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.body() == null) {
                return null;
            }

            Type parameterizedType = TypeToken.getParameterized(
                    List.class,
                    TypeToken.getParameterized(AjaxResponse.class, resultType).getType()
            ).getType();
            List<AjaxResponse<R>> ajaxResponse = gson.fromJson(response.body().charStream(), parameterizedType);
            if (ajaxResponse != null && ajaxResponse.size() > 0) {
                return ajaxResponse.get(0).getData();
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    @Nullable
    public final PublicConfig getPublicConfig() {
        return call(PublicConfig.class, "tool_mobile_get_public_config", true, new Object(), null);
    }
}
