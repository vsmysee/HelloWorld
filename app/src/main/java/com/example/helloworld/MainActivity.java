package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    final List<String> blogData = new ArrayList<>();
    final List<String> articleData = new ArrayList<>();

    static Map<String, String> blogSite = new HashMap<>();
    static Map<String, String> articleSite = new HashMap<>();

    static {
        blogSite.put("http://www.yinwang.org", "li.list-group-item > a");
        blogSite.put("https://afoo.me/posts.html", "header.title > div > h2 > a");
        blogSite.put("https://tech.meituan.com/", "h2.post-title > a");
        blogSite.put("http://www.ruanyifeng.com/blog/archives.html", "#alpha-inner > div.module-categories > div.module-content > ul.module-list > li > a");
        blogSite.put("https://insights.thoughtworks.cn/tag/featured/", "a[rel=bookmark]");
        blogSite.put("https://insights.thoughtworks.cn/", "a[rel=bookmark]");


        articleSite.put("https://www.36kr.com", "a.article-item-title");
        articleSite.put("https://www.ifanr.com/", "a.js-title-transform");
        articleSite.put("https://toutiao.io/posts/hot/7", "h3.title > a");
        articleSite.put("http://www.kejilie.com", "h3.am_list_title > a");
        articleSite.put("https://www.tmtpost.com/lists/latest_list_new", "li.part_post > div.info > a > h3");
        articleSite.put("https://segmentfault.com/news/", "h4.news__item-title");
        articleSite.put("https://www.tuicool.com/ah/20/", "a[style=display: block]");
        articleSite.put("https://www.jdon.com/", "div.important > div.info > a");
        articleSite.put("https://www.geeksforgeeks.org/", "div.content > div.head > a");


    }


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

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                for (String key : blogSite.keySet()) {

                                    Document document = Jsoup.connect(key).get();

                                    Elements items = document.select(blogSite.get(key));
                                    for (Element item : items) {
                                        blogData.add(item.text());
                                        data.add(item.text());
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                        }

                    }).start();


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

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                for (String key : articleSite.keySet()) {

                                    Document document = Jsoup.connect(key).get();

                                    Elements items = document.select(articleSite.get(key));
                                    for (Element item : items) {
                                        articleData.add(item.text());
                                        data.add(item.text());
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }).start();


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