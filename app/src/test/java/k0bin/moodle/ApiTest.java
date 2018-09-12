package k0bin.moodle;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import k0bin.moodle.model.api.MoodleApi;
import k0bin.moodle.model.api.PublicConfig;

public class ApiTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void test() {
        MoodleApi moodleApi = new MoodleApi("https://moodle.thm.de");
        //moodleApi.getPublicConfig().observeForever(it -> System.out.println(it.toString()));
        PublicConfig config = moodleApi.getPublicConfig();
    }
}
