package com.amicel.tunaikudemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class UserAdapter extends Adapter<UserHolder> {
    private Context context;
    private ItemClickListener itemClickListener;
    private ArrayList<User> userList;

    public class UserHolder extends ViewHolder implements OnClickListener {
        public TextView mUsername;

        public UserHolder(View itemView) {
            super(itemView);
            this.mUsername = (TextView) itemView.findViewById(C0203R.id.user_username);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (UserAdapter.this.itemClickListener != null) {
                UserAdapter.this.itemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public UserAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(parent.getContext()).inflate(C0203R.layout.user_holder, parent, false));
    }

    public void onBindViewHolder(UserHolder holder, int position) {
        holder.mUsername.setText(((User) this.userList.get(position)).getUsername());
    }

    public int getItemCount() {
        return this.userList.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
