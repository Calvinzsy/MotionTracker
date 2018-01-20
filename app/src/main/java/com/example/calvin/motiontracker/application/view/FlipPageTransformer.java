package com.example.calvin.motiontracker.application.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Custom transformer for {@link ViewPager} that simulates page flip and fade.
 */
public class FlipPageTransformer implements ViewPager.PageTransformer{

    /**
     * The degree of rotation around vertical axis at peak.
     */
    private static final float MAX_ROTATION_Y = -15f;

    /**
     * The alpha value at peak.
     */
    private static final float MIN_ALPHA = 0.5f;

    /**
     * {@inheritDoc}
     */
    @Override
    public void transformPage(View view, float position) {

        if (position < -1) {
            view.setAlpha(0);
        }else if (position <= 1) {

            float rotationY = position * MAX_ROTATION_Y;
            view.setRotationY(rotationY);

            float alpha = Math.max(MIN_ALPHA, 1 - Math.abs(position));
            view.setAlpha(alpha);
        } else {
            view.setAlpha(0);
        }
    }
}