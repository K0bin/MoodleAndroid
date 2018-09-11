package k0bin.moodle.model.api.ajax;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoodleAjax {
    private MoodleAjaxService service;
    private static final AjaxParameters[] PUBLIC_CONFIG_PARAMETERS = new AjaxParameters[] {new AjaxParameters("tool_mobile_get_public_config")};

    public MoodleAjax(@NonNull String siteUrl) {
        if (!siteUrl.endsWith("/")) {
            siteUrl = siteUrl + "/";
        }

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(siteUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(MoodleAjaxService.class);
    }

    public Call<AjaxResponse<PublicConfig>[]> getPublicConfig() {
        return service.getPublicConfig(PUBLIC_CONFIG_PARAMETERS);
    }
}
