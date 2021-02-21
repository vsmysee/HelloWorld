package com.example.helloworld;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final List<String> blogData = new ArrayList<>();
    final List<String> articleData = new ArrayList<>();
    final List<String> newsData = new ArrayList<>();
    final List<String> bookData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button me = findViewById(R.id.me_button);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, BlogActivity.class);
                startActivity(intent);

            }
        });



        final List<String> data = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String day = format.format(new Date());

        Button newsButton = findViewById(R.id.news_button);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.clear();

                if (blogData.size() == 0) {

                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    String url = "http://myfiledata.test.upcdn.net/data/" + day + "-news.json";
                    asyncHttpClient.get(url, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            System.out.println(responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {


                                JSONArray array = new JSONArray(responseString);
                                for (int i = 0; i < array.length(); i++) {
                                    data.add(array.getString(i));
                                    newsData.add(array.getString(i));
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
                    for (String blogDatum : newsData) {
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

        Button blogBtn = findViewById(R.id.blog_button);
        blogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.clear();

                if (blogData.size() == 0) {

                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    String url = "http://myfiledata.test.upcdn.net/data/" + day + "-blogs.json";

                    asyncHttpClient.get(url, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            System.out.println(responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {


                                JSONArray array = new JSONArray(responseString);
                                for (int i = 0; i < array.length(); i++) {
                                    data.add(array.getString(i));
                                    blogData.add(array.getString(i));
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
                    String url = "http://myfiledata.test.upcdn.net/data/" + day + "-articles.json";

                    asyncHttpClient.get(url, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            System.out.println(responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {


                                JSONArray array = new JSONArray(responseString);
                                for (int i = 0; i < array.length(); i++) {
                                    data.add(array.getString(i));
                                    articleData.add(array.getString(i));
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

        final Button bookBtn = findViewById(R.id.book_button);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data.clear();

                if (bookData.size() == 0) {

                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    String url = "http://myfiledata.test.upcdn.net/data/" + day + "-books.json";
                    asyncHttpClient.get(url, new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            System.out.println(responseString);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {
                                JSONArray array = new JSONArray(responseString);
                                for (int i = 0; i < array.length(); i++) {
                                    data.add(array.getString(i));
                                    bookData.add(array.getString(i));
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


                    for (String item : bookData) {
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