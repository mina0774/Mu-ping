/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.cloudvision;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;

    private String imagepath;
    private TextView mImageDetails;
    private TextView mMusicDetails;
    private ImageView mMainImage;

    List<Object> ObjectArray = new ArrayList<Object>();
    List<Double> valence = new ArrayList<Double>();
    List<Double> arousal = new ArrayList<Double>();
    List<Double> valence_H=new ArrayList<Double>();
    List<Double> valence_L=new ArrayList<Double>();
    List<Double> arousal_H=new ArrayList<Double>();
    List<Double> arousal_L=new ArrayList<Double>();
    Double valence_final_obj;
    Double arousal_final_obj;
    static String[] colorResults = {};

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMusicDetails=(TextView)findViewById(R.id.music_details);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        FloatingActionButton fab3 = findViewById(R.id.fab3);
        TextView reco = findViewById(R.id.reco);

        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder
                    .setMessage("사진을 선택해주세요")
                    .setPositiveButton("갤러리", (dialog, which) -> startGalleryChooser())
                    .setNegativeButton("카메라", (dialog, which) -> startCamera());
            ObjectArray.clear();
            valence.clear();
            arousal.clear();
            valence_H.clear();
            valence_L.clear();
            arousal_H.clear();
            arousal_L.clear();
            builder.create().show();
        });

        //유저 프로파일로
        fab2.setOnClickListener(view -> {
            if(user == null) {
                Toast.makeText(MainActivity.this, "로그인 되어있지 않습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                StartActivity _StartActivity = (StartActivity) StartActivity._StartActivity;
                _StartActivity.finish();
                finish();

            } else {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        //좋아한 곡 리스트
        fab3.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LikeListActivity.class);
            startActivity(intent);
        });

        reco.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RecommendListActivity.class);
            startActivity(intent);
        });


        mImageDetails = findViewById(R.id.image_details);
        mMainImage = findViewById(R.id.main_image);


    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "사진을 선택해주세요."),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imagepath=dir.getAbsolutePath()+"/"+FILE_NAME;
        return new File(dir, FILE_NAME);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imagepath =getRealPathFromURI(data.getData());
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap = scaleBitmapDown(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), MAX_DIMENSION);
                int degree = getExifOrientation(imagepath);
                bitmap = getRotatedBitmap(bitmap, degree);
                callCloudVision(bitmap);
                mMainImage.setImageBitmap(bitmap);
                mMainImage.setDrawingCacheEnabled(true);
                mMainImage.buildDrawingCache();

                ColorData a = new ColorData();
                int[] colorInts = a.getColorScale(bitmap);
                colorResults = a.getSimilarScale(colorInts[0], colorInts[1], colorInts[2]);
            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    //사진 회전 문제
    private String getRealPathFromURI(Uri contentURI){
        String[] proj = { MediaStore.Images.Media.DATA };

        CursorLoader cursorLoader = new CursorLoader(this, contentURI, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //회전 각도 구하기
    private int getExifOrientation(String filePath) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        return 90;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        return 180;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        return 270;
                }
            }
        }

        return 0;
    }
    //이미지 회전하기
    private Bitmap getRotatedBitmap(Bitmap bitmap, int degree) {
        if (degree != 0 && bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            try {
                Bitmap tmpBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                if (bitmap != tmpBitmap) {
                    bitmap.recycle();
                    bitmap = tmpBitmap;
                }
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("LABEL_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }

    private class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<MainActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LableDetectionTask(MainActivity activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                TextView imageDetail = activity.findViewById(R.id.image_details);
                String word=find_adj(Double.parseDouble(colorResults[1]),Double.parseDouble(colorResults[2]));
                Double[] final_value=combine_Attribute(valence_final_obj,arousal_final_obj);
                String final_word=find_adj(final_value[0],final_value[1]);
                String music[]=find_music(final_word);

                result = result + word + " " + colorResults[1] + " , " + colorResults[2]+"\n\n"
                        +final_value[0].toString()+","
                        +final_value[1].toString()+" "
                        +final_word;

                imageDetail.setText(result);
                mMusicDetails.setText(music[0]+" - "+music[1]);

            }
        }
    }

    private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        mImageDetails.setText("사진 업로드 중입니다. 잠시만 기다려주세요.");

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    //DB에서 AV값 꺼내기
    public List<Object> objectToAV() {
        SQLite helper;
        SQLiteDatabase db;
        Cursor cursor;
        Iterator iterator = ObjectArray.iterator();
        List<Object> word = new ArrayList<Object>();

        helper = new SQLite(this);
        db = helper.getReadableDatabase();
        while (iterator.hasNext()) {
            cursor = db.rawQuery("SELECT * FROM valence_arousal where word='" + (String) ((String) iterator.next()).toLowerCase() + "';", null);
            while (cursor.moveToNext()) {
                word.add(cursor.getString(0)); //word
                word.add(cursor.getString(1)); //valence
                word.add(cursor.getString(2)); //arousal
            }
        }
        return word;
    }

    //가까운 노래 형용사 찾기
    public String find_adj(double valence_obj,double arousal_obj){
        SQLite helper;
        SQLiteDatabase db;
        Cursor cursor;
        Double arousal_pow;
        Double valence_pow;
        Double sum_pow;
        Double temp=100.0;
        Double valence_real=0.0;
        Double arousal_real=0.0;
        String word="a";
        helper = new SQLite(this);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM criteria_adj;", null);
        while (cursor.moveToNext()) {
            valence_pow = Math.pow((cursor.getDouble(1) - valence_obj), 2);
            arousal_pow = Math.pow((cursor.getDouble(2) - arousal_obj), 2);
            sum_pow = valence_pow + arousal_pow;
            if (sum_pow < temp) {
                valence_real = cursor.getDouble(1);
                arousal_real = cursor.getDouble(2);
                temp=sum_pow;
                word=cursor.getString(0);
            }
        }
        cursor.close();
        return word;
    }

    //Object Detection 결과값 받아오기
    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("Keyword: \n");
        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {
                ObjectArray.add(String.format(Locale.US, "%s", label.getDescription()));
            }
        } else {
            message.append("nothing");
        }
        List<Object> result_word = objectToAV();
        Iterator iterator = result_word.iterator();
        int count = 0;
        int i = 0;
        while (iterator.hasNext()) {
            String word = (String) (iterator.next());
            // valence,arousal 배열에 값 넣기
            if ((count % 3) == 1) {
                valence.add(Double.parseDouble(word));
            }
            if ((count % 3) == 2) {
                arousal.add(Double.parseDouble(word));
            }
            count += 1;
        }
        valence_final_obj=range()[0];
        arousal_final_obj=range()[1];
        String word=find_adj(valence_final_obj,arousal_final_obj);
        message.append(word+" ");
        message.append(valence_final_obj+ " , "+arousal_final_obj);
        message.append("\nColor:\n");
        return message.toString();
    }

    //valence arousal 값 범위 안에 속하는지 확인하기
    public Double[] range() {
        List<Integer> arousal_int=new ArrayList<Integer>();
        List<Integer> valence_int=new ArrayList<Integer>();
        Double temp;
        Iterator iterator = arousal.iterator();
        int arousal_count=0;
        while (iterator.hasNext()) {
            temp = (Double) iterator.next();
            if (temp <= 5) {
                arousal_int.add(arousal_count,0);
            } else if (temp > 5) {
                arousal_int.add(arousal_count,1);
            }
            arousal_count++;
        }

        Double temp1;
        Iterator iterator1 = valence.iterator();
        int valence_count=0;
        while (iterator1.hasNext()) {
            temp1 = (Double) iterator1.next();
            if (temp1 <= 5) {
               valence_int.add(valence_count,0);
            } else if (temp1 > 5) {
                valence_int.add(valence_count,1);
            }
            valence_count++;
        }
        Iterator arousal_iterator=arousal_int.iterator();
        int count=0;
        int num1=0,num2=0,num3=0,num4=0;
        int temp_int;
        while(arousal_iterator.hasNext()){
            temp_int = (int)arousal_iterator.next();
            if(valence_int.get(count)==1&&temp_int==1){
                num1++;
            }else if(valence_int.get(count)==1&&temp_int==0){
                num2++;
            }else if(valence_int.get(count)==0&&temp_int==0){
                num3++;
            }else if(valence_int.get(count)==0&&temp_int==1){
                num4++;
            }
            count++;
        }

        int[] num={num1,num2,num3,num4};
        int max=num[0];
        for(int i=0;i<num.length;i++) {
            if (max < num[i]) {
                max = num[i];
            }
        }

        count=0;
        Double sum1_a=0.0;Double sum2_a=0.0;Double sum3_a=0.0;Double sum4_a=0.0;
        Double sum1_v=0.0;Double sum2_v=0.0;Double sum3_v=0.0;Double sum4_v=0.0;
        Double avg_v=0.0; Double avg_a=0.0;
        arousal_iterator=arousal_int.iterator();
        if (max==num1) {
            while (arousal_iterator.hasNext()) {
                temp_int = (int)arousal_iterator.next();
                if (valence_int.get(count) == 1 && temp_int == 1) {
                    sum1_v += valence.get(count);
                    sum1_a+=arousal.get(count);
                }
                count++;
            }
            avg_v=sum1_v/num1;
            avg_a=sum1_a/num1;
        }
        else if(max==num2){
            while (arousal_iterator.hasNext()) {
                temp_int = (int)arousal_iterator.next();
                if (valence_int.get(count) == 1 && temp_int  == 0) {
                    sum2_v += valence.get(count);
                    sum2_a+=arousal.get(count);
                }
                count++;
            }
            Log.d("sum_v",""+sum2_v);
            Log.d("sum_a",""+sum2_a);
            avg_v=sum2_v/num2;
            avg_a=sum2_a/num2;
        }
        else if (max==num3){
            while (arousal_iterator.hasNext()) {
                temp_int = (int)arousal_iterator.next();
                if (valence_int.get(count) == 0 && temp_int == 0) {
                    sum3_v += valence.get(count);
                    sum3_a+=arousal.get(count);
                }
                count++;
            }
            avg_v=sum3_v/num3;
            avg_a=sum3_a/num3;
        }
        else if (max==num4){
            while (arousal_iterator.hasNext()) {
                temp_int = (int)arousal_iterator.next();
                if (valence_int.get(count) == 0 && temp_int == 1) {
                    sum4_v += valence.get(count);
                    sum4_a+=arousal.get(count);
                }
                count++;
            }
            avg_v=sum4_v/num4;
            avg_a=sum4_a/num4;
        }

        Double[] avg={avg_v,avg_a};
        return avg;
    }

    //valence arousal 색깔, 오브젝트 평균 내기
    public Double[] combine_Attribute(Double obj_v,Double obj_a){
        Double color_v=Double.parseDouble(colorResults[1]);
        Double color_a=Double.parseDouble(colorResults[2]);
        Double color_weight=0.53333; //가중치 임의로 설정
        Double object_weight=0.46667; //가중치 임의로 설정
        Double final_v=0.0;
        Double final_a=0.0;
       //같은 사분면일때
        //1사분면
        if(obj_v>5&&obj_a>5&& color_v>5&& color_a>5) {
            final_v=obj_v*object_weight+color_v*color_weight;
            final_a=obj_a*object_weight+color_a*color_weight;
        }
        //2사분면
        else if(obj_v>5&&obj_a<=5&& color_v>5&& color_a<=5) {
            final_v=obj_v*object_weight+color_v*color_weight;
            final_a=obj_a*object_weight+color_a*color_weight;
        }
        //3사분면
        else if(obj_v<=5&&obj_a<=5&& color_v<=5&& color_a<=5) {
            final_v=obj_v*object_weight+color_v*color_weight;
            final_a=obj_a*object_weight+color_a*color_weight;
        }
        //4사분면
        else if(obj_v<=5&&obj_a>5&& color_v<=5&& color_a>5) {
            final_v=obj_v*object_weight+color_v*color_weight;
            final_a=obj_a*object_weight+color_a*color_weight;
        }
        //다른 사분면일때
        else{
            if(color_weight>=object_weight){
                final_v=color_v;
                final_a=color_a;
            }
            else if(color_weight<=object_weight){
                final_v=obj_v;
                final_a=obj_a;
            }
        }
        Log.d("평균",""+final_v+","+final_a);
        Double[] final_va={final_v,final_a};
        return final_va;
    }

    public String[] find_music(String adj_final){
        SQLite helper;
        SQLiteDatabase db;
        Cursor cursor;
        String title="";
        String performer="";
        helper = new SQLite(this);
        db = helper.getReadableDatabase();

        cursor = db.rawQuery("SELECT title,performer FROM music_to_value_final where word='"+adj_final+"' order by random();",null);
    if(cursor.moveToNext()) {
    title = cursor.getString(0);
    performer = cursor.getString(1);
    }

        String[] music={title,performer};

        return music;
    }

    public void music_play(View view){
        Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
        startActivity(intent);
    }
}
