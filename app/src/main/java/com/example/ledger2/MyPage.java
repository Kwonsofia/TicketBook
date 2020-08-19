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
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MyPage extends AppCompatActivity {
    final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
    //onCreate외에도 다른 함수에서 사용하기 위해 밖으로 빼주었음.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        /*-------------------Hooks---------------------*/
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyPage_PageAdapter myPage_pageAdapter = new MyPage_PageAdapter(getSupportFragmentManager(), 4);


        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        ImageView imageView_tabCalendar = findViewById(R.id.calendar_page);
        ImageView imageView_tabAccount = findViewById(R.id.ledger);
        ImageView imageView_tabMyPage = findViewById(R.id.my_page);

        /*-------------------TabImage_BOTTOMBAR---------------------*/
        imageView_tabCalendar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentone = new Intent(MyPage.this, Calendar.class);
                startActivity(intentone);
            }
        });
        imageView_tabAccount.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttwo = new Intent(MyPage.this, LedgerActivity.class);
                startActivity(intenttwo);
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
        navigationView.bringToFront(); /////??????????????????????????????????????????????

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return false;
//            }
//        });
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        ImageView openMenuImage = (ImageView) findViewById(R.id.icon_menu);
        // 메뉴 이미지 클릭 시 메뉴 열리게 함.
        openMenuImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (!drawer.isDrawerOpen(Gravity.RIGHT)) {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        navigationView.setCheckedItem(R.id.home);


    } //onCreate()

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    } //onBackPressed()


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home:
                Intent intent_home = new Intent(MyPage.this, Calendar.class);
                startActivity(intent_home);
                break;
            case R.id.wishlist:
                Intent intent_wishlist = new Intent(MyPage.this, MyPage_WishList.class);
                startActivity(intent_wishlist);
                break;
            case R.id.logout:
                Toast.makeText(this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        //메뉴가 선택되면 행해지기 전에 Drawer가 닫힘.
        return true;
    }

}

