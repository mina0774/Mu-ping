package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MusicListActivity extends AppCompatActivity {

    static DrawableManager DM=new DrawableManager();
    AsyncTask<?,?,?> searchTask;
    ArrayList<SearchData> sdata=new ArrayList<SearchData>();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("User");
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    LikeButton likeCheck;
    private TextView title;
    private TextView performer;
    private TextView genre;
    private ImageView song_image;
    private String uE;

    final String serverKey="AIzaSyA0B72xByDrqzb68bZ0fQylnasgHDdGbMw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        likeCheck = findViewById(R.id.like_check);
        likeCheck.setCircleStartColorRes(R.color.colorAnimation1);
        likeCheck.setExplodingDotColorsRes(R.color.colorAnimation1, R.color.colorAnimation2);
        likeCheck.setCircleEndColorRes(R.color.colorAnimation1);

        Intent intent = getIntent();
        title = (TextView) findViewById(R.id.tv_title);
        performer = (TextView) findViewById(R.id.tv_performer);
        genre=(TextView)findViewById(R.id.tv_genre);
        song_image = (ImageView) findViewById(R.id.song_image);
        title.setText(intent.getStringExtra("title"));
        performer.setText(intent.getStringExtra("performer"));
        genre.setText(intent.getStringExtra("genre"));
        String adj;
        adj = intent.getStringExtra("adj");

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        song_image.setImageBitmap(image);
        song_image.setVisibility(View.GONE);

        String Title = title.getText().toString();
        String Performer = performer.getText().toString();
        String Genre = genre.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            uE = user.getEmail();

            StorageReference songRef = storageReference.child(Title+"_image_"+uE);

            likeCheck.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String userEmail = user.getEmail();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap sendBitmap = ((BitmapDrawable)song_image.getDrawable()).getBitmap();
                    sendBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
                    byte[] data = stream.toByteArray();
                    UploadTask uploadTask = songRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }
                                    // Continue with the task to get the download URL
                                    return songRef.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                    } else {
                                    }
                                }
                            });

                        }
                    });

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            SongItem songItem = new SongItem(Title, Performer, Genre, adj);
                            StringTokenizer st = new StringTokenizer(userEmail, "@");
                            myRef.child(st.nextToken()).child("song").child(Title).setValue(songItem);

                            Toast.makeText(MusicListActivity.this, "내가 좋아한곡 리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String userEmail = user.getEmail();

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            StringTokenizer st = new StringTokenizer(userEmail, "@");
                            myRef.child(st.nextToken()).child("song").child(Title).removeValue();

                            Toast.makeText(MusicListActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            });
        } else {
            likeCheck.setVisibility(View.GONE);
        }

        searchTask=new searchTask().execute();


    }

    private class searchTask extends  AsyncTask<Void,Void,Void>{
        @Override
        protected  void onPreExecute(){ super.onPreExecute();}

        @Override
        protected Void doInBackground(Void... params){
            try{
                paringJsonData(getUtube());
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            ListView searchlist = (ListView) findViewById(R.id.searchlist);

            StoreListAdapter mAdapter = new StoreListAdapter(MusicListActivity.this, R.layout.listview_music, sdata);
            searchlist.setAdapter(mAdapter);
        }
    }

    public JSONObject getUtube() {
       String music=title.getText().toString()+"-"+performer.getText().toString();
        String ret="";
        for(int i=0;i<music.length();i++){
            if(music.charAt(i)==' '){
                ret+="%20";
                continue;
            }
            ret+=music.charAt(i);
        }

        HttpGet httpGet = new HttpGet(
                "https://www.googleapis.com/youtube/v3/search?"
                        + "part=snippet&q=" + ret
                        + "&maxResults=20&key="+ serverKey);
        Log.d("http","https://www.googleapis.com/youtube/v3/search?"
                + "part=snippet&maxResults=20&q=" + ret
                + "&key="+ serverKey);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    private void paringJsonData(JSONObject jsonObject) throws JSONException {
        sdata.clear();

        JSONArray contacts = jsonObject.getJSONArray("items");

        for (int i = 0; i < contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);
            String vodid = c.getJSONObject("id").getString("videoId");

            String title = c.getJSONObject("snippet").getString("title");
            String changString = "";
            try {
                changString = new String(title.getBytes("8859_1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String date = c.getJSONObject("snippet").getString("publishedAt")
                    .substring(0, 10);
            String imgUrl = c.getJSONObject("snippet").getJSONObject("thumbnails")
                    .getJSONObject("default").getString("url");

            sdata.add(new SearchData(vodid, changString, imgUrl, date));
        }
    }

    String vodid="";

    public class StoreListAdapter extends ArrayAdapter<SearchData> {
        private ArrayList<SearchData> items;
        SearchData fInfo;

        public StoreListAdapter(Context context, int textViewResourseId,ArrayList<SearchData> items) {
            super(context, textViewResourseId, items);
            this.items = items;
        }

        public View getView(int position, View convertView, ViewGroup parent) {// listview

            View v = convertView;
            fInfo = items.get(position);

            LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.listview_music, null);
            ImageView img = (ImageView) v.findViewById(R.id.img);

            String url = fInfo.getUrl();

            String sUrl = "";
            String eUrl = "";
            sUrl = url.substring(0, url.lastIndexOf("/") + 1);
            eUrl = url.substring(url.lastIndexOf("/") + 1, url.length());
            try {
                eUrl = URLEncoder.encode(eUrl, "EUC-KR").replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String new_url = sUrl + eUrl;

            DM.fetchDrawableOnThread(new_url, img);
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            v.setTag(position);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();

                    Intent intent = new Intent(MusicListActivity.this,
                            PlayMusicActivity.class);
                    Log.d("아이템"," "+items.get(pos).getVideoId());
                    intent.putExtra("id", items.get(pos).getVideoId());
                    startActivity(intent);
                }
            });

            ((TextView) v.findViewById(R.id.title)).setText(fInfo.getTitle());
            ((TextView) v.findViewById(R.id.date)).setText(fInfo.getPublishedAt());

            return v;
        }
    }


}
