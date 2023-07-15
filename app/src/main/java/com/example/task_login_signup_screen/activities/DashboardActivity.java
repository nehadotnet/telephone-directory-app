package com.example.task_login_signup_screen.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_login_signup_screen.Model.ContactModel;
import com.example.task_login_signup_screen.R;
import com.example.task_login_signup_screen.adapter.RecyclerContactAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    TextView userDetail;
    RecyclerView rvContacts;
    Toolbar toolbar;
    FloatingActionButton btnOpenDialog;

    ArrayList<ContactModel> arrayContact=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        iniUI();
        setUI();
    }

    private void iniUI(){
        userDetail=findViewById(R.id.tv_user_detail);
        toolbar=findViewById(R.id.toolbar);
        rvContacts=findViewById(R.id.rv_contacts);
        btnOpenDialog=findViewById(R.id.btn_open_dialog);

    }

    private void setUI(){
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Telephone Directory");
        }
        String name=getIntent().getStringExtra("name");
        String email=getIntent().getStringExtra("email");
        userDetail.setText("UserName: "+name+"\n\nEmail: "+email);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));

//        ContactModel model=new ContactModel(R.drawable.contact,"A","878574854");
//        arrayContact.add(model);

        RecyclerContactAdapter adapter=new RecyclerContactAdapter(this,arrayContact);
        rvContacts.setAdapter(adapter);

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog=new Dialog(DashboardActivity.this);
                dialog.setContentView(R.layout.add_update_layout);

                EditText edName=dialog.findViewById(R.id.ed_name);
                EditText edNumber=dialog.findViewById(R.id.ed_number);
                AppCompatButton btnAction=dialog.findViewById(R.id.btn_add);

                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name="",number="";
                        if(!edName.getText().toString().equals("")){
                             name=edName.getText().toString();
                        }
                        if(!edName.getText().toString().equals("")){
                            number=edNumber.getText().toString();

                        }else{
                            Toast.makeText(DashboardActivity.this, "fill al the fields", Toast.LENGTH_SHORT).show();
                        }

                        arrayContact.add(new ContactModel(name,number));
                        adapter.notifyItemInserted(arrayContact.size()-1);

                        rvContacts.scrollToPosition(arrayContact.size()-1);
                        dialog.dismiss();


                    }
                });
                dialog.show();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        
        if(itemId==R.id.opt_logout){
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        } else if (itemId==R.id.opt_settings) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}