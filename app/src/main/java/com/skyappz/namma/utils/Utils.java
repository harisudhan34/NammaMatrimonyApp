package com.skyappz.namma.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.skyappz.namma.AppController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.skyappz.namma.utils.ImageFilePath.isDownloadsDocument;
import static com.skyappz.namma.utils.ImageFilePath.isExternalStorageDocument;


public class Utils {

    public static final String DUMMY_TODAY_MATCHES = "{\n" +
            "\t\"status\":true,\n" +
            "\t\"msg\":\"\",\n" +
            "\t\"Users\":[\n" +
            "\t {\n" +
            "      \"user_id\": \"1\",\n" +
            "      \"name\": \"testuser\",\n" +
            "      \"email\": \"selvirevathi3@gmail.com\",\n" +
            "      \"password\": \"test\",\n" +
            "      \"gender\": \"male\",\n" +
            "      \"dob\": \"\",\n" +
            "      \"age\": \"22\",\n" +
            "      \"religion\": \"hindu\",\n" +
            "      \"mother_tongue\": \"tamil\",\n" +
            "      \"mobile_number\": \"9843872561\",\n" +
            "      \"nationality\": \"indian\",\n" +
            "      \"country\": \"india\",\n" +
            "      \"state\": \"tamilnadu\",\n" +
            "      \"home_city\": \"chennai\",\n" +
            "      \"education\": \"mca\",\n" +
            "      \"occupation\": \"it\",\n" +
            "      \"office_details\": \"\",\n" +
            "      \"profile_image\": \"images (1).jpg\",\n" +
            "      \"profile_image_approve\": \"1\",\n" +
            "      \"id_proof\": \"\",\n" +
            "      \"id_proof_approve\": \"1\",\n" +
            "      \"cover_image\": \"\",\n" +
            "      \"cover_image_approve\": \"0\",\n" +
            "      \"horoscope\": \"\",\n" +
            "      \"horoscope_approve\": \"1\",\n" +
            "      \"monthly_income\": \"45000\",\n" +
            "      \"caste\": \"hindu\",\n" +
            "      \"sub_caste\": \"vanniyar\",\n" +
            "      \"father_name\": \"\",\n" +
            "      \"mother_name\": \"\",\n" +
            "      \"father_occupation\": \"\",\n" +
            "      \"mother_occupation\": \"\",\n" +
            "      \"no_of_siblings\": \"0\",\n" +
            "      \"family_status\": \"\",\n" +
            "      \"family_type\": \"\",\n" +
            "      \"profile_created_by\": \"2019-04-04 22:20:56\",\n" +
            "      \"horoscope_id\": \"\",\n" +
            "      \"raasi\": \"Virgo\",\n" +
            "      \"paadham\": \"paadham\",\n" +
            "      \"star\": \"revathi\",\n" +
            "      \"having_dosham\": \"no\",\n" +
            "      \"dosham_details\": \"test\",\n" +
            "      \"height\": \"160\",\n" +
            "      \"weight\": \"57\",\n" +
            "      \"body_type\": \"\",\n" +
            "      \"complexion\": \"\",\n" +
            "      \"physical_status\": \"good\",\n" +
            "      \"smoking_habits\": \"no\",\n" +
            "      \"drinking_habits\": \"no\",\n" +
            "      \"eating_habits\": \"nonveg\",\n" +
            "      \"about_myself\": \"\",\n" +
            "      \"marital_status\": \"single\",\n" +
            "      \"no_of_children\": \"0\",\n" +
            "      \"login_type\": \"0\",\n" +
            "      \"device_id\": \"\",\n" +
            "      \"profile_created_for\": \"\",\n" +
            "      \"paid_status\": \"\",\n" +
            "      \"disability\": \"\",\n" +
            "      \"food_habits\": \"\",\n" +
            "      \"residency_address\": \"\",\n" +
            "      \"working_sector\": \"\"\n" +
            "    },\n" +
            "\t {\n" +
            "      \"user_id\": \"1\",\n" +
            "      \"name\": \"testuser\",\n" +
            "      \"email\": \"selvirevathi3@gmail.com\",\n" +
            "      \"password\": \"test\",\n" +
            "      \"gender\": \"male\",\n" +
            "      \"dob\": \"\",\n" +
            "      \"age\": \"22\",\n" +
            "      \"religion\": \"hindu\",\n" +
            "      \"mother_tongue\": \"tamil\",\n" +
            "      \"mobile_number\": \"9843872561\",\n" +
            "      \"nationality\": \"indian\",\n" +
            "      \"country\": \"india\",\n" +
            "      \"state\": \"tamilnadu\",\n" +
            "      \"home_city\": \"chennai\",\n" +
            "      \"education\": \"mca\",\n" +
            "      \"occupation\": \"it\",\n" +
            "      \"office_details\": \"\",\n" +
            "      \"profile_image\": \"images (1).jpg\",\n" +
            "      \"profile_image_approve\": \"1\",\n" +
            "      \"id_proof\": \"\",\n" +
            "      \"id_proof_approve\": \"1\",\n" +
            "      \"cover_image\": \"\",\n" +
            "      \"cover_image_approve\": \"0\",\n" +
            "      \"horoscope\": \"\",\n" +
            "      \"horoscope_approve\": \"1\",\n" +
            "      \"monthly_income\": \"45000\",\n" +
            "      \"caste\": \"hindu\",\n" +
            "      \"sub_caste\": \"vanniyar\",\n" +
            "      \"father_name\": \"\",\n" +
            "      \"mother_name\": \"\",\n" +
            "      \"father_occupation\": \"\",\n" +
            "      \"mother_occupation\": \"\",\n" +
            "      \"no_of_siblings\": \"0\",\n" +
            "      \"family_status\": \"\",\n" +
            "      \"family_type\": \"\",\n" +
            "      \"profile_created_by\": \"2019-04-04 22:20:56\",\n" +
            "      \"horoscope_id\": \"\",\n" +
            "      \"raasi\": \"Virgo\",\n" +
            "      \"paadham\": \"paadham\",\n" +
            "      \"star\": \"revathi\",\n" +
            "      \"having_dosham\": \"no\",\n" +
            "      \"dosham_details\": \"test\",\n" +
            "      \"height\": \"160\",\n" +
            "      \"weight\": \"57\",\n" +
            "      \"body_type\": \"\",\n" +
            "      \"complexion\": \"\",\n" +
            "      \"physical_status\": \"good\",\n" +
            "      \"smoking_habits\": \"no\",\n" +
            "      \"drinking_habits\": \"no\",\n" +
            "      \"eating_habits\": \"nonveg\",\n" +
            "      \"about_myself\": \"\",\n" +
            "      \"marital_status\": \"single\",\n" +
            "      \"no_of_children\": \"0\",\n" +
            "      \"login_type\": \"0\",\n" +
            "      \"device_id\": \"\",\n" +
            "      \"profile_created_for\": \"\",\n" +
            "      \"paid_status\": \"\",\n" +
            "      \"disability\": \"\",\n" +
            "      \"food_habits\": \"\",\n" +
            "      \"residency_address\": \"\",\n" +
            "      \"working_sector\": \"\"\n" +
            "    },\n" +
            "\t {\n" +
            "      \"user_id\": \"1\",\n" +
            "      \"name\": \"testuser\",\n" +
            "      \"email\": \"selvirevathi3@gmail.com\",\n" +
            "      \"password\": \"test\",\n" +
            "      \"gender\": \"male\",\n" +
            "      \"dob\": \"\",\n" +
            "      \"age\": \"22\",\n" +
            "      \"religion\": \"hindu\",\n" +
            "      \"mother_tongue\": \"tamil\",\n" +
            "      \"mobile_number\": \"9843872561\",\n" +
            "      \"nationality\": \"indian\",\n" +
            "      \"country\": \"india\",\n" +
            "      \"state\": \"tamilnadu\",\n" +
            "      \"home_city\": \"chennai\",\n" +
            "      \"education\": \"mca\",\n" +
            "      \"occupation\": \"it\",\n" +
            "      \"office_details\": \"\",\n" +
            "      \"profile_image\": \"images (1).jpg\",\n" +
            "      \"profile_image_approve\": \"1\",\n" +
            "      \"id_proof\": \"\",\n" +
            "      \"id_proof_approve\": \"1\",\n" +
            "      \"cover_image\": \"\",\n" +
            "      \"cover_image_approve\": \"0\",\n" +
            "      \"horoscope\": \"\",\n" +
            "      \"horoscope_approve\": \"1\",\n" +
            "      \"monthly_income\": \"45000\",\n" +
            "      \"caste\": \"hindu\",\n" +
            "      \"sub_caste\": \"vanniyar\",\n" +
            "      \"father_name\": \"\",\n" +
            "      \"mother_name\": \"\",\n" +
            "      \"father_occupation\": \"\",\n" +
            "      \"mother_occupation\": \"\",\n" +
            "      \"no_of_siblings\": \"0\",\n" +
            "      \"family_status\": \"\",\n" +
            "      \"family_type\": \"\",\n" +
            "      \"profile_created_by\": \"2019-04-04 22:20:56\",\n" +
            "      \"horoscope_id\": \"\",\n" +
            "      \"raasi\": \"Virgo\",\n" +
            "      \"paadham\": \"paadham\",\n" +
            "      \"star\": \"revathi\",\n" +
            "      \"having_dosham\": \"no\",\n" +
            "      \"dosham_details\": \"test\",\n" +
            "      \"height\": \"160\",\n" +
            "      \"weight\": \"57\",\n" +
            "      \"body_type\": \"\",\n" +
            "      \"complexion\": \"\",\n" +
            "      \"physical_status\": \"good\",\n" +
            "      \"smoking_habits\": \"no\",\n" +
            "      \"drinking_habits\": \"no\",\n" +
            "      \"eating_habits\": \"nonveg\",\n" +
            "      \"about_myself\": \"\",\n" +
            "      \"marital_status\": \"single\",\n" +
            "      \"no_of_children\": \"0\",\n" +
            "      \"login_type\": \"0\",\n" +
            "      \"device_id\": \"\",\n" +
            "      \"profile_created_for\": \"\",\n" +
            "      \"paid_status\": \"\",\n" +
            "      \"disability\": \"\",\n" +
            "      \"food_habits\": \"\",\n" +
            "      \"residency_address\": \"\",\n" +
            "      \"working_sector\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static void showAlert(Activity mActivity, String s) {
        AlertDialog.Builder invalidUserDialog = new AlertDialog.Builder(mActivity);
        invalidUserDialog.setTitle("Message");
        invalidUserDialog.setMessage(s);
        invalidUserDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        if (!mActivity.isFinishing()) {
            invalidUserDialog.show();
        }
    }

    public static void showAlert(Context mActivity, String s) {
        AlertDialog.Builder invalidUserDialog = new AlertDialog.Builder(mActivity);
        invalidUserDialog.setTitle("Message");
        if (s.contains("dn not unique based on rules")) {
            s = "A user with that email account already exists";
        }
        invalidUserDialog.setMessage(s);
        invalidUserDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        invalidUserDialog.show();
    }

    public static boolean isEmailValid(String email) {
        return email.matches(emailPattern);
    }

    public static boolean isConnected(Activity _activity) {
        ConnectivityManager conMgr = (ConnectivityManager) _activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }//checkInternetConnection()

    public static ProgressDialog showProgress(String msg) {
        ProgressDialog progressDialog = new ProgressDialog(AppController.getInstance().getBaseContext());
        progressDialog.setMessage(msg);
        return progressDialog;
    }

    public static void showToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    public static String getFacebokKeyHash(Context context) {

        String keyHash = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.i(" Facebook KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", "Exception(NameNotFoundException) : " + e);

        } catch (NoSuchAlgorithmException e) {
            Log.e("", "Exception(NoSuchAlgorithmException) : " + e);
        }
        return keyHash;

    }

    public static String getTime(int currentHour, int currentMins) {
        String time, hourZero = "", minsZero = "";
        String ampm = "AM";
        if (currentHour > 12) {
            currentHour -= 12;
            ampm = "PM";
        }
        if (currentHour < 10) {
            hourZero = "0";
        }
        if (currentMins < 10) {
            minsZero = "0";
        }
        time = "" + hourZero + currentHour + ":" + minsZero + currentMins + " " + ampm;
        return time;
    }

    public static String getTime() {

        Date currentTime = Calendar.getInstance().getTime();
        int currentHour = currentTime.getHours();
        int currentMins = currentTime.getMinutes();
        String ampm = "AM";

        String time, hourZero = "", minsZero = "";
        if (currentHour > 12) {
            currentHour -= 12;
            ampm = "PM";
        }
        if (currentHour < 10) {
            hourZero = "0";
        }
        if (currentMins < 10) {
            minsZero = "0";
        }
        time = "" + hourZero + currentHour + ":" + minsZero + currentMins + " " + ampm;
        return time;
    }

    public static String convertDate(Date date) {
        String strDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return strDate;
    }

    public static String convertTime(String time) {


        int currentHour = Integer.parseInt(time.split(":")[0]);
        int currentMins = Integer.parseInt(time.split(":")[1]);
        String ampm = "AM";

        String hourZero = "", minsZero = "";
        if (currentHour > 12) {
            currentHour -= 12;
            ampm = "PM";
        }
        if (currentHour < 10) {
            hourZero = "0";
        }
        if (currentMins < 10) {
            minsZero = "0";
        }
        time = "" + hourZero + currentHour + ":" + minsZero + currentMins + " " + ampm;
        return time;
    }

    public static String getPathFromURI(Uri uri) {
        String path = uri.getPath().toString();
        return path;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String convertDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String res = null;
        try {
            Date date = format.parse(strDate);
            res = DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return res;
    }

/*    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }*/


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";

        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else {

                if (Build.VERSION.SDK_INT > 20) {
                    //getExternalMediaDirs() added in API 21
                    File extenal[] = context.getExternalMediaDirs();
                    if (extenal.length > 1) {
                        filePath = extenal[1].getAbsolutePath();
                        filePath = filePath.substring(0, filePath.indexOf("Android")) + split[1];
                    }
                } else {
                    filePath = "/storage/" + type + "/" + split[1];
                }
                return filePath;
            }

        } else if (isDownloadsDocument(uri)) {
            // DownloadsProvider
            final String id = DocumentsContract.getDocumentId(uri);
            //final Uri contentUri = ContentUris.withAppendedId(
            // Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    String result = cursor.getString(index);
                    cursor.close();
                    return result;
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if (DocumentsContract.isDocumentUri(context, uri)) {
            // MediaProvider
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String[] ids = wholeID.split(":");
            String id;
            String type;
            if (ids.length > 1) {
                id = ids[1];
                type = ids[0];
            } else {
                id = ids[0];
                type = ids[0];
            }

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{id};
            final String column = "_data";
            final String[] projection = {column};
            Cursor cursor = context.getContentResolver().query(contentUri,
                    projection, selection, selectionArgs, null);

            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex(column);

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
            return filePath;
        } else {
            String[] proj = {MediaStore.Audio.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                if (cursor.moveToFirst())
                    filePath = cursor.getString(column_index);
                cursor.close();
            }


            return filePath;
        }
        return null;
    }

    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int columnIndex =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(columnIndex);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int columnIndex
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        String debugTag = "MemoryInformation";
        // Image nin islenmeden onceki genislik ve yuksekligi
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.d(debugTag, "image height: " + height + "---image width: " + width);
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(debugTag, "inSampleSize: " + inSampleSize);
        return inSampleSize;
    }

    public static String resizeAndCompressImageBeforeSend(Context context, String filePath, String fileName) {
        final int MAX_IMAGE_SIZE = 700 * 1024; // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap bmpPic = BitmapFactory.decodeFile(filePath, options);


        int compressQuality = 100; // quality decreasing by 5 every loop.
        int streamLength;
        do {
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            Log.d("compressBitmap", "Quality: " + compressQuality);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
            compressQuality -= 5;
            Log.d("compressBitmap", "Size: " + streamLength / 1024 + " kb");
        } while (streamLength >= MAX_IMAGE_SIZE);

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap", "cacheDir: " + context.getCacheDir());
            FileOutputStream bmpFile = new FileOutputStream(context.getCacheDir() + fileName);
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
            Log.e("compressBitmap", "Error on saving file");
        }
        //return the path of resized and compressed file
        return context.getCacheDir() + fileName;
    }

    public static String parseDate(int dayOfMonth, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = format.format(calendar.getTime());
        return strDate;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        FileChannel source = new FileInputStream(sourceFile).getChannel();
        FileChannel destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public static File createImageFile(Context context) throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        String imageFilePath = image.getAbsolutePath();
        return image;
    }

    public static boolean dateIsPassed(String date) {
        try {
            if (new SimpleDateFormat("dd-MM-yyyy").parse(date).before(new Date())) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String capitalizeWord(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

