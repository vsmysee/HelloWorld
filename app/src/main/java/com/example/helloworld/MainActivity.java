package com.example.helloworld;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager2 viewPager = (ViewPager2) findViewById(R.id.viewPager2);
        viewPager.setUserInputEnabled(false);

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter( getSupportFragmentManager(),getLifecycle());

        viewPager.setAdapter(adapter);

        RadioGroup radiogroup = (RadioGroup)findViewById(R.id.llNav);
        radiogroup.check(R.id.rb0);

        viewPager.setCurrentItem(0,false);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb0) {
                    viewPager.setCurrentItem(0,false);
                }

                if (checkedId == R.id.rb1) {
                    viewPager.setCurrentItem(1,false);
                }

                if (checkedId == R.id.rb2) {
                    viewPager.setCurrentItem(2,false);
                }

                if (checkedId == R.id.rb3) {
                    viewPager.setCurrentItem(3,false);
                }

            }
        });


    }
}