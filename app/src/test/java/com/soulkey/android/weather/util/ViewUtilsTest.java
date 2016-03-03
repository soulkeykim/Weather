package com.soulkey.android.weather.util;

import android.view.View;

import com.soulkey.android.weather.WTTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WTTestRunner.class)
public class ViewUtilsTest {

    @Test
    public void testConstructor() {
        // For code coverage..
        new ViewUtils();
    }

    @Test
    public void testHideHandlesEmptyInput() {
        ViewUtils.hide();
    }

    @Test
    public void testHideHandlesNullInput() {
        ViewUtils.hide((View[]) null);
    }

    @Test
    public void testHideHandlesNullViewInput() {
        ViewUtils.hide((View) null);
    }

    @Test
    public void testHideHandlesOneViewInput() {
        final View view = new View(RuntimeEnvironment.application);
        view.setVisibility(View.VISIBLE);

        ViewUtils.hide(view);

        assertThat(view.getVisibility()).isEqualTo(View.GONE);
    }

    @Test
    public void testHideWithMultipleViews() {
        final View[] views = {
                new View(RuntimeEnvironment.application),
                new View(RuntimeEnvironment.application),
                new View(RuntimeEnvironment.application)
        };
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }

        ViewUtils.hide(views);

        for (View view : views) {
            assertThat(view.getVisibility()).isEqualTo(View.GONE);
        }
    }

    @Test
    public void testHideInvisibleHandlesEmptyInput() {
        ViewUtils.hideInvisible();
    }

    @Test
    public void testHideInvisibleHandlesNullInput() {
        ViewUtils.hideInvisible((View[]) null);
    }

    @Test
    public void testHideInvisibleHandlesNullViewInput() {
        ViewUtils.hideInvisible((View) null);
    }

    @Test
    public void testHideInvisibleHandlesOneViewInput() {
        final View view = new View(RuntimeEnvironment.application);
        view.setVisibility(View.VISIBLE);

        ViewUtils.hideInvisible(view);

        assertThat(view.getVisibility()).isEqualTo(View.INVISIBLE);
    }

    @Test
    public void testHideInvisibleWithMultipleViews() {
        final View[] views = {
                new View(RuntimeEnvironment.application),
                new View(RuntimeEnvironment.application),
                new View(RuntimeEnvironment.application)
        };
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }

        ViewUtils.hideInvisible(views);

        for (View view : views) {
            assertThat(view.getVisibility()).isEqualTo(View.INVISIBLE);
        }
    }

    @Test
    public void testShowHandlesEmptyInput() {
        ViewUtils.show();
    }

    @Test
    public void testShowHandlesNullInput() {
        ViewUtils.show((View[]) null);
    }

    @Test
    public void testShowHandlesNullViewInput() {
        ViewUtils.show((View) null);
    }

    @Test
    public void testShowHandlesOneViewInput() {
        final View view = new View(RuntimeEnvironment.application);
        view.setVisibility(View.GONE);

        ViewUtils.show(view);

        assertThat(view.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void testShowWithMultipleViews() {
        final View[] views = {
                new View(RuntimeEnvironment.application),
                new View(RuntimeEnvironment.application),
                new View(RuntimeEnvironment.application)
        };
        for (View view : views) {
            view.setVisibility(View.GONE);
        }

        ViewUtils.show(views);

        for (View view : views) {
            assertThat(view.getVisibility()).isEqualTo(View.VISIBLE);
        }
    }
}
