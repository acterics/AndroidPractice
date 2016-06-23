package com.acterics.sandbox.webstore;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.acterics.sandbox.R;
import com.acterics.sandbox.webstore.database.DialogEntryInput;

import static com.acterics.sandbox.webstore.database.StoreContentReaderContract.StoreContentEntry.COLUMN_NAME_PRICE;
import static com.acterics.sandbox.webstore.database.StoreContentReaderContract.StoreContentEntry.COLUMN_NAME_PRODUCT;
import static com.acterics.sandbox.webstore.database.StoreContentReaderContract.StoreContentEntry.COLUMN_NAME_SELL_DATE;

/**
 * Created by User on 12.06.2016.
 */
public class FragmentDatabase extends Fragment {

    ListView databaseContentList = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View v = inflater.inflate(R.layout.fragment_database, null);

        databaseContentList = (ListView) v.findViewById(R.id.database_content_list);



        Button addEntryButton = (Button) v.findViewById(R.id.add_entry_button);
        Button clearDatabaseButton = (Button) v.findViewById(R.id.clear_database_button);
        assert addEntryButton != null;

        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent entryInputDialogIntent = new Intent(getActivity(), DialogEntryInput.class);
                startActivity(entryInputDialogIntent);
            }
        });

        clearDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onPause();
                    ActivityMainWebStore.getStoreContentReaderContract().clear(FragmentDatabase.this.getActivity());
            }
        });


        return v;

    }

    @Override
    public void onResume() {
        refreshDatabase();
        super.onResume();
    }


    private void refreshDatabase() {
        String[] from = {COLUMN_NAME_PRODUCT, COLUMN_NAME_PRICE, COLUMN_NAME_SELL_DATE};
        int[] to = {R.id.product_name_field, R.id.product_price_field, R.id.product_date_field};

        try {
            Cursor databaseCursor = ActivityMainWebStore.getStoreContentReaderContract().read();
            if(databaseCursor == null) {
                ActivityMainWebStore.getStoreContentReaderContract().create();
                databaseContentList.setAdapter(null);
            }
            else {
                databaseContentList.setAdapter(new SimpleCursorAdapter(getActivity(),
                        R.layout.store_content_item,
                        ActivityMainWebStore.getStoreContentReaderContract().read(),
                        from,
                        to));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
