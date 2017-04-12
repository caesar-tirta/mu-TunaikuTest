package com.amicel.tunaikudemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;

public class AddFragment extends Fragment implements OnClickListener {
    private static final String ADDUSER_URL = "http://promoe.id/tunaiku/adduser.php";
    private Button mAddBtn;
    private EditText mNewPassword;
    private EditText mNewUsername;
    private Toolbar mToolbar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(C0203R.layout.fragment_add, container, false);
        this.mToolbar = (Toolbar) rootView.findViewById(C0203R.id.add_toolbar);
        this.mNewUsername = (EditText) rootView.findViewById(C0203R.id.add_username);
        this.mNewPassword = (EditText) rootView.findViewById(C0203R.id.add_password);
        this.mAddBtn = (Button) rootView.findViewById(C0203R.id.add_addBtn);
        this.mAddBtn.setOnClickListener(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(this.mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        setHasOptionsMenu(true);
        return rootView;
    }

    public void onClick(View v) {
        if (v == this.mAddBtn) {
            addUser();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addUser() {
        add(this.mNewUsername.getText().toString(), this.mNewPassword.getText().toString());
    }

    private void add(String username, String password) {
        new AsyncTask<String, Void, String>() {
            private Dialog loadingDialog;

            protected void onPreExecute() {
                super.onPreExecute();
                this.loadingDialog = ProgressDialog.show(AddFragment.this.getContext(), "Please wait", "Adding User...");
            }

            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap();
                data.put("username", params[0]);
                data.put("password", params[1]);
                return new RequestHandler().sendPostRequest(AddFragment.ADDUSER_URL, data);
            }

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                this.loadingDialog.dismiss();
                ((MainFragment) AddFragment.this.getTargetFragment()).getAllUsers();
                AddFragment.this.getActivity().getSupportFragmentManager().popBackStack();
            }
        }.execute(new String[]{username, password});
    }
}
