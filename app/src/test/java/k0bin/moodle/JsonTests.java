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
import k0bin.moodle.model.api.Course;
import k0bin.moodle.model.api.PublicConfig;
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
        final String json = "{\n" +
                "\t\"wwwroot\": \"root\",\n" +
                "\t\"httpswwwroot\": \"httpsroot\",\n" +
                "\t\"sitename\": \"Testsite\",\n" +
                "\t\"guestlogin\", 0,\n" +
                "\t\"rememberusername\": 1,\n" +
                "\t\"registerauth\": \"hello world\",\n" +
                "\t\"forgottenpasswordurl\": \"https://givepassword\",\n" +
                "\t\"authinstructions\": \"Log in!\",\n" +
                "\t\"authnoneenabled\": 0,\n" +
                "\t\"enablewebservices\": 1,\n" +
                "\t\"enablemobilewebservice\": 1,\n" +
                "\t\"maintenanceenabled\": 0,\n" +
                "\t\"maintenancemessage\": \"Too bad\",\n" +
                "\t\"logourl\": \"https://logo\",\n" +
                "\t\"compactlogourl\": \"https://compactlogo\",\n" +
                "\t\"typeoflogin\": 12,\n" +
                "\t\"launchurl\": \"http://launchme\",\n" +
                "\t\"mobilecssurl\": \"https://css\",\n" +
                "\t\"tool_mobile_disabledfeatures\": \"no\",\n" +
                "\t\"warnings\": [\n" +
                "\t\t\"warning1\", \"warning2\"\n" +
                "\t]\n" +
                "}";

        final PublicConfig config = gson.fromJson(json, PublicConfig.class);
        Assert.assertEquals("root", config.getWwwRoot());
        Assert.assertEquals("httpsroot", config.getHttpsWwwRoot());
        Assert.assertEquals("Testsite", config.getSiteName());
        Assert.assertEquals(0, config.getGuestLogin());
        Assert.assertEquals(1, config.getRememberUsername());
        Assert.assertEquals("hello world", config.getRegisterAuth());
        Assert.assertEquals("https://givepassword", config.getForgottenPasswordUrl());
        Assert.assertEquals("Log in!", config.getAuthInstructions());
        Assert.assertEquals(0, config.getAuthNoneEnabled());
        Assert.assertEquals(1, config.getEnableWebServices());
        Assert.assertEquals(1, config.getEnableMobileWebService());
        Assert.assertEquals(0, config.getMaintenanceEnabled());
        Assert.assertEquals("Too bad", config.getMaintenanceMessage());
        Assert.assertEquals("https://logo", config.getLogoUrl());
        Assert.assertEquals("https://compactlogo", config.getCompactlogourl());
        Assert.assertEquals(12, config.getTypeOfLogin());
        Assert.assertEquals("http://launchme", config.getLaunchUrl());
        Assert.assertEquals("https://css", config.getMobileCssUrl());
        Assert.assertEquals("no", config.getToolMobileDisabledFeatures());
        Assert.assertArrayEquals(new String[] {
            "warning1", "warning2"
        }, config.getWarnings().toArray());

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
        Assert.assertEquals("Testsite", info.getSiteName());
        Assert.assertEquals("Testuser", info.getUserName());
        Assert.assertEquals("Test", info.getFirstName());
        Assert.assertEquals("User", info.getLastName());
        Assert.assertEquals("Test, User", info.getFullName());
        Assert.assertEquals("de", info.getLang());
        Assert.assertEquals(1L, info.getUserId());
        Assert.assertEquals("https://moodletest.com", info.getSiteUrl());
        Assert.assertEquals("https://moodletest.com/pic.jpg", info.getUserpictureurl());
        Assert.assertArrayEquals(new ApiFunction[] {
                new ApiFunction("func1", "1.1.1"),
                new ApiFunction("func2", "2.0.1")
        }, info.getFunctions().toArray());
        Assert.assertEquals(1, info.getDownloadfiles());
        Assert.assertEquals(1, info.getUploadfiles());
        Assert.assertEquals("3.4.2+ (Build: 20180502)", info.getRelease());
        Assert.assertEquals("2017111302.1", info.getVersion());
        Assert.assertEquals("", info.getMobileCssUrl());
        Assert.assertArrayEquals(new AdvancedFeature[] {
                new AdvancedFeature("usecomments", 1),
                new AdvancedFeature("usetags", 0)
        }, info.getAdvancedFeatures().toArray());
        Assert.assertTrue(info.userCanManageOwnFiles());
        Assert.assertEquals(25L, info.getUserQuota());
        Assert.assertEquals(104857600, info.getUserMaxUploadFileSize());
        Assert.assertEquals(0, info.getUserHomepage());
        Assert.assertEquals("gregorian", info.getSiteCalendarType());
        Assert.assertEquals("gregorian", info.getUserCalendarType());
    }

    @Test
    public void testRestError() {
        final String json = "{\"exception\":\"exception\",\"errorcode\":\"errorcode1\",\"message\":\"hello\"}";
        final RestError error = gson.fromJson(json, RestError.class);
        Assert.assertEquals("exception", error.getException());
        Assert.assertEquals("errorcode1", error.getErrorCode());
        Assert.assertEquals("hello", error.getMessage());
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

    @Test
    public void testCourse() {
        final String json = "{\n" +
                "\t\"id\": 13,\n" +
                "\t\"shortname\": \"test\",\n" +
                "\t\"fullname\": \"Test Kurs\",\n" +
                "\t\"enrolledusercount\": 500,\n" +
                "\t\"idnumber\": \"\",\n" +
                "\t\"visible\": 1,\n" +
                "\t\"summary\": \"Summary\",\n" +
                "\t\"summaryformat\": 1,\n" +
                "\t\"format\": \"topics\",\n" +
                "\t\"showgrades\": true,\n" +
                "\t\"lang\": \"\",\n" +
                "\t\"enablecompletion\": false,\n" +
                "\t\"category\": 517,\n" +
                "\t\"progress\": null,\n" +
                "\t\"startdate\": 1519081200,\n" +
                "\t\"enddate\": 0\n" +
                "}";
        final Course course = gson.fromJson(json, Course.class);
        Assert.assertEquals(13, course.getId());
        Assert.assertEquals("test", course.getShortName());
        Assert.assertEquals("Test Kurs", course.getFullName());
        Assert.assertEquals(500, course.getEnrolledUserCount());
        Assert.assertEquals("", course.getIdNumber());
        Assert.assertEquals(1, course.getVisible());
        Assert.assertEquals("Summary", course.getSummary());
        Assert.assertEquals(1, course.getSummaryFormat());
        Assert.assertEquals("topics", course.getFormat());
        Assert.assertTrue(course.getShowGrades());
        Assert.assertEquals("", course.getLang());
        Assert.assertFalse(course.getEnableCompletion());
        Assert.assertEquals(517, course.getCategory());
        Assert.assertNull(course.getProgress());
        Assert.assertEquals(1519081200, course.getStartDate());
        Assert.assertEquals(0, course.getEndDate());
    }
}
