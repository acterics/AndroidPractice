package com.acterics.sandbox.chat;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.acterics.sandbox.R;
import com.acterics.sandbox.utils.Utils;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * Created by User on 19.06.2016.
 */
public class FragmentLogin extends Fragment {

    private static String log = "FRAGMENT_LOGIN_LOG";

    EditText nicknameField;
    TextView loginResult;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, null);


        nicknameField = (EditText) v.findViewById(R.id.nickname_field);
        loginResult = (TextView) v.findViewById(R.id.login_result);

        Button loginButton = (Button) v.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlString = getString(R.string.default_server_address) + "/login";
                MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
                formData.add("nickname", nicknameField.getText().toString());
                try {
                    handleJSONResponse(new Utils.PostRequest(urlString, formData).execute().get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });


        return v;
    }

    private void handleJSONResponse(Utils.PostJSONResponse response) {
        if(response.toBoolean()) {
            String url = getString(R.string.default_server_address) + "/" + response.getUrl();
            try {
                loginResult.setText(new Utils.SimpleHttpRequest(url).execute().get());

            } catch (Exception e) {
                loginResult.setText(e.getMessage());
            }
        }
        else {
            loginResult.setText(response.getMessage());
        }
    }
}
