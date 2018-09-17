package k0bin.moodle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

import k0bin.moodle.model.MoodleException;
import k0bin.moodle.model.api.AdvancedFeature;
import k0bin.moodle.model.api.AjaxResponse;
import k0bin.moodle.model.api.ApiFunction;
import k0bin.moodle.model.api.RestError;
import k0bin.moodle.model.api.SiteInfo;

public class JsonTests {
    private Gson gson;

    @Before
    public void prepareGson() {
        gson = new GsonBuilder().create();
    }

    @Test
    public void testPublicConfig() {

    }

    @Test
    public void testSiteInfo() {
        final String siteInfoJson = "{\n" +
                "   \"sitename\":\"Testsite\",\n" +
                "   \"username\":\"Testuser\",\n" +
                "   \"firstname\":\"Test\",\n" +
                "   \"lastname\":\"User\",\n" +
                "   \"fullname\":\"Test, User\",\n" +
                "   \"lang\":\"de\",\n" +
                "   \"userid\":1,\n" +
                "   \"siteurl\":\"https:\\/\\/moodletest.com\",\n" +
                "   \"userpictureurl\":\"https:\\/\\/moodletest.com/pic.jpg\",\n" +
                "   \"functions\":[\n" +
                "      {\n" +
                "         \"name\":\"func1\",\n" +
                "         \"version\":\"1.1.1\"\n" +
                "      },\n" +
                "      {\n" +
                "         \"name\":\"func2\",\n" +
                "         \"version\":\"2.0.1\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"downloadfiles\":1,\n" +
                "   \"uploadfiles\":1,\n" +
                "   \"release\":\"3.4.2+ (Build: 20180502)\",\n" +
                "   \"version\":\"2017111302.1\",\n" +
                "   \"mobilecssurl\":\"\",\n" +
                "   \"advancedfeatures\":[\n" +
                "      {\n" +
                "         \"name\":\"usecomments\",\n" +
                "         \"value\":1\n" +
                "      },\n" +
                "      {\n" +
                "         \"name\":\"usetags\",\n" +
                "         \"value\":0\n" +
                "      }\n" +
                "   ],\n" +
                "   \"usercanmanageownfiles\":true,\n" +
                "   \"userquota\":25,\n" +
                "   \"usermaxuploadfilesize\":104857600,\n" +
                "   \"userhomepage\":0,\n" +
                "   \"siteid\":1,\n" +
                "   \"sitecalendartype\":\"gregorian\",\n" +
                "   \"usercalendartype\":\"gregorian\"\n" +
                "}";
        final SiteInfo info = this.gson.fromJson(siteInfoJson, SiteInfo.class);
        Assert.assertEquals(info.getSiteName(), "Testsite");
        Assert.assertEquals(info.getUserName(), "Testuser");
        Assert.assertEquals(info.getFirstName(), "Test");
        Assert.assertEquals(info.getLastName(), "User");
        Assert.assertEquals(info.getFullName(), "Test, User");
        Assert.assertEquals(info.getLang(), "de");
        Assert.assertEquals(info.getUserId(), 1L);
        Assert.assertEquals(info.getSiteUrl(), "https://moodletest.com");
        Assert.assertEquals(info.getUserpictureurl(), "https://moodletest.com/pic.jpg");
        Assert.assertArrayEquals(info.getFunctions().toArray(), new ApiFunction[] {
                new ApiFunction("func1", "1.1.1"),
                new ApiFunction("func2", "2.0.1")
        });
        Assert.assertEquals(info.getDownloadfiles(), 1);
        Assert.assertEquals(info.getUploadfiles(), 1);
        Assert.assertEquals(info.getRelease(), "3.4.2+ (Build: 20180502)");
        Assert.assertEquals(info.getVersion(), "2017111302.1");
        Assert.assertEquals(info.getMobileCssUrl(), "");
        Assert.assertArrayEquals(info.getAdvancedFeatures().toArray(), new AdvancedFeature[] {
                new AdvancedFeature("usecomments", 1),
                new AdvancedFeature("usetags", 0)
        });
        Assert.assertTrue(info.userCanManageOwnFiles());
        Assert.assertEquals(info.getUserQuota(), 25L);
        Assert.assertEquals(info.getUserMaxUploadFileSize(), 104857600);
        Assert.assertEquals(info.getUserHomepage(), 0);
        Assert.assertEquals(info.getSiteCalendarType(), "gregorian");
        Assert.assertEquals(info.getUserCalendarType(), "gregorian");
    }

    @Test
    public void testRestError() {
        final String json = "{\"exception\":\"exception\",\"errorcode\":\"errorcode1\",\"message\":\"hello\"}";
        final RestError error = gson.fromJson(json, RestError.class);
        Assert.assertEquals(error.getException(), "exception");
        Assert.assertEquals(error.getErrorCode(), "errorcode1");
        Assert.assertEquals(error.getMessage(), "hello");
    }

    @Test(expected = MoodleException.InvalidTokenException.class)
    public void testTokenException() throws MoodleException {
        final String json = "{\"exception\":\"moodle_exception\",\"errorcode\":\"invalidtoken\",\"message\":\"Ung\\u00fcltiges Token - Token wurde nicht gefunden\"}";
        final RestError error = gson.fromJson(json, RestError.class);
        throw MoodleException.parse(error);
    }

    @Test
    public void testAjaxResponse() {
        final String json = "[{\"error\": true, \"data\": \"hello\"}]";
        final Type parameterizedType = TypeToken.getParameterized(
                List.class,
                TypeToken.getParameterized(AjaxResponse.class, String.class).getType()
        ).getType();
        final List<AjaxResponse<R>> ajaxResponse = gson.fromJson(json, parameterizedType);
        Assert.assertEquals(ajaxResponse.size(), 1);
        Assert.assertFalse(ajaxResponse.get(0).isSuccessful());
        Assert.assertEquals(ajaxResponse.get(0).getData(), "hello");
    }
}
