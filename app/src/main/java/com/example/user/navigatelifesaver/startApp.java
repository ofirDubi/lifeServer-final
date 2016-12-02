package com.example.user.navigatelifesaver;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class startApp extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String USERNAME;
    GlobalVars globalVars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);


        USERNAME = ((GlobalVars) this.getApplication()).getUSERNAME();
        globalVars = ((GlobalVars) getApplicationContext());

        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.diaglogo, "Diagnose Yourself");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_action_refresh, "List Symptoms");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.info, "Help");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.chat_icon, "Chat");
        drawerItem[4] = new ObjectDrawerItem(R.drawable.mapicon, "map");
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new simptome();
                break;
            case 1:
                fragment = new ReadFragment();
                break;
            case 2:
                fragment = new HelpFragment();
                break;
            case 3:
                startActivity(new Intent(startApp.this, ChatView.class));
                break;
            case 4:
                startActivity(new Intent(startApp.this, MapsActivity.class));
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager() ;
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            if(getActionBar() != null){
                getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
            }
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}