package com.pshegde.instagramclient;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends ActionBarActivity {

    public static final String CLIENT_ID="09cd591825394bbc9c99380d086a4012";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photos);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_layout);
        getSupportActionBar().hide();

        //send out api requests to photos android async http and picasso android
        photos = new ArrayList<>();
        //create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        //find the listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //set the adapter binding it to the listview
        lvPhotos.setAdapter(aPhotos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        LinearLayout myTitleBar = (LinearLayout) findViewById(R.id.title_bar_layout);
////        TextView tvTitleBar = (TextView)myTitleBar.findViewById(R.id.tvTitleBar);
////        tvTitleBar.setText(R.string.app_name);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.title_bar_layout);
//        getSupportActionBar().
//
//
//        //fetch the popular photos
//        fetchPopularPhotos();
    }

//    public void fetchTimelineAsync(int page) {
//        client.getHomeTimeline(0, new JsonHttpResponseHandler() {
//            public void onSuccess(JSONArray json) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//                aPhotos.clear();
//                // ...the data has come back, add new items to your adapter...
//                aPhotos.addAll(...);
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeContainer.setRefreshing(false);
//            }
//
//            public void onFailure(Throwable e) {
//                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
//            }
//        });
//    }

    //trigger api request
    public void fetchPopularPhotos(){
        //popular: https://api.instagram.com/v1/media/popular?access_token=
        //client_id: 09cd591825394bbc9c99380d086a4012


        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //create network client
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,null, new JsonHttpResponseHandler() {

            //on success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                //response
                //Log.i("DEBUG", response.toString());
                JSONArray photosJSON = null;
                try{
                    photosJSON = response.getJSONArray("data");
                    //iterate array of posts
                    for(int i=0;i<photosJSON.length();i++){
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        //decode attributes of json into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        //Author name: {"data" => [x] => "user" => "username"}
                        if(photoJSON.getJSONObject("user") != null)
                            photo.setUsername(photoJSON.getJSONObject("user").getString("username"));
                        //caption: {"data" => [x] => "caption" => "text"}
                        if(photoJSON.getJSONObject("caption") != null)
                            photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                        //Type: {"data" => [x] => "type"} {"image" or "video"}
                        //URL:{"data"=>[x] => "images" => "standard resolution" => "url"}
                        photo.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        if(photoJSON.getJSONObject("likes") != null)
                            photo.setLikeCount(photoJSON.getJSONObject("likes").getInt("count"));
                        photo.setRelativePostingTime(DateUtils.getRelativeTimeSpanString(photoJSON.getLong("created_time") * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString());
                        if(photoJSON.getJSONObject("user") != null)
                            photo.setUserProfilePic(photoJSON.getJSONObject("user").getString("profile_picture"));
                        if(photoJSON.getJSONObject("comments") != null) {
                            JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
                            for (int j = 0; j < commentsJSON.length(); j++) {
                                JSONObject commentJSON = commentsJSON.getJSONObject(j);
                                InstagramComment comment = new InstagramComment();
                                comment.setCreatedTime(DateUtils.getRelativeTimeSpanString(commentJSON.getLong("created_time") * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString());
                                comment.setText(commentJSON.getString("text"));
                                comment.setUsername(commentJSON.getJSONObject("from").getString("username"));
                                photo.addComment(comment);
                            }
                        }
                        photos.add(photo);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

                //callback
                aPhotos.notifyDataSetChanged();

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
