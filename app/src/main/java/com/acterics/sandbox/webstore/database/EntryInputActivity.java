package com.acterics.sandbox.webstore.database;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acterics.sandbox.R;
import com.acterics.sandbox.utils.Utils;

public class EntryInputActivity extends AppCompatActivity {

    private final static String log = "ENTRY_INPUT_LOGGER";

    private EditText productNameInput;
    private EditText productPriceInput;
    private EditText productDateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_input);

        productDateInput = (EditText) findViewById(R.id.product_date_input);
        productNameInput = (EditText) findViewById(R.id.product_name_input);
        productPriceInput = (EditText) findViewById(R.id.product_price_input);

        final StoreContentReaderContract dataContract = new StoreContentReaderContract(this);

        Button clearButton = (Button) findViewById(R.id.clear_button);
        Button addButton = (Button) findViewById(R.id.add_button);
        assert clearButton != null;
        assert addButton != null;

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideSoftKeyBoard(EntryInputActivity.this);


                productDateInput.setText("");
                productPriceInput.setText("");
                productNameInput.setText("");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView emptyView;
                if((emptyView = isEmptyFields()) == null) {
                    try {
                        dataContract.insert(productNameInput.getText().toString(), productPriceInput.getText().toString(), productDateInput.getText().toString());
                    } catch (Exception e) {
                        Log.e(log, e.getMessage());
                    }
                    Toast toast = Toast.makeText(EntryInputActivity.this, R.string.toast_entry_added_successfully, Toast.LENGTH_SHORT);
                    toast.show();
                    onBackPressed();

                }
                else {
                    //Set focus on empty field
                    emptyView.requestFocus();
                    //Show keyboard
                    Utils.showSoftKeyboard(EntryInputActivity.this, emptyView);

                    Toast toast = Toast.makeText(EntryInputActivity.this, R.string.toast_fill_empty_field, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Nullable
    private TextView isEmptyFields() {
        if(isEmptyField(productNameInput)) {
            return productNameInput;
        }
        if(isEmptyField(productPriceInput)) {
            return productPriceInput;
        }
        if(isEmptyField(productDateInput)) {
            return productDateInput;
        }
        return null;
    }



    private static boolean isEmptyField(TextView v) {
        return v.getText().toString().isEmpty();
    }
}
