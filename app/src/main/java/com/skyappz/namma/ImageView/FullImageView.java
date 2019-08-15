package com.skyappz.namma.ImageView;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;


import com.skyappz.namma.R;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullImageView extends Activity implements View.OnClickListener {
    private ViewPager pager;
    private ImageView close;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_image_view);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.hide();
        }

        pager = (ViewPager) findViewById(R.id.pager);
        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(this);
        ArrayList<EnlargeImage> list = new ArrayList<>();

        if (getIntent().getParcelableArrayListExtra("IMAGES") != null) {
            list.addAll(getIntent().<EnlargeImage>getParcelableArrayListExtra("IMAGES"));

            pos = getIntent().getIntExtra("POS", 0);
//            for (int i = 0; i < images.length; i++) {
//                EnlargeImage enlargeImage = new EnlargeImage();
//                enlargeImage.setImage(images[i]);
//                enlargeImage.setImage(Constant.AdminPageURL + images[i]);
//                list.add(enlargeImage);
//            }
        }
        ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(FullImageView.this, list, pos);
        pager.setAdapter(pagerAdapter);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                this.finish();
                break;
        }
    }


    public void downloadBtnSelected(View anchor) {
        final ListPopupWindow lpw = new ListPopupWindow(this);
        String[] data = {".png", ".pdf", ".jpg", ".jpeg"};
        ArrayAdapter<String> pa =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, data);
        lpw.setAdapter(pa);

        //setting up an anchor view
        lpw.setAnchorView(anchor);

        //Setting measure specifications. I'v used this mesure specs to display my
        //ListView as wide as my anchor view is
        lpw.setHeight(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
        lpw.setWidth(anchor.getRight() - anchor.getLeft());

        // Background is needed. You can use your own drawable or make a 9patch.
        // I'v used a custom btn drawable. looks nice.
        lpw.setBackgroundDrawable(this.getResources().getDrawable(
                android.R.drawable.btn_default));

        // Offset between anchor view and popupWindow
        lpw.setVerticalOffset(3);

        lpw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                /// Our action.....
                lpw.dismiss();

            }
        });
        lpw.show();

    }
}

