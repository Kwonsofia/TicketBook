package com.example.ledger2;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MyPage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        /*-------------------Hooks---------------------*/
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPage_PageAdapter myPage_pageAdapter = new MyPage_PageAdapter(getSupportFragmentManager(), 4);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        ImageView imageView_tabCalendar = findViewById(R.id.calendar_page);
        ImageView imageView_tabAccount = findViewById(R.id.ledger);
        ImageView imageView_tabMyPage = findViewById(R.id.my_page);

        /*-------------------TabImage_BOTTOMBAR---------------------*/
        imageView_tabCalendar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTabone = new Intent(MyPage.this,Calendar.class);
                startActivity(intentTabone);
            }
        });
        imageView_tabAccount.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTabtwo = new Intent(MyPage.this,Calendar.class);
                startActivity(intentTabtwo);
            }
        });



        /*-------------------ToolBar---------------------*/
        setSupportActionBar(toolbar);

        /*-------------------ViewPager---------------------*/
        viewPager.setAdapter(myPage_pageAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        /*-------------------TabLayout---------------------*/
        tabLayout.addTab(tabLayout.newTab().setText("뮤지컬"));
        tabLayout.addTab(tabLayout.newTab().setText("연극"));
        tabLayout.addTab(tabLayout.newTab().setText("전시회"));
        tabLayout.addTab(tabLayout.newTab().setText("기타"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*-------------------Navigation Drawer Menu---------------------*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ImageView openMenuImage = (ImageView) findViewById(R.id.icon_menu) ;
        // 메뉴 이미지 클릭 시 메뉴 열리게 함.
        openMenuImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout) ;
                if (!drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.openDrawer(Gravity.RIGHT) ;
                }
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("test", "onOptionsItemSelected - 메뉴항목을 클릭했을 때 호출됨");

        int id = item.getItemId();


        switch(id) {
            case R.id.home:
                Toast.makeText(getApplicationContext(), "홈 메뉴 클릭",
                        Toast.LENGTH_SHORT).show();
                Intent intentmenuhome = new Intent(MyPage.this,Calendar.class);
                startActivity(intentmenuhome);
                return true;
            case R.id.wistlist:
                Toast.makeText(getApplicationContext(), "위시리스트 메뉴 클릭",
                        Toast.LENGTH_SHORT).show();
                Intent intentmenuwishlist = new Intent(MyPage.this,MyPage_WishList.class);
                startActivity(intentmenuwishlist);
                return true;
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "로그아웃되었습니다.",
                        Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

