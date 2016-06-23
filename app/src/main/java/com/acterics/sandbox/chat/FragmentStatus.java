package com.acterics.sandbox.chat;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acterics.sandbox.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * Created by User on 14.06.2016.
 */

//TODO solve connection problem!
public class FragmentStatus extends Fragment {

    private static String log = "FRAGMENT_STATUS_LOG";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_status, null);
        Button connectButton = (Button) v.findViewById(R.id.connect_button);
        final EditText addressField = (EditText) v.findViewById(R.id.server_address_field);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HTTPRequestTask().execute(addressField.getText().toString());
            }
        });


        return v;
    }

    class HTTPRequestTask extends AsyncTask<String, Void, HTTPMessage> {
        @Override
        protected HTTPMessage doInBackground(String ... params) {
            final String url = "http://" + params[0];
            try {

            } catch (Exception e) {
                Log.e(FragmentStatus.log, e.getMessage());
            }
            return null;
        }


        @Override
        protected void onPostExecute(HTTPMessage httpMessage) {
            if(httpMessage == null) {
                Toast.makeText(FragmentStatus.this.getActivity(), "Cannot connect to server ", Toast.LENGTH_SHORT).show();
            }
            try {
                TextView currentAuthorView = (TextView) FragmentStatus.this.getView().findViewById(R.id.current_author);
                TextView currentMessageView = (TextView) FragmentStatus.this.getView().findViewById(R.id.current_message);

                currentAuthorView.setText(httpMessage.getAuthor());
                currentMessageView.setText(httpMessage.getMessage());
            } catch (Exception e) {
                Log.e(FragmentStatus.log, e.getMessage());
            }

        }
    }



}





