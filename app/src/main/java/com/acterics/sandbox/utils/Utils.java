package com.acterics.sandbox.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.acterics.sandbox.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by User on 11.06.2016.
 */
public class Utils {

    public static void hideSoftKeyBoard(@NonNull Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSoftKeyboard(@NonNull Activity activity, @NonNull View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startIndependActivity(Activity activity, Class<?> cls) {
        activity.startActivity(new Intent(activity, cls));
    }

    /*
        Post request to server

     */
    public static class PostJSONResponse {
        private int response;
        private String message;
        private String url;

        public Boolean toBoolean() {
            return response != 0;
        }

        public int getResponse() {
            return response;
        }

        public void setResponse(int response) {
            this.response = response;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class PostRequest extends AsyncTask<Void, Void, PostJSONResponse> {


        MultiValueMap<String, Object> formData;
        String url;

        public PostRequest(@NonNull String url, MultiValueMap<String, Object> requetParams) {
            this.url = url;
            this.formData = requetParams;
        }



        @Override
        protected PostJSONResponse doInBackground(Void... params) {
            try {

//                HttpHeaders requestHeaders = new HttpHeaders();
//                requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//                // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
//                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(
//                        formData, requestHeaders);

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate(true);


                PostJSONResponse response = restTemplate.postForObject(url, formData, PostJSONResponse.class);

                // Make the network request, posting the message, expecting a String in response from the server
                //ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
                        //String.class);

                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class SimpleHttpRequest extends AsyncTask<Void, Void, String> {
        String url;

        public SimpleHttpRequest(@NonNull String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                RestTemplate restTemplate = new RestTemplate(true);
                String response = restTemplate.postForObject(url, null, String.class);
                if(response == null) {
                    throw new Exception("Null response");
                }

                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
    }

//    private static boolean dialogResult = false;
//    public static boolean getAlertDialogResult(Context context, String title, String message, String positiveResult, String negativeResult) {
//
//
//        AlertDialog dialog =  new AlertDialog.Builder(context)
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(positiveResult, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialogResult = true;
//                    }
//                })
//                .setNegativeButton(negativeResult, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialogResult = false;
//                    }
//                })
//                .setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        dialogResult = false;
//                    }
//                })
//                .create();
//        dialog.show();


//    }
}
