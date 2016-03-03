package com.soulkey.android.weather;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WTTestRunner.class)
public class WTAppTest {

    @After
    public void teardown() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testGetApplicationComponent() {
        // Mostly for code coverage..
        final WTApp app = (WTApp) RuntimeEnvironment.application;
        assertThat(app.getAppServicesComponent()).isNotNull();
    }
}
