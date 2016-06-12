package com.acterics.sandbox.webstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.acterics.sandbox.R;
import com.acterics.sandbox.webstore.database.EntryInputActivity;
import com.acterics.sandbox.webstore.database.StoreContentReaderContract;
import com.acterics.sandbox.webstore.database.StoreDatabaseActivity;

import static com.acterics.sandbox.webstore.database.StoreContentReaderContract.*;



public class MainWebStoreActivity extends AppCompatActivity implements View.OnClickListener{


    private final static String log = "MAIN_WEBSTORE_LOGGER";

    public static StoreContentReaderContract getStoreContentReaderContract() {
        return storeContentReaderContract;
    }

    private static StoreContentReaderContract storeContentReaderContract;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_web_store);

        ImageButton cartButton = (ImageButton) findViewById(R.id.cart_button);
        ImageButton entryInputButton = (ImageButton) findViewById(R.id.entry_input_button);
        assert cartButton != null;
        assert entryInputButton != null;

        cartButton.setOnClickListener(this);
        entryInputButton.setOnClickListener(this);

        storeContentReaderContract = new StoreContentReaderContract(this);

    }

    @Override
    public void onClick(View v) {
        Log.i(log, "Item clicked with id " + v.getId());
        Intent intent;
        switch (v.getId()) {
            case R.id.cart_button :
                intent= new Intent(this, StoreDatabaseActivity.class);
                startActivity(intent);
                break;
            case R.id.entry_input_button :
                intent = new Intent(this, EntryInputActivity.class);
                startActivity(intent);
                break;
        }
    }
}
