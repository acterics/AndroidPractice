package com.acterics.sandbox.webstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by User on 06.06.2016.
 */


//TODO Test work with database
public final class StoreContentReaderContract {

    private StoreContentReaderDbHelper mDbHelper;
    private Context context;
    private final static String log = "STORE_CONTENT_LOGGER";

    public StoreContentReaderContract(Context _context) {

        context = _context;
        mDbHelper = new StoreContentReaderDbHelper();
    }

    public static abstract class StoreContentEntry implements BaseColumns {
        public final static String TABLE_NAME = "store_content_table";
        public final static String COLUMN_NAME_PRODUCT = "product_name";
        public final static String COLUMN_NAME_PRICE = "price";
        public final static String COLUMN_NAME_SELL_DATE = "sell_date";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StoreContentEntry.TABLE_NAME + " (" +
                    StoreContentEntry._ID + " INTEGER PRIMARY KEY, " +
                    StoreContentEntry.COLUMN_NAME_PRODUCT + TEXT_TYPE + COMMA_SEP +
                    StoreContentEntry.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
                    StoreContentEntry.COLUMN_NAME_SELL_DATE + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StoreContentEntry.TABLE_NAME;

    public class StoreContentReaderDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "StoreContentReader.db";

        public StoreContentReaderDbHelper() {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    private class InsertEntryTask extends AsyncTask<String, Integer, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDbHelper = new StoreContentReaderDbHelper();
        }

        @Override
        protected Long doInBackground(String... params) {
            SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(StoreContentEntry.COLUMN_NAME_PRODUCT, params[0]);
            values.put(StoreContentEntry.COLUMN_NAME_PRICE, params[1]);
            values.put(StoreContentEntry.COLUMN_NAME_SELL_DATE, params[2]);

            return mDb.insert(StoreContentEntry.TABLE_NAME, null, values);

        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.i(log, "Entry added, new row id = " + aLong);
            mDbHelper = null;
        }
    }
    private class ReadDbTask extends AsyncTask<Void, Integer, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDbHelper = new StoreContentReaderDbHelper();
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            SQLiteDatabase mDb = mDbHelper.getReadableDatabase();
            String[] projection = {
                    StoreContentEntry._ID,
                    StoreContentEntry.COLUMN_NAME_PRODUCT,
                    StoreContentEntry.COLUMN_NAME_PRICE,
                    StoreContentEntry.COLUMN_NAME_SELL_DATE
            };

           //String order = StoreContentEntry.COLUMN_NAME_PRODUCT + " DESC";
            String order = StoreContentEntry._ID;

            return mDb.query(
                    StoreContentEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    order);

        }
    }

    public void insert(String name, String prise, String date) {
        new InsertEntryTask().execute(name, prise, date);
    }

    @Nullable
    public Cursor read() {
        try {
            return new ReadDbTask().execute().get();

        } catch (Exception e) {
            Log.e(log, e.getMessage());
        }
        return null;
    }


}
