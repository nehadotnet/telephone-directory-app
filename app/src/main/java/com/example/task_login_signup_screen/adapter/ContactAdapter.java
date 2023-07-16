package com.example.task_login_signup_screen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_login_signup_screen.models.ContactModel;
import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.listeners.OnItemClickListener;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<ContactModel> dataSet;
    OnItemClickListener onItemClickListener;

    public ContactAdapter(Context context, ArrayList<ContactModel> dataSet, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    public void refreshAdapter(ArrayList<ContactModel> contactModels){
        this.dataSet=contactModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvName.setText(dataSet.get(position).getFullName());
        holder.tvNumber.setText(dataSet.get(position).getPhone());

        holder.llRow.setOnClickListener(v -> onItemClickListener.onItemClick(holder.getAdapterPosition(), 30));

        holder.btnDelete.setOnClickListener(v -> onItemClickListener.onItemClick(holder.getAdapterPosition(), 20));

        holder.btnCall.setOnClickListener(v -> onItemClickListener.onItemClick(holder.getAdapterPosition(), 10));
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber;
        ImageView ivContact;
        LinearLayout llRow;
        AppCompatButton btnDelete, btnCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_title);
            tvNumber = itemView.findViewById(R.id.tv_number);
            ivContact = itemView.findViewById(R.id.iv_contact);
            llRow = itemView.findViewById(R.id.ll_row);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnCall = itemView.findViewById(R.id.btn_call);
        }
    }
}
