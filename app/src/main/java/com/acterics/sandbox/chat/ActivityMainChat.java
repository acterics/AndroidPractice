package com.acterics.sandbox.chat;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.acterics.sandbox.ActivityOne;
import com.acterics.sandbox.R;
import com.acterics.sandbox.utils.Utils;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public class ActivityMainChat extends AppCompatActivity {

    private final static String log = "CHAT_ACTIVITY_LOG";
    private Drawer.Result drawerResult = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_main_chat);


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.chat_fragment_layout, new FragmentLogin());
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Log.e(log, e.getMessage());
        }



        drawerResult = new Drawer()
                .withActivity(this)
                .withHeader(R.layout.drawer_header)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                            .withName(R.string.contacts)
                            .withIcon(FontAwesome.Icon.faw_book)
                            .withIdentifier(R.string.contacts),
                        new PrimaryDrawerItem()
                            .withName(R.string.options)
                            .withIdentifier(R.string.options)
                            .withIcon(FontAwesome.Icon.faw_cog)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        Utils.hideSoftKeyBoard(ActivityMainChat.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }
                })
                .withDrawerWidthDp(getResources().getInteger(R.integer.drawer_width))
                .build();






    }


    //TODO create mutual class to avoid code duplication
    @Override
    public void onBackPressed() {
        if(drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
