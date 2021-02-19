package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final List<String> blogData = new ArrayList<>();
    final List<String> articleData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final List<String> data = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        Button refreshBtn = findViewById(R.id.article_refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.clear();
                adapter.notifyDataSetChanged();
            }
        });

        Button blogBtn = findViewById(R.id.blog_button);
        blogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.clear();

                if (blogData.size() == 0) {

                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    asyncHttpClient.get("http://myfiledata.test.upcdn.net/data/data.txt", new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            System.out.println(responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                JSONArray blog = jsonObject.getJSONArray("blog");
                                for (int i = 0; i < blog.length(); i++) {
                                    data.add(blog.getString(i));
                                    blogData.add(blog.getString(i));
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });


                } else {


                    data.clear();
                    for (String blogDatum : blogData) {
                        data.add(blogDatum);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });


                }
            }
        });


        final Button articleBtn = findViewById(R.id.article_button);
        articleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.clear();

                if (articleData.size() == 0) {

                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    asyncHttpClient.get("http://myfiledata.test.upcdn.net/data/data.json", new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            System.out.println(responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                JSONArray blog = jsonObject.getJSONArray("news");
                                for (int i = 0; i < blog.length(); i++) {
                                    data.add(blog.getString(i));
                                    articleData.add(blog.getString(i));
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });


                } else {


                    for (String item : articleData) {
                        data.add(item);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });


                }
            }
        });

    }
}