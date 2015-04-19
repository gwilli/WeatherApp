package com.limerobotsoftware.weatherapp.util;

import android.support.annotation.AnyRes;
import android.view.View;
import android.widget.TextView;

public class ViewHelper {

    public static void setTextView(View parentView, @AnyRes int resId, String value) {
        if (parentView != null) {
            TextView tv = (TextView) parentView.findViewById(resId);
            if (tv != null)
                tv.setText(value);
        }
    }

    public static void setViewVisibility(View parentView, @AnyRes int resId, int visibility) {
        if (parentView != null) {
            View view = parentView.findViewById(resId);
            if (view != null)
                view.setVisibility(visibility);
        }
    }

}
