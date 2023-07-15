package com.example.task_login_signup_screen.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_login_signup_screen.Model.ContactModel;
import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.activities.DashboardActivity;

import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {

    Context context;
    ArrayList<ContactModel> arrContacts;

    public RecyclerContactAdapter(Context context, ArrayList<ContactModel> arrContacts) {
        this.context = context;
        this.arrContacts = arrContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ivContact.setImageResource(arrContacts.get(position).image);
        holder.tvName.setText(arrContacts.get(position).name);
        holder.tvNumber.setText(arrContacts.get(position).number);

        holder.llRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_update_layout);

                EditText edName = dialog.findViewById(R.id.ed_name);
                EditText edNumber = dialog.findViewById(R.id.ed_number);
                AppCompatButton btnAction = dialog.findViewById(R.id.btn_add);
                TextView tvTitle = dialog.findViewById(R.id.tv_title);

                btnAction.setText("Update");
                tvTitle.setText("Update Contact");

                edName.setText((arrContacts.get(position)).name);
                edNumber.setText((arrContacts.get(position)).number);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = "", number = "";
                        if (!edName.getText().toString().equals("")) {
                            name = edName.getText().toString();
                        }
                        if (!edName.getText().toString().equals("")) {
                            number = edNumber.getText().toString();

                        } else {
                            Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show();
                        }

                        arrContacts.set(position, new ContactModel(name, number));
                        notifyItemChanged(position);

                        dialog.dismiss();
                        Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                arrContacts.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                builder.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber;
        ImageView ivContact;
        LinearLayout llRow;
        AppCompatButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_title);
            tvNumber = itemView.findViewById(R.id.tv_number);
            ivContact = itemView.findViewById(R.id.iv_contact);
            llRow = itemView.findViewById(R.id.ll_row);
            //btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}
