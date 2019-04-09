package cn.wangjianlog.ipcstudy;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.wangjianlog.ipcstudy.impl.AIDLActivity;
import cn.wangjianlog.ipcstudy.impl.AIDLManagerActivity;
import cn.wangjianlog.ipcstudy.impl.BaseActivity;
import cn.wangjianlog.ipcstudy.impl.BinderPoolActivity;
import cn.wangjianlog.ipcstudy.impl.MessengerActivity;
import cn.wangjianlog.ipcstudy.impl.ProviderActivity;
import cn.wangjianlog.ipcstudy.impl.SocketActivity;
import cn.wangjianlog.ipcstudy.impl.fragment.MainFragment;
import cn.wangjianlog.ipcstudy.impl.fragment.SlideFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context mContext;
    private SlideFragment slideFragment;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Test", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.nav_text_color)));

        initFragment();

        firstFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_aidl) {
            AIDLActivity.start(mContext);
        } else if (id == R.id.nav_aidl_manager) {
            AIDLManagerActivity.start(mContext);
        } else if (id == R.id.nav_messenger) {
            MessengerActivity.start(mContext);
        } else if (id == R.id.nav_provider) {
            ProviderActivity.start(mContext);
        } else if (id == R.id.nav_socket) {
            SocketActivity.start(mContext);
        } else if (id == R.id.nav_binder_pool) {
            BinderPoolActivity.start(mContext);
        } else if (id == R.id.nav_slide) {
            switchFragment(R.id.container,slideFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragment(){
        mainFragment = new MainFragment();
        slideFragment = new SlideFragment();
    }

    private void firstFragment(){
        addFragment(R.id.container,mainFragment);
        currentFragment = mainFragment;
    }

}
