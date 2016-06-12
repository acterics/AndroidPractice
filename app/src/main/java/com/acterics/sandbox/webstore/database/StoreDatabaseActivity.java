package com.acterics.sandbox.webstore.database;

import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.acterics.sandbox.R;
import com.acterics.sandbox.webstore.MainWebStoreActivity;

public class StoreDatabaseActivity extends AppCompatActivity {

    private final static String log = "DATABASE_LOGGER";
    private ListView storeContentList = null;
    private ListView cartContentList = null;
    private ImageButton addButton = null;
    private ImageButton removeButton = null;
    private View selectedView = null;

    private StoreContentReaderContract storeContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_database);

        storeContentList = (ListView) findViewById(R.id.store_content_list);
        cartContentList = (ListView) findViewById(R.id.cart_list);
        addButton = (ImageButton) findViewById(R.id.add_button);
        removeButton = (ImageButton) findViewById(R.id.remove_button);

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        assert cancelButton != null;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        storeContent = MainWebStoreActivity.getStoreContentReaderContract();
        String[] from = {StoreContentReaderContract.StoreContentEntry.COLUMN_NAME_PRODUCT, StoreContentReaderContract.StoreContentEntry.COLUMN_NAME_PRICE};
        int[] to = {R.id.product_name_field, R.id.product_price_field};
        try {
            storeContentList.setAdapter(new SimpleCursorAdapter(this, R.layout.store_content_item, storeContent.read(), from, to));
            cartContentList.setAdapter(new SimpleCursorAdapter(this, R.layout.store_content_item, null, from, to));
        } catch (Exception e) {
            Log.e(log, e.getMessage());
        }
    }

//        storeContentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //onStoreItemClick(parent, view, position, id);
//            }
//        });
//
//        cartContentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //onCartItemClick(parent, view, position, id);
//            }
//        });
//
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(selectedView != null && !isInCart(selectedView)) {
////                    clearItemColor(selectedView);
////                    cartContentList.addFooterView(selectedView);
////                    selectedView = null;
////                }
//            }
//        });
//
//        removeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if(selectedView != null) {
////                    clearItemColor(selectedView);
////                    cartContentList.removeFooterView(selectedView);
////                    selectedView = null;
////                }
//            }
//        });
//
//    }

//    //TODO implement cart checking
//    private boolean isInCart(View v) {
//       for(int i = 0; i < cartContentList.getCount(); i++) {
//           if(isEqualItems(v, (View) cartContentList.getItemAtPosition(i))) {
//               Toast toast = Toast.makeText(this, R.string.toast_already_added_in_list, Toast.LENGTH_SHORT);
//               toast.show();
//               return true;
//           }
//
//       }
//        Log.i(log, "Items are different");
//        return false;
//    }
//
//
//    //TODO implement view comparing
//    private static boolean isEqualItems(View v1, View v2) {
//        return false;
//    }
//
//    private void clearItemColor(View v) {
//        if(v != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                v.setBackgroundColor(getColor(R.color.itemColor));
//            }
//            else {
//                v.setBackgroundColor(getResources().getColor(R.color.itemColor));
//            }
//        }
//    }
//
//    private void selectItem(View v ) {
//        if(v != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                v.setBackgroundColor(getColor(R.color.selectedItemColor));
//            }
//            else {
//                v.setBackgroundColor(getResources().getColor(R.color.selectedItemColor));
//            }
//        }
//    }
//
//    private void onStoreItemClick(AdapterView<?> parent, View view, int position, long id) {
//        clearItemColor(selectedView);
//        selectedView = view;
//        selectItem(selectedView);
//
//        addButton.setVisibility(View.VISIBLE);
//        removeButton.setVisibility(View.VISIBLE);
//        addButton.setEnabled(true);
//        removeButton.setEnabled(false);
//    }
//
//    private void onCartItemClick(AdapterView<?> parent, View view, int position, long id) {
//        addButton.setEnabled(false);
//        removeButton.setEnabled(true);
//
//        clearItemColor(selectedView);
//        selectedView = view;
//        selectItem(selectedView);
//
//    }
}
