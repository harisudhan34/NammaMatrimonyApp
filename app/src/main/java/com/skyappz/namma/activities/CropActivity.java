package com.skyappz.namma.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.skyappz.namma.R;
import com.skyappz.namma.utils.Utils;
import com.naver.android.helloyako.imagecrop.view.ImageCropView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CropActivity extends Activity {
    public static final String TAG = "CropActivity";

    private ImageCropView imageCropView;
    private float[] positionInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        imageCropView = (ImageCropView) findViewById(R.id.image);

        Intent i = getIntent();
        Uri uri = i.getData();
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int imageWidth = (int) ( (float) metrics.widthPixels / 1.5 );
//        int imageHeight = (int) ( (float) metrics.heightPixels / 1.5 );
//
//        bitmap = BitmapLoadUtils.decode(uri.toString(), imageWidth, imageHeight);
//
//        imageCropView.setImageBitmap(bitmap);

        imageCropView.setImageFilePath(uri.toString());

        imageCropView.setAspectRatio(1, 1);

        findViewById(R.id.ratio11btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click 1 : 1");
                if (isPossibleCrop(1, 1)) {
                    imageCropView.setAspectRatio(1, 1);
                } else {
                    Utils.showToast(CropActivity.this, "Cannot crop");
                }
            }
        });

        findViewById(R.id.ratio34btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click 3 : 4");
                if (isPossibleCrop(3, 4)) {
                    imageCropView.setAspectRatio(3, 4);
                } else {
                    Utils.showToast(CropActivity.this, "Cannot crop");
                }
            }
        });

        findViewById(R.id.ratio43btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click 4 : 3");
                if (isPossibleCrop(4, 3)) {
                    imageCropView.setAspectRatio(4, 3);
                } else {
                    Utils.showToast(CropActivity.this, "Cannot crop");
                }
            }
        });

        findViewById(R.id.ratio169btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click 16 : 9");
                if (isPossibleCrop(16, 9)) {
                    imageCropView.setAspectRatio(16, 9);
                } else {
                    Utils.showToast(CropActivity.this, "Cannot crop");
                }
            }
        });

        findViewById(R.id.ratio916btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click 9 : 16");
                if (isPossibleCrop(9, 16)) {
                    imageCropView.setAspectRatio(9, 16);
                } else {
                    Utils.showToast(CropActivity.this, "Cannot crop");
                }
            }
        });

        findViewById(R.id.crop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageCropView.isChangingScale()) {
                    Bitmap b = imageCropView.getCroppedImage();
                    if (b != null) {
                        bitmapConvertToFile(b);
                    } else {
                        Utils.showToast(CropActivity.this, "Failed to crop");
                    }
                }
            }
        });
    }

    private boolean isPossibleCrop(int widthRatio, int heightRatio) {
        Bitmap bitmap = imageCropView.getViewBitmap();
        if (bitmap == null) {
            return false;
        }
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        if (bitmapWidth < widthRatio && bitmapHeight < heightRatio) {
            return false;
        } else {
            return true;
        }
    }

    public File bitmapConvertToFile(Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        File bitmapFile = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory("cambook"), "");
            if (!file.exists()) {
                file.mkdir();
            }

            bitmapFile = new File(file, "IMG_" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(Calendar.getInstance().getTime()) + ".jpg");
            fileOutputStream = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            MediaScannerConnection.scanFile(this, new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(final String path, Uri uri) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(CropActivity.this, "file saved", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.putExtra("path", path);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
        }

        return bitmapFile;
    }

    public void onClickSaveButton(View v) {
        imageCropView.saveState();
        View restoreButton = findViewById(R.id.restore_btn);
        if (!restoreButton.isEnabled()) {
            restoreButton.setEnabled(true);
        }
    }

    public void onClickRestoreButton(View v) {
        imageCropView.restoreState();
    }

}