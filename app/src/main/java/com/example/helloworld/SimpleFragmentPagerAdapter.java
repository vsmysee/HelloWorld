package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> lists = new ArrayList<>();

    {
        lists.add(new HomeFragment());
        lists.add(new BooksFragment());
        lists.add(new ArticlesFragment());
        lists.add(new BlogFragment());
    }


    public SimpleFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       return lists.get(position);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


}