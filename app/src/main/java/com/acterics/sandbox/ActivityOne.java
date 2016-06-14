package com.acterics.sandbox;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.acterics.sandbox.utils.Utils;
import com.acterics.sandbox.webstore.MainWebStoreActivity;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

//TODO add fragments
public class ActivityOne extends AppCompatActivity {
    private static final String log = "ACTIVITY_ONE_LOGGER";

    private Drawer.Result drawerResult = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);



        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
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
                                .withIdentifier(R.string.activity_one_title)
                                .withName(R.string.main_page)
                                .withIcon(FontAwesome.Icon.faw_magic),
                        new PrimaryDrawerItem()
                                .withIdentifier(R.string.web_store)
                                .withName(R.string.web_store)
                                .withIcon(FontAwesome.Icon.faw_cart_plus),
                        new PrimaryDrawerItem()
                                .withName(R.string.chat)
                                .withIdentifier(R.string.chat)
                                .withIcon(FontAwesome.Icon.faw_wechat),
                        new DividerDrawerItem(),

                        new SecondaryDrawerItem()
                                .withName(R.string.options)
                                .withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem()
                                .withName(R.string.help)
                                .withIcon(FontAwesome.Icon.faw_question_circle),
                        new DividerDrawerItem(),

                        new SecondaryDrawerItem()
                                .withIdentifier(R.string.open_source)
                                .withName(R.string.open_source)
                                .withIcon(FontAwesome.Icon.faw_github)


                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        Utils.hideSoftKeyBoard(ActivityOne.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(ActivityOne.this, ActivityOne.this.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        int identifier = 0;
                        try {
                            identifier = drawerItem.getIdentifier();
                        } catch (Exception e) {
                            Log.e(log, e.getMessage());
                        }
                        switch (identifier) {
                            case R.string.web_store:
                                Intent intent = new Intent(ActivityOne.this, MainWebStoreActivity.class);
                                startActivity(intent);
                                break;
                            case R.string.open_source:
                                Uri address = Uri.parse("https://github.com/acterics/AndroidPractice");
                                Intent openLinkIntent = new Intent(Intent.ACTION_VIEW, address);
                                startActivity(openLinkIntent);
                                break;
                            default:
                                Toast.makeText(ActivityOne.this, R.string.toast_unhandled_item, Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .withDrawerWidthDp(300)
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

    @Override
    protected void onResume() {
        drawerResult.setSelection(0);
        super.onResume();
    }


}


