package com.amicel.tunaikudemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements OnClickListener {
    private Button mLoginBtn;
    private EditText mPassword;
    private EditText mUsername;
    private String password;
    private String username;

    /* renamed from: com.amicel.tunaikudemo.LoginFragment.1LoginAsync */
    class AnonymousClass1LoginAsync extends AsyncTask<String, Void, String> {
        private Dialog loadingDialog;
        private String resultStr;
        final /* synthetic */ String val$password;
        final /* synthetic */ String val$username;

        AnonymousClass1LoginAsync(String str, String str2) {
            this.val$username = str;
            this.val$password = str2;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.loadingDialog = ProgressDialog.show(LoginFragment.this.getContext(), "Please wait", "Loading...");
        }

        protected String doInBackground(String... params) {
            Exception e;
            try {
                URL url = new URL("http://promoe.id/tunaiku/login.php");
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("username", this.val$username);
                jsonObj.put("password", this.val$password);
                String message = jsonObj.toString();
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setFixedLengthStreamingMode(message.getBytes().length);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(15000);
                OutputStream os = new BufferedOutputStream(httpURLConnection.getOutputStream());
                os.write(message.getBytes());
                os.flush();
                InputStream inputStream = httpURLConnection.getInputStream();
                this.resultStr = new BufferedReader(new InputStreamReader(inputStream)).readLine();
                inputStream.close();
                httpURLConnection.disconnect();
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                return this.resultStr;
            } catch (JSONException e3) {
                e = e3;
                e.printStackTrace();
                return this.resultStr;
            }
            return this.resultStr;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.loadingDialog.dismiss();
            try {
                if (new JSONObject(s).getInt("success") == 1) {
                    Fragment detailFragment = new MainFragment();
                    FragmentTransaction transaction = LoginFragment.this.getFragmentManager().beginTransaction();
                    transaction.replace(C0203R.id.main_frameLayout, detailFragment, "detailFragment");
                    transaction.commit();
                    return;
                }
                Toast.makeText(LoginFragment.this.getContext(), "Invalid User Name or Password", 0).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class BackgroundTask extends AsyncTask<Void, String, String> {
        private BackgroundTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("1")) {
                Fragment detailFragment = new MainFragment();
                FragmentTransaction transaction = LoginFragment.this.getFragmentManager().beginTransaction();
                transaction.replace(C0203R.id.main_frameLayout, detailFragment, "detailFragment");
                transaction.commit();
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(C0203R.layout.fragment_login, container, false);
        this.mUsername = (EditText) rootView.findViewById(C0203R.id.login_username);
        this.mPassword = (EditText) rootView.findViewById(C0203R.id.login_password);
        this.mLoginBtn = (Button) rootView.findViewById(C0203R.id.login_loginBtn);
        this.mLoginBtn.setOnClickListener(this);
        return rootView;
    }

    public void onClick(View v) {
        if (v == this.mLoginBtn) {
            this.username = this.mUsername.getText().toString();
            this.password = this.mPassword.getText().toString();
            login(this.username, this.password);
        }
    }

    private void login(String username, String password) {
        new AnonymousClass1LoginAsync(username, password).execute(new String[]{username, password});
    }
}
