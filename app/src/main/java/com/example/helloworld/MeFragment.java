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

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {

    private ArrayAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me, container, false);

        final List<String> data = new ArrayList<>();

        final List<Blog> allRates = ((MainActivity) getActivity()).getAllRates();

        for (Blog allRate : allRates) {
            data.add(allRate.getName());
        }

        adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1, data);

        final ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final String url = allRates.get(position).getKey();

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


                final BlogDataSource blogDataSource = ((MainActivity) getActivity()).getBlogDataSource();


                PopupMenu popup = new PopupMenu(getActivity(), view);//第二个参数是绑定的那个view
//获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
//填充菜单
                inflater.inflate(R.menu.delete, popup.getMenu());
//绑定菜单项的点击事件

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
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


        return view;
    }

    public void refresh() {
        adapter.notifyDataSetChanged();
    }
}