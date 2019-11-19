package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class LikeMusicPlayActivity extends AppCompatActivity {
    static DrawableManager DM=new DrawableManager();
    AsyncTask<?,?,?> searchTask;
    ArrayList<SearchData> sdata=new ArrayList<SearchData>();
   String music_str;

    final String serverKey="AIzaSyA0B72xByDrqzb68bZ0fQylnasgHDdGbMw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_music_play);
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        music_str=intent.getStringExtra("music_info");
        Log.d("d음악음악",music_str);

        searchTask=new LikeMusicPlayActivity.searchTask().execute();

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

            LikeMusicPlayActivity.StoreListAdapter mAdapter = new LikeMusicPlayActivity.StoreListAdapter(LikeMusicPlayActivity.this, R.layout.listview_music, sdata);
            searchlist.setAdapter(mAdapter);
        }
    }

    public JSONObject getUtube() {
        String music=music_str;
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

        public StoreListAdapter(Context context, int textViewResourseId, ArrayList<SearchData> items) {
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

                    Intent intent = new Intent(LikeMusicPlayActivity.this,
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

