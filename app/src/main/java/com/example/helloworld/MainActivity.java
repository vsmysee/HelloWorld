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
        articleSite.put("https://www.36kr.com", "a.article-item-title");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final List<String> data = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

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
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

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
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });


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