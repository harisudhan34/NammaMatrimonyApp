package com.skyappz.namma.ImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.skyappz.namma.R;

import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {

    private ImageViewTouch imageView;
    ArrayList<EnlargeImage> PhotoCollection;
    Context ctx;
    int pos;

    public ImagePagerAdapter(Context ctx, ArrayList<EnlargeImage> Photos, int pos) {
        this.PhotoCollection = Photos;
        this.ctx = ctx;
        this.pos = pos;
    }


    @Override
    public int getCount() {
        return PhotoCollection.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @SuppressLint("NewApi")
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageViewTouch imageView = new ImageViewTouch(ctx);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        try {
            imageView
                    .setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

                imageView.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
                    @Override
                    public void onSingleTapConfirmed() {
                        Intent i = new Intent(imageView.getContext(), FullImageView.class);
                        EnlargeImage[] cs = PhotoCollection.toArray(new EnlargeImage[PhotoCollection.size()]);
                        i.putParcelableArrayListExtra("IMAGES", PhotoCollection);
                        i.putExtra("POS", position);
                        ctx.startActivity(i);
                    }
                });

            if (position == 0) {
                UrlImageViewHelper.setUrlDrawable(imageView,
                        PhotoCollection.get(position).getImage(), R.drawable.loading,
                        new UrlImageViewCallback() {
                            @Override
                            public void onLoaded(ImageView loadedImage,
                                                 Bitmap loadedBitmap, String url,
                                                 boolean loadedFromCache) {
                                if (!loadedFromCache) {
                                    ScaleAnimation scale = new ScaleAnimation(
                                            0,
                                            1,
                                            0,
                                            1,
                                            ScaleAnimation.RELATIVE_TO_SELF,
                                            .5f,
                                            ScaleAnimation.RELATIVE_TO_SELF,
                                            .5f);
                                    scale.setDuration(300);
                                    scale.setInterpolator(new OvershootInterpolator());
                                    loadedImage.startAnimation(scale);
                                }
                            }
                        }
                );
            } else {
                UrlImageViewHelper.setUrlDrawable(imageView,
                        PhotoCollection.get(position).getImage(),
                        R.drawable.loading, new UrlImageViewCallback() {
                            @Override
                            public void onLoaded(ImageView loadedImage,
                                                 Bitmap loadedBitmap, String url,
                                                 boolean loadedFromCache) {
                                if (!loadedFromCache) {
                                    ScaleAnimation scale = new ScaleAnimation(
                                            0,
                                            1,
                                            0,
                                            1,
                                            ScaleAnimation.RELATIVE_TO_SELF,
                                            .5f,
                                            ScaleAnimation.RELATIVE_TO_SELF,
                                            .5f);
                                    scale.setDuration(300);
                                    scale.setInterpolator(new OvershootInterpolator());
                                    loadedImage.startAnimation(scale);
                                }
                            }
                        }
                );
            }

            container.addView(imageView, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageViewTouch) object);
    }
}