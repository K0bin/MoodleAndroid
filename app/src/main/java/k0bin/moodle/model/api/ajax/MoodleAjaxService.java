package k0bin.moodle.model.api.ajax;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MoodleAjaxService {
    @POST("lib/ajax/service.php")
    Call<AjaxResponse<PublicConfig>[]> getPublicConfig(@Body AjaxParameters[] parameters);
}
