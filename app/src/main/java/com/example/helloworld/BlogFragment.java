package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class BlogFragment extends Fragment {

    private Map<String, String> urlMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blogs, container, false);

        final List<String> data = new ArrayList<>();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String day = format.format(new Date());

        final ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, data);

        final ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final String url = urlMap.get(data.get(position));

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getActivity(), ShowActivity.class);
                        intent.putExtra("html", url);
                        startActivity(intent);

                    }
                }).start();


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final String name = data.get(position);
                final String url = urlMap.get(name);

                PopupMenu popup = new PopupMenu(getActivity(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.collect, popup.getMenu());


                final BlogDataSource blogDataSource = ((MainActivity) getActivity()).getBlogDataSource();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.collect) {
                            blogDataSource.createRate(url, name);

                            List<Fragment> fragments = getFragmentManager().getFragments();
                            for (Fragment fragment : fragments) {
                                if (fragment instanceof MeFragment) {
                                    ((MeFragment) fragment).refresh();
                                }
                            }
                        }

                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });

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

                    RandomHelper helper = new RandomHelper();
                    for (int i = 0; i < array.length(); i++) {

                        int index = helper.getIndex(array.length() + 1);

                        JSONObject jo = array.getJSONObject(index);
                        String name = jo.getString("name");
                        data.add(name);
                        urlMap.put(name, jo.getString("url"));

                        if (i % 50 == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
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


        return view;
    }
}