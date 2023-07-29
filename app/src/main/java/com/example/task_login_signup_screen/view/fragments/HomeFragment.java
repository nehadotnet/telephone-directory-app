package com.example.task_login_signup_screen.view.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.adapter.ContactAdapter;
import com.example.task_login_signup_screen.db.DataBaseHandler;
import com.example.task_login_signup_screen.listeners.OnItemClickListener;
import com.example.task_login_signup_screen.models.ContactModel;
import com.example.task_login_signup_screen.utils.Utils;
import com.example.task_login_signup_screen.view.activities.ContactFormActivity;
import com.example.task_login_signup_screen.view.activities.DashboardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnItemClickListener {
    RecyclerView rvContacts;
    FloatingActionButton fabAddContact;
    ArrayList<ContactModel> arrayContact = new ArrayList<>();
    ContactAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        iniUI(view);
        return view;

    }
    private void iniUI(View view) {
        rvContacts = view.findViewById(R.id.rv_contacts);
        fabAddContact = view.findViewById(R.id.btn_open_dialog);

        setContactList();
        fabAddContact.setOnClickListener(v -> {
            Utils.navigateScreen(getContext(), ContactFormActivity.class);
//            refreshAdapter();
        });
    }

    private void setContactList() {
        arrayContact.clear();
        arrayContact = DataBaseHandler.getInstance(getContext()).getAllContact(getContext());
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactAdapter(getContext(), arrayContact, this);
        rvContacts.setAdapter(adapter);
    }



    @Override
    public void onItemClick(int position, int type) {
        if (type == 10) {
            String phoneNumber = arrayContact.get(position).getPhone();
            Utils.dialContact(getContext(), phoneNumber);
        } else if (type == 20) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.delete_contact))
                    .setMessage(getString(R.string.are_you_sure_want_to_delete))
                    .setPositiveButton(getString(R.string.dialog_yes), (dialog, which) -> {
                        Log.e("TAG", "onItemClick: " + arrayContact.get(position).toString());
                        boolean result = DataBaseHandler.getInstance(getContext()).deleteContact(arrayContact.get(position).getId());
                        if (result) {
                            Utils.showToastMessage(getContext(), getString(R.string.contact_deleted_successfully));
                            arrayContact.clear();
                            arrayContact = DataBaseHandler.getInstance(getContext()).getAllContact(getContext());
                            Log.e("TAG", "onItemClick: " + arrayContact.size());
                            adapter.refreshAdapter(arrayContact);
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(getString(R.string.dialog_no), (dialog, which) -> {
                    });
            builder.show();
        } else if (type == 30) {
            Intent intent = new Intent(getContext(), ContactFormActivity.class);
            intent.putExtra("contact", arrayContact.get(position));
            startActivity(intent);
        }
    }
    private void refreshAdapter() {
        arrayContact = DataBaseHandler.getInstance(getContext()).getAllContact(getContext());
        adapter.refreshAdapter(arrayContact);
        rvContacts.scrollToPosition(arrayContact.size() - 1);
    }

}