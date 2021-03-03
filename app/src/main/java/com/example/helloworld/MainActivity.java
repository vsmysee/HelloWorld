package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final List<String> blogData = new ArrayList<>();
    final List<String> blogUrl = new ArrayList<>();
    final List<String> articleData = new ArrayList<>();
    final List<String> newsData = new ArrayList<>();
    final List<String> bookData = new ArrayList<>();


    private BlogDataSource blogDataSource;

    private static int TAB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blogDataSource = new BlogDataSource(this);
        blogDataSource.open();

        final List<String> data = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);


        Button collect = findViewById(R.id.collect);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAB = 5;
                data.clear();
                List<Blog> allRates = blogDataSource.getAllRates();

                for (Blog allRate : allRates) {
                    data.add(allRate.getName());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });


        Button me = findViewById(R.id.me_button);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, BlogActivity.class);
                startActivity(intent);

            }
        });


        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (TAB == 5) {

                    final String url = blogDataSource.getAllRates().get(position).getKey();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                            intent.putExtra("html", url);
                            startActivity(intent);

                        }
                    }).start();
                }

                if (TAB == 4) {

                    final String url = blogUrl.get(position);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                            intent.putExtra("html", url);
                            startActivity(intent);

                        }
                    }).start();

                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final String name = data.get(position);
                final String url = blogUrl.get(position);


                PopupMenu popup = new PopupMenu(MainActivity.this, view);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                if (TAB == 5) {
                    inflater.inflate(R.menu.delete, popup.getMenu());
                }
                if (TAB == 4) {
                    inflater.inflate(R.menu.collect, popup.getMenu());
                }
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.collect) {
                            blogDataSource.createRate(url, name);
                        }
                        if (item.getItemId() == R.id.delete) {
                            blogDataSource.deleteRate(blogDataSource.getAllRates().get(position));
                            data.remove(position);
                            adapter.notifyDataSetChanged();

                        }
                        return false;
                    }
                });
                //显示(这一行代码不要忘记了)
                popup.show();
                return false;
            }
        });


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String day = format.format(new Date());

        Button newsButton = findViewById(R.id.news_button);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TAB = 1;

                data.clear();

                if (newsData.size() == 0) {

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

                                    if (i % 50 == 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
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

                TAB = 4;

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
                                    JSONObject jo = array.getJSONObject(i);
                                    data.add(jo.getString("name"));
                                    blogData.add(jo.getString("name"));
                                    if (jo.has("url")) {
                                        blogUrl.add(jo.getString("url"));
                                    }

                                    if (i % 50 == 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
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

                TAB = 2;

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

                                    if (i % 50 == 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
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

                TAB = 3;

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

                                    if (i % 50 == 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
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