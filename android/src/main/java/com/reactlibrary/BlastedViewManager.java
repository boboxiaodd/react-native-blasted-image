package com.xerdnu.blastedimage;

import com.bumptech.glide.Glide;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import android.graphics.PorterDuff;
import android.media.Image;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewGroup;

import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;

import android.util.Log;

import javax.annotation.Nullable;


public class BlastedViewManager extends SimpleViewManager<ImageView> {

    public static final String REACT_CLASS = "BlastedImageView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ImageView createViewInstance(ThemedReactContext reactContext) {
        return new ImageView(reactContext);
    }

    @ReactProp(name = "sourceUri")
    public void setSourceUri(ImageView view, String sourceUri) {
        if (sourceUri != null && !sourceUri.isEmpty()) {
            Glide.with(view.getContext())
                .load(sourceUri)
                .into(view);
            view.setVisibility(View.VISIBLE);  // SourceUri is valid so show ImageView
        } else {
            view.setVisibility(View.INVISIBLE);  // Hide the ImageView
        }
    }

    @ReactProp(name = "resizeMode")
    public void setResizeMode(ImageView view, String resizeMode) {
        Log.d("BlastedImageViewManager", "resizeMode value: " + resizeMode);

        // If resizeMode is not specified or is invalid, set to cover
        if (resizeMode == null || resizeMode.isEmpty() || "undefined".equals(resizeMode)) {
            resizeMode = "cover";
        }

        if ("contain".equals(resizeMode)) {
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else if ("stretch".equals(resizeMode)) {
            view.setScaleType(ImageView.ScaleType.FIT_XY);
        } else if ("cover".equals(resizeMode)) {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else if ("center".equals(resizeMode)) {
            view.setScaleType(ImageView.ScaleType.CENTER);
        } else {
            view.setScaleType(ImageView.ScaleType.CENTER_CROP); // Default to cover
        }

    }

    @ReactProp(name = "tintColor", customType = "Color")
    public void setTintColor(ImageView view, @Nullable Integer color) {
        if (color == null) {
            view.clearColorFilter();
        } else {
            view.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    @ReactProp(name = "width")
    public void setWidth(ImageView view, int width) {
        if (width <= 0) {
            width = 100; // default 100
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = width;
            view.setLayoutParams(layoutParams);
        }
    }

    @ReactProp(name = "height")
    public void setHeight(ImageView view, int height) {
        if (height <= 0) {
            height = 100; // default 100
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
    }

    // more properties... :)
}
