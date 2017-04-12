package com.amicel.tunaikudemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainFragment extends Fragment implements OnClickListener, ItemClickListener {
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton mFab;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private UserAdapter userAdapter;
    private ArrayList<User> userList;
    private RecyclerView userRV;

    /* renamed from: com.amicel.tunaikudemo.MainFragment.1 */
    class C01991 implements DialogInterface.OnClickListener {
        final /* synthetic */ User val$user;

        C01991(User user) {
            this.val$user = user;
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case ConstraintTableLayout.ALIGN_CENTER /*0*/:
                    MainFragment.this.updateUsername(this.val$user.getId(), this.val$user.getUsername());
                case ConstraintTableLayout.ALIGN_LEFT /*1*/:
                    MainFragment.this.deleteUser(this.val$user.getId());
                default:
            }
        }
    }

    /* renamed from: com.amicel.tunaikudemo.MainFragment.1DeleteUser */
    class AnonymousClass1DeleteUser extends AsyncTask<Void, Void, String> {
        ProgressDialog loading;
        final /* synthetic */ int val$id;

        AnonymousClass1DeleteUser(int i) {
            this.val$id = i;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.loading = ProgressDialog.show(MainFragment.this.getContext(), "Updating...", "Wait...", false, false);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.loading.dismiss();
            Toast.makeText(MainFragment.this.getContext(), s, 1).show();
            MainFragment.this.getAllUsers();
        }

        protected String doInBackground(Void... params) {
            return new RequestHandler().sendGetRequestParam(Config.URL_DELETE_USER, String.valueOf(this.val$id));
        }
    }

    /* renamed from: com.amicel.tunaikudemo.MainFragment.1UpdateUser */
    class AnonymousClass1UpdateUser extends AsyncTask<Void, Void, String> {
        private ProgressDialog loading;
        final /* synthetic */ int val$id;
        final /* synthetic */ String val$newUsername;

        AnonymousClass1UpdateUser(int i, String str) {
            this.val$id = i;
            this.val$newUsername = str;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.loading = ProgressDialog.show(MainFragment.this.getContext(), "Updating...", "Wait...", false, false);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            this.loading.dismiss();
            Toast.makeText(MainFragment.this.getContext(), s, 1).show();
            MainFragment.this.getAllUsers();
        }

        protected String doInBackground(Void... params) {
            HashMap<String, String> hashMap = new HashMap();
            hashMap.put("User_ID", String.valueOf(this.val$id));
            hashMap.put("Username", this.val$newUsername);
            return new RequestHandler().sendPostRequest(Config.URL_UPDATE_USER, hashMap);
        }
    }

    /* renamed from: com.amicel.tunaikudemo.MainFragment.2 */
    class C02002 implements OnEditorActionListener {
        C02002() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 6) {
                return false;
            }
            ((InputMethodManager) v.getContext().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
    }

    /* renamed from: com.amicel.tunaikudemo.MainFragment.3 */
    class C02013 implements DialogInterface.OnClickListener {
        final /* synthetic */ int val$id;
        final /* synthetic */ EditText val$input;

        C02013(EditText editText, int i) {
            this.val$input = editText;
            this.val$id = i;
        }

        public void onClick(DialogInterface dialog, int which) {
            Log.i("test", "out: " + this.val$input.getText().toString());
            MainFragment.this.updateUser(this.val$id, this.val$input.getText().toString());
        }
    }

    /* renamed from: com.amicel.tunaikudemo.MainFragment.4 */
    class C02024 implements DialogInterface.OnClickListener {
        C02024() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    private class GetAllUsersAsync extends AsyncTask<String, String, String> {
        String jsonStringTemp;

        private GetAllUsersAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            MainFragment.this.userList.clear();
            MainFragment.this.mProgressBar.setVisibility(0);
        }

        protected String doInBackground(String... params) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://promoe.id/tunaiku/getAllUsers.php").openConnection();
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(15000);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    this.jsonStringTemp = readLine;
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(this.jsonStringTemp).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                try {
                    JSONArray jsonArray = new JSONObject(stringBuilder.toString().trim()).getJSONArray("users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        MainFragment.this.userList.add(new User(obj.getInt("User_ID"), obj.getString("Username"), obj.getString("Password")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainFragment.this.mProgressBar.setVisibility(8);
            MainFragment.this.userRV.setLayoutManager(MainFragment.this.linearLayoutManager);
            MainFragment.this.userRV.setItemAnimator(new DefaultItemAnimator());
            MainFragment.this.userRV.setAdapter(MainFragment.this.userAdapter);
        }
    }

    public MainFragment() {
        this.userList = new ArrayList();
        this.linearLayoutManager = new LinearLayoutManager(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(C0203R.layout.fragment_main, container, false);
        this.mToolbar = (Toolbar) rootView.findViewById(C0203R.id.mainFragment_toolbar);
        this.userRV = (RecyclerView) rootView.findViewById(C0203R.id.mainFragment_rv);
        this.mProgressBar = (ProgressBar) rootView.findViewById(C0203R.id.mainFragment_progressBar);
        this.mFab = (FloatingActionButton) rootView.findViewById(C0203R.id.mainFragment_fab);
        this.mFab.setOnClickListener(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(this.mToolbar);
        setHasOptionsMenu(true);
        this.userAdapter = new UserAdapter(getContext(), this.userList);
        this.userAdapter.setItemClickListener(this);
        getAllUsers();
        return rootView;
    }

    public void getAllUsers() {
        new GetAllUsersAsync().execute(new String[0]);
    }

    public void onClick(View v) {
        if (v == this.mFab) {
            Fragment addFragment = new AddFragment();
            addFragment.setTargetFragment(this, 2);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(C0203R.id.main_frameLayout, addFragment, "addFragment");
            transaction.hide(this);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void onItemClick(View v, int pos) {
        User user = (User) this.userList.get(pos);
        CharSequence[] dialogOptions = new CharSequence[]{"Edit", "Delete"};
        Builder builder = new Builder(getContext());
        builder.setTitle(user.getUsername());
        builder.setItems(dialogOptions, new C01991(user));
        builder.show();
    }

    private void updateUsername(int id, String username) {
        Builder builder = new Builder(getContext());
        builder.setTitle((CharSequence) username);
        View input = new EditText(getContext());
        input.setMaxLines(1);
        input.setImeOptions(6);
        input.setOnEditorActionListener(new C02002());
        builder.setView(input);
        builder.setPositiveButton((CharSequence) "OK", new C02013(input, id));
        builder.setNegativeButton((CharSequence) "Cancel", new C02024());
        builder.show();
    }

    private void updateUser(int id, String newUsername) {
        new AnonymousClass1UpdateUser(id, newUsername).execute(new Void[0]);
    }

    private void deleteUser(int id) {
        new AnonymousClass1DeleteUser(id).execute(new Void[0]);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(C0203R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
