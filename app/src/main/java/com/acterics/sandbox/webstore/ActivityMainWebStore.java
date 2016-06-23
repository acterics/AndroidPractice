package com.acterics.sandbox.webstore;

import android.app.Fragment;
import android.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;


import com.acterics.sandbox.R;
import com.acterics.sandbox.utils.Utils;
import com.acterics.sandbox.webstore.database.StoreContentReaderContract;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


//TODO add orientation changing handler

public class ActivityMainWebStore extends AppCompatActivity {


    private final static String log = "MAIN_WEBSTORE_LOGGER";

    private Drawer.Result drawerResult= null;
    private static StoreContentReaderContract storeContentReaderContract;

    private FragmentTransaction fragmentTransaction = null;
    private Fragment currentFragment = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web_store);

        storeContentReaderContract = new StoreContentReaderContract(this);

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.web_store_fragment_layout, new FragmentDatabase());
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.web_store_toolbar);
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            Log.e(log, e.getMessage());
        }

        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                            .withName(R.string.database_admin)
                            .withIdentifier(R.string.database_admin)
                            .withIcon(FontAwesome.Icon.faw_database),
                        new PrimaryDrawerItem()
                            .withName(R.string.cart)
                            .withIcon(FontAwesome.Icon.faw_shopping_cart)
                            .withIdentifier(R.string.cart),

                        new DividerDrawerItem(),

                        new SecondaryDrawerItem()
                            .withName(R.string.options)
                            .withIcon(FontAwesome.Icon.faw_cog)
                            .withIdentifier(R.string.options),

                        new DividerDrawerItem(),

                        new SecondaryDrawerItem()
                            .withName(R.string.exit)
                            .withIdentifier(R.string.exit)
                            .withIcon(FontAwesome.Icon.faw_eraser)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        Utils.hideSoftKeyBoard(ActivityMainWebStore.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        fragmentTransaction = getFragmentManager().beginTransaction();



                        int identifier = 0;
                        try {
                            identifier = drawerItem.getIdentifier();
                        } catch (Exception e) {
                            Log.e(log, e.getMessage());
                        }


                        switch (identifier) {
                            case R.string.database_admin:
                                currentFragment = new FragmentDatabase();
                                break;
                            case R.string.cart:
                                currentFragment = new FragmentCart();
                                break;
                            case R.string.options:
                                break;
                            case R.string.exit:
                                ActivityMainWebStore.super.onBackPressed();
                                break;

                        }
                        if(currentFragment != null) {
                            fragmentTransaction.replace(R.id.web_store_fragment_layout, currentFragment);
                            fragmentTransaction.commit();
                        }
                    }
                })
                .withDrawerWidthDp(getResources().getInteger(R.integer.drawer_width))
                .build();
    }

    @Override
    public void onBackPressed() {
        if(drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }

    }



    public static StoreContentReaderContract getStoreContentReaderContract() {
        return storeContentReaderContract;
    }
}
