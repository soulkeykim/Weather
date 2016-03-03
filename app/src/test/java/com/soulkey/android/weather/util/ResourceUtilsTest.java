package com.soulkey.android.weather.util;

import com.soulkey.android.weather.R;
import com.soulkey.android.weather.WTTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(WTTestRunner.class)
public class ResourceUtilsTest {

    @Test
    public void testConstructor() {
        // For code coverage..
        new ResourceUtils();
    }

    @Test
    public void testGetImageId() {
        assertThat(ResourceUtils.getImageId(RuntimeEnvironment.application, "10d")).isEqualTo(R.drawable.ic_10d);
        assertThat(ResourceUtils.getImageId(RuntimeEnvironment.application, "01d")).isEqualTo(R.drawable.ic_01d);
        assertThat(ResourceUtils.getImageId(RuntimeEnvironment.application, "50n")).isEqualTo(R.drawable.ic_50n);
    }
}
