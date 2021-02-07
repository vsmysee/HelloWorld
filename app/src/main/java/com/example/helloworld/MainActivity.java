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
        blogSite.put("https://tech.meituan.com/page/2.html", "h2.post-title > a");
        blogSite.put("http://www.ruanyifeng.com/blog/archives.html", "#alpha-inner > div.module-categories > div.module-content > ul.module-list > li > a");
        blogSite.put("https://insights.thoughtworks.cn/tag/featured/", "a[rel=bookmark]");
        blogSite.put("https://insights.thoughtworks.cn/", "a[rel=bookmark]");
        blogSite.put("https://thzt.github.io/archives/", "h2.post-title > a  > span");
        blogSite.put("https://draveness.me/", "article > a");
        blogSite.put("https://pingcap.com/blog-cn/", "h1.title > a");
        blogSite.put("https://blog.codingnow.com/", "h3.entry-header");
        blogSite.put("https://coolshell.cn", "h2.entry-title > a");
        blogSite.put("https://blogs.360.cn/", "h1.title > a");
        blogSite.put("https://codechina.org/", "ul.wp-block-latest-posts > li > a");
        blogSite.put("https://manateelazycat.github.io/index.html", "li.post-line > a.post-title");


        articleSite.put("https://www.36kr.com", "a.article-item-title");
        articleSite.put("https://www.ifanr.com/", "a.js-title-transform");
        articleSite.put("http://www.iheima.com", "a.title");
        articleSite.put("https://www.donews.com/", "div.content > span.title");
        articleSite.put("https://www.cnbeta.com/", "div.item > dl > dt > a");


        articleSite.put("https://toutiao.io/posts/hot/7", "h3.title > a");
        articleSite.put("https://jishuin.proginn.com/", "div.article-title > a");
        articleSite.put("https://jishuin.proginn.com/default/2", "div.article-title > a");
        articleSite.put("https://jishuin.proginn.com/default/3", "div.article-title > a");


        articleSite.put("http://www.cbdio.com/node_2570.htm", "p.cb-media-title > a");
        articleSite.put("http://www.woshipm.com/", "h2.post-title > a");
        articleSite.put("https://www.myzaker.com", "h2.article-title");
        articleSite.put("https://www.topjavablogs.com/", "a.itemLink");

        articleSite.put("https://amazonaws-china.com/cn/blogs/china/", "h2.blog-post-title > a");
        articleSite.put("https://amazonaws-china.com/cn/blogs/china/page/2/", "h2.blog-post-title > a");


        articleSite.put("https://aijishu.com/", "h3.text-body");

        articleSite.put("https://huanqiukexue.com/plus/list.php?tid=1", "div.astrtext > a >h4");
        articleSite.put("https://huanqiukexue.com/plus/list.php?tid=1&TotalResult=4849&PageNo=2", "div.astrtext > a >h4");
        articleSite.put("https://huanqiukexue.com/plus/list.php?tid=1&TotalResult=4849&PageNo=3", "div.astrtext > a >h4");


        articleSite.put("https://thenewstack.io/", "h2.small > a");
        articleSite.put("https://thenewstack.io/page/2", "h2.small > a");
        articleSite.put("https://thenewstack.io/page/3", "h2.small > a");


        articleSite.put("http://dockerone.com/", "h4 > a");
        articleSite.put("http://dockerone.com/sort_type-new__day-0__is_recommend-0__page-2", "h4 > a");
        articleSite.put("http://dockerone.com/sort_type-new__day-0__is_recommend-0__page-3", "h4 > a");


        articleSite.put("http://www.kejilie.com", "h3.am_list_title > a");
        articleSite.put("https://www.tmtpost.com/lists/latest_list_new", "li.part_post > div.info > a > h3");

        articleSite.put("https://www.infoworld.com", "div.post-cont > h3 > a");
        articleSite.put("https://dzone.com/", "h3.article-title > a");
        articleSite.put("https://www.geeksforgeeks.org/", "div.content > div.head > a");

        articleSite.put("https://segmentfault.com/news/", "h4.news__item-title");
        articleSite.put("https://www.cnblogs.com", "a.post-item-title");
        articleSite.put("https://www.donews.com/", "div.info > p.title");
        articleSite.put("https://news.cnblogs.com/", "h2 > a[target=_blank]");
        articleSite.put("https://news.cnblogs.com/n/page/2/", "h2 > a[target=_blank]");
        articleSite.put("https://www.hollischuang.com/page/1", "article > header > h2 > a");
        articleSite.put("https://www.hollischuang.com/page/2", "article > header > h2 > a");
        articleSite.put("https://www.hollischuang.com/page/3", "article > header > h2 > a");
        articleSite.put("https://www.hollischuang.com/page/4", "article > header > h2 > a");
        articleSite.put("https://www.williamlong.info/", "a[rel=bookmark]");
        articleSite.put("https://www.tuicool.com/ah/20/", "a[style=display: block]");
        articleSite.put("https://developer.ibm.com/zh/articles/", "h3.developer--card__title > span");
        articleSite.put("https://lobste.rs/", "a[rel=ugc noreferrer]");
        articleSite.put("https://news.ycombinator.com/", "a.storylink");
        articleSite.put("https://www.qbitai.com/", "div.text_box > h4 > a");


        articleSite.put("https://www.oschina.net/translate", "div.translate-item > div.content > a");
        articleSite.put("https://www.oschina.net/translate/widgets/_translate_index_list?category=0&tab=completed&sort=&p=2&type=ajax", "div.translate-item > div.content > a");
        articleSite.put("https://www.oschina.net/translate/widgets/_translate_index_list?category=0&tab=completed&sort=&p=3&type=ajax", "div.translate-item > div.content > a");

        articleSite.put("https://www.jdon.com", "div.important > div.info > a");

        articleSite.put("https://www.cncf.io/blog/", "p.archive-title > a");
        articleSite.put("https://www.cncf.io/blog/page/2/", "p.archive-title > a");

        for (int i = 1; i <= 4; i++) {
            articleSite.put("https://www.oschina.net/news/widgets/_news_index_generic_list?p=" + i + "&type=ajax", "h3 > a");
        }


        articleSite.put("https://www.oschina.net/translate", "div.translate-item > div.content > a");
        articleSite.put("https://www.oschina.net/translate/widgets/_translate_index_list?category=0&tab=completed&sort=&p=2&type=ajax", "div.translate-item > div.content > a");
        articleSite.put("https://www.oschina.net/translate/widgets/_translate_index_list?category=0&tab=completed&sort=&p=3&type=ajax", "div.translate-item > div.content > a");


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

                            for (String key : blogSite.keySet()) {

                                try {

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

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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