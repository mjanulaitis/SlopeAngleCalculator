package com.avatech.slopeangle.views;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avatech.slopeangle.R;
import com.avatech.slopeangle.viewmodels.SlopeAngleViewModel;

import butterknife.Bind;

//The main activity.  Displays a Toolbar, NavigationView and Fragment.
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    public static SlopeAngleViewModel viewModel;
    private static Fragment currentFragment;
    private static AboveSlopeFragment aboveSlopeFragment;
    private static OnSlopeFragment onSlopeFragment;
    private static SideSlopeFragment sideSlopeFragment;
    private static NotImplementedYetFragment notImplementedYetFragment;

    public MainActivity()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null)
            navigationView.getMenu().performIdentifierAction(R.id.nav_above_slope, 0);

        if (viewModel == null)
            viewModel = new SlopeAngleViewModel(this);
    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
////        if (currentFragment != null && currentFragment == onSlopeFragment)
////            viewModel.onResume(this);
//    }

    public void onResumeViewModel()
    {
        viewModel.onResume(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            Toast.makeText(this, R.string.not_implemented_yet, Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        currentFragment = null;

        switch (id)
        {
            case R.id.nav_above_slope:
            {
                if (aboveSlopeFragment == null)
                    aboveSlopeFragment = new AboveSlopeFragment();
                currentFragment = aboveSlopeFragment;
                break;
            }
            case R.id.nav_on_slope:
            {
                if (onSlopeFragment == null)
                {
                    onSlopeFragment = new OnSlopeFragment();
                    onSlopeFragment.setActivity(this);
                }
                currentFragment = onSlopeFragment;
                break;
            }
            case R.id.nav_side_slope:
            {
                if (sideSlopeFragment == null)
                    sideSlopeFragment = new SideSlopeFragment();
                currentFragment = sideSlopeFragment;
                break;
            }
            default:
            {
                if (notImplementedYetFragment == null)
                    notImplementedYetFragment = new NotImplementedYetFragment();
                currentFragment = notImplementedYetFragment;
                break;
            }
        }

        displayFragment(currentFragment);
        return true;
    }

    //Displays the passed fragment.
    private void displayFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public void onCalculate(View view)
    {
        startActivity(new Intent(this, AboveSlopeCameraActivity.class));
    }
}
