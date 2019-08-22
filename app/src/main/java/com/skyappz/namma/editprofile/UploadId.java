package com.skyappz.namma.editprofile;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonParseException;
import com.skyappz.namma.AppController;
import com.skyappz.namma.R;
import com.skyappz.namma.activities.MainDashboard;
import com.skyappz.namma.utils.Utils;
import com.skyappz.namma.webservice.WebServiceListener;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.skyappz.namma.activities.HomeActivity.userid;

public class UploadId extends Fragment implements WebServiceListener {

    public static UploadId newInstance() {
        return new UploadId();
    }
    AppCompatImageView profilepic,profilepic_photo,profilepic_horoscope;
    AppCompatTextView photo_skip,id_skip,horo_skip;
    AppCompatButton uploadbutton,uploadbutton_photo,uploadbutton_horoscope;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    String clicknmae;
    private Activity mActivity;
    String uploadurl="https://nammamatrimony.in/api/uploads.php";
    //Uri to store the image uri
    private Uri filePath;
    Intent CropIntent;
    Uri uri;
    TabHost tabs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upload_id, container, false);

        tabs = (TabHost)view.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("Photo");
        spec.setContent(R.id.tab1);
        spec.setIndicator("photo");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("ID");
        spec.setContent(R.id.tab2);
        spec.setIndicator("ID");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("Horoscope");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Horoscope");
        tabs.addTab(spec);


        uploadbutton=(AppCompatButton)view.findViewById(R.id.upload_button);
        profilepic=(AppCompatImageView)view.findViewById(R.id.profile_image);
        id_skip=(AppCompatTextView)view.findViewById(R.id.id_skip);

        uploadbutton_photo=(AppCompatButton)view.findViewById(R.id.upload_button_photo);
        profilepic_photo=(AppCompatImageView)view.findViewById(R.id.profile_image_photo);
        photo_skip=(AppCompatTextView)view.findViewById(R.id.photo_skip);

        uploadbutton_horoscope=(AppCompatButton)view.findViewById(R.id.upload_button_horoscope);
        profilepic_horoscope=(AppCompatImageView)view.findViewById(R.id.profile_image_hororscope);
        horo_skip=(AppCompatTextView)view.findViewById(R.id.horo_skip);
        requestStoragePermission();
//        tabs.getTabWidget().setEnabled(false);
        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath != null){
                    uploadMultipart();
                }else {
                    Toast.makeText(getActivity(),"Choose image",Toast.LENGTH_SHORT).show();
                }

            }
        });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                clicknmae = "ID";
            }
        });
        uploadbutton_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath != null){
                    uploadPHOTO();
                }else {
                    Toast.makeText(getActivity(),"Choose image",Toast.LENGTH_SHORT).show();
                }

            }
        });
        profilepic_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                clicknmae = "PHOTO";
            }
        });

        uploadbutton_horoscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath != null){
                    uploadHoroscope();
                }else {
                    Toast.makeText(getActivity(),"Choose image",Toast.LENGTH_SHORT).show();
                }

            }
        });
        profilepic_horoscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                clicknmae = "horoscope";
            }
        });
        photo_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabs.setCurrentTab(1);
            }
        });
        id_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabs.setCurrentTab(2);
            }
        });

        return view;


    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    private void showFileChooser() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(i, "Select Image From Gallery"), PICK_IMAGE_REQUEST);

    }
    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                if (clicknmae.equalsIgnoreCase("ID")){
                    profilepic.setScaleType(ImageView.ScaleType.FIT_XY);
                    uri = data.getData();
//                    ImageCropFunction();
                    profilepic.setImageBitmap(bitmap);
                }

                if (clicknmae.equalsIgnoreCase("PHOTO")){
                    profilepic_photo.setScaleType(ImageView.ScaleType.FIT_XY);
                    profilepic_photo.setImageBitmap(bitmap);
                }

                if (clicknmae.equalsIgnoreCase("horoscope")){
                    profilepic_horoscope.setScaleType(ImageView.ScaleType.FIT_XY);
                    profilepic_horoscope.setImageBitmap(bitmap);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }

    public void uploadMultipart() {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, uploadurl)
                    .addFileToUpload(path, "id_proof") //Adding file
                    .addParameter("user_id", userid) //Adding text parameter to the request
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(UploadInfo uploadInfo) {
                        }

                        @Override
                        public void onError(UploadInfo uploadInfo, Exception exception) {

                        }

                        @Override
                        public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Toast.makeText(getActivity(),"completed",Toast.LENGTH_SHORT).show();
                            tabs.setCurrentTab(2);
                        }

                        @Override
                        public void onCancelled(UploadInfo uploadInfo) {

                        }
                    })
                    .startUpload(); //Starting the upload


        } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void uploadPHOTO() {
        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, uploadurl)
                    .addFileToUpload(path, "profile_image") //Adding file
                    .addParameter("user_id", userid) //Adding text parameter to the request
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(UploadInfo uploadInfo) {
                        }

                        @Override
                        public void onError(UploadInfo uploadInfo, Exception exception) {

                        }

                        @Override
                        public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                            Toast.makeText(getActivity(),"completed",Toast.LENGTH_SHORT).show();
                            tabs.setCurrentTab(1);
                        }

                        @Override
                        public void onCancelled(UploadInfo uploadInfo) {

                        }
                    })
                    .startUpload(); //Starting the upload


        } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void uploadHoroscope() {

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(getActivity(), uploadId, uploadurl)
                    .addFileToUpload(path, "horoscope") //Adding file
                    .addParameter("user_id", userid) //Adding text parameter to the request
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(UploadInfo uploadInfo) {
                        }

                        @Override
                        public void onError(UploadInfo uploadInfo, Exception exception) {

                        }

                        @Override
                        public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                            sendmail();
//                            send_sms();
                            Intent move=new Intent(getActivity(), MainDashboard.class);
                            mActivity.startActivity(move);
                            getActivity().finish();
                            Toast.makeText(getActivity(),"completed",Toast.LENGTH_SHORT).show();
//                            preferences.setRegistered(true);

                        }

                        @Override
                        public void onCancelled(UploadInfo uploadInfo) {

                        }
                    })
                    .startUpload(); //Starting the upload


        } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendmail(){
        String tag_json_obj = "sendemail";
        String url = "https://nammamatrimony.in/api/sendmail.php?user_id="+userid;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status= response.getString("status");
                            if (status.equalsIgnoreCase("true")){
                                Log.e("mailsended",response.getString("msg"));
                            }

                        } catch (JSONException e) {

                        } catch (JsonParseException e) {

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        if (Utils.isConnected((getActivity()))) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }
    }

    public void send_sms(){
        String tag_json_obj = "sendsms";
        String url = "https://nammamatrimony.in/api/send_sms.php?mobile_number=9865565513";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status= response.getString("status");
                            if (status.equalsIgnoreCase("true")){
                                Log.e("smssended",response.getString("msg"));
                            }

                        } catch (JSONException e) {

                        } catch (JsonParseException e) {

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        if (Utils.isConnected((getActivity()))) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            this.isNetworkAvailable(false);
        }
    }





    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onSuccess(int requestCode, int responseCode, Object response) {

    }

    @Override
    public void onFailure(int requestCode, int responseCode, Object response) {

    }

    @Override
    public void isNetworkAvailable(boolean flag) {

    }

    @Override
    public void onProgressStart() {

    }

    @Override
    public void onProgressEnd() {

    }
}

