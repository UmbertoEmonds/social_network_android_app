package com.mireal.admin.mireal7;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.mireal.admin.mireal7.controller.MainActivityBroadcastReceiver;
import com.mireal.admin.mireal7.controller.BackgroundService;
import com.mireal.admin.mireal7.controller.POSTService;
import com.mireal.admin.mireal7.controller.PUTService;
import com.mireal.admin.mireal7.model.Message;
import com.mireal.admin.mireal7.model.Utilisateur;
import com.mireal.admin.mireal7.vue.MonCompte_Fragment;
import com.mireal.admin.mireal7.vue.QuoiDeNeuf_Fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView action_bar_tv;
    public static final String TAG = "AZERTY";
    private MainActivityBroadcastReceiver backgroundReceiver;
    private FrameLayout frameChargement;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        frameChargement.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        frameChargement = (FrameLayout)findViewById(R.id.frameChargement);
        frameChargement.setVisibility(View.VISIBLE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        action_bar_tv = (TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        action_bar_tv.setText("Quoi de neuf ?");

    }

    public void registerLoading() {
        IntentFilter filter;

        backgroundReceiver = new MainActivityBroadcastReceiver(this);
        filter=new IntentFilter();
        filter.addAction(BackgroundService.UTILISATEUR_SERVICE_FINISHED);
        filter.addAction(POSTService.POST_SERVICE_FINISHED);
        filter.addAction(PUTService.PUT_SERVICE_FINISHED);
        registerReceiver(backgroundReceiver, filter);
    }

    public void unRegisterLoading() {
        if (null!=backgroundReceiver)
            unregisterReceiver(backgroundReceiver);
        backgroundReceiver=null;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerLoading();
    }

    public void onStop() {
        // il est important de se désinscrire quand l'activité est arrêtée
        unRegisterLoading();
        super.onStop();
    }

    public void onUtilisateurServiceFinished(Utilisateur utilisateur, ArrayList<Message> messages) {

        // quand le chargement des messages est terminé
        Log.v(TAG, "Chargement terminé");

        QuoiDeNeuf_Fragment fragment = new QuoiDeNeuf_Fragment();

        Bundle bundle = new Bundle();

        bundle.putSerializable("utilisateur", utilisateur);
        bundle.putSerializable("messages", messages);

        fragment.setArguments(bundle);

        pushFragment(fragment);

        frameChargement.setVisibility(View.GONE);

    }

    public void onMessagePOSTServiceFinished()
    {
        Log.v(TAG, "Envoi terminé");
        Fragment fragment = null;
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }

    public void onPutLikeServiceFinished(){

        Log.v(TAG, "Envoi terminé");
        Fragment fragment = null;
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();

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

    private void pushFragment(Fragment newFragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment, newFragment);

        transaction.commit();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_quoideneuf) {

            action_bar_tv.setText("Quoi de neuf ?");


        } else if (id == R.id.nav_moncompte) {

            action_bar_tv.setText("Mon compte");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
