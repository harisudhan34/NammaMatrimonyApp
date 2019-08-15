package com.skyappz.namma.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Surendar.V on 2/25/2018.
 */

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
       /* if (height >= (10 * width)) {
            height = width;
            setScaleType(ScaleType.CENTER_INSIDE);
        } else if (width == height) {
            height = width;
            setScaleType(ScaleType.FIT_XY);
        } else {
            height = width;
            setScaleType(ScaleType.FIT_XY);
        }*/
        setMeasuredDimension(width, width);
    }
}
