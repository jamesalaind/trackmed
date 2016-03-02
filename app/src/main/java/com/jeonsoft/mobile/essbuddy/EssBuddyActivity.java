package com.jeonsoft.mobile.essbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jeonsoft.mobile.essbuddy.data.context.ProfileContext;
import com.jeonsoft.mobile.essbuddy.networking.HttpResponse;
import com.jeonsoft.mobile.essbuddy.utils.AsyncHttpRequest;
import com.jeonsoft.mobile.essbuddy.utils.AsyncHttpRequestListener;

public class EssBuddyActivity extends BaseCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvProfileName, tvCompanyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ess_buddy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AsyncHttpRequest request = new AsyncHttpRequest("http://10.0.0.82:3000/api/employees/32", "GET", new AsyncHttpRequestListener() {
                    @Override
                    public void complete(HttpResponse response) {
                        Snackbar.make(view, response.getResponseCode() + ": " + response.getResponseMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void failure(HttpResponse response, String message) {
                        Snackbar.make(view, response != null ? response.getResponseCode() + ": " : "" + message, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void request() {
                        Toast.makeText(EssBuddyActivity.this, "Fetching employee list.", Toast.LENGTH_SHORT).show();
                    }
                });
                request.execute();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getHeaderView(0).setOnClickListener(onProfileClickListener);

        tvCompanyName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvCompanyName);
        tvProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvProfileName);
    }

    private void initProfileInfo() {
        tvProfileName.setText(ProfileContext.getInstance().getProfileName());
        tvCompanyName.setText(ProfileContext.getInstance().getCompanyName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        initProfileInfo();
    }

    private final View.OnClickListener onProfileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EssBuddyActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    };

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ess_buddy_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        setNavigationContent(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationContent(MenuItem item) {
        String name = item.getTitleCondensed().toString();
        try {
            Class<?> cls = Class.forName(getPackageName().concat(".navfragments.").concat(name.concat("Fragment")));
            Fragment fragment = (Fragment) cls.getConstructor().newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        } catch (ClassNotFoundException ex) {
            logE("Cannot find class: " + name.concat("Fragment") + ". " + ex.getMessage());
        } catch (NoSuchMethodException ex) {
            logE("No such method. " + ex.getMessage());
        } catch (Exception ex) {
            logE(ex.getMessage());
        }
    }
}
