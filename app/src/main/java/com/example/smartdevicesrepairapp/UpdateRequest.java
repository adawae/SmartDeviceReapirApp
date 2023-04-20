package com.example.smartdevicesrepairapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateRequest extends AppCompatActivity {
    EditText u_id, u_name;
    EditText ru_id, u_device, u_model, u_issue, u_address, u_spinner;
    String sru_id, su_device, su_model, su_issue, su_address, su_spinner;
    Button u_search, u_update, u_clear;
    DatabaseReference ref, ref1;
    private FirebaseAuth firebaseAuth;
    String d_id, d_name, d_model, d_issue, d_address, d_type;
    String d_ID, d_n;
    String input_name, input;
     String k;
     String ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request);

        u_id= (EditText) findViewById(R.id.u_id);
        u_name= (EditText) findViewById(R.id.u_name);

        ru_id= (EditText) findViewById(R.id.ru_id);
        u_spinner= (EditText) findViewById(R.id.u_spinner);
        u_device= (EditText) findViewById(R.id.u_device);
        u_model= (EditText) findViewById(R.id.u_model);
        u_issue= (EditText) findViewById(R.id.u_issue);
        u_address= (EditText) findViewById(R.id.u_address);

        u_search= (Button) findViewById(R.id.u_search);
        u_update= (Button) findViewById(R.id.u_update);
        u_clear= (Button) findViewById(R.id.u_clear);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Requests");
        u_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input= u_id.getText().toString();
                input_name= u_name.getText().toString();

                String fetch_id= ru_id.getText().toString();
                String fetch_name= u_device.getText().toString();
                ref= FirebaseDatabase.getInstance().getReference("Requests");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child: snapshot.getChildren()) { // 👈 loop over the child nodes
                            if (child.exists()){
                            d_ID = child.child("reqID").getValue().toString();
                            d_n = child.child("reqName").getValue().toString();
                            if (d_ID.equals(input) && d_n.equals(input_name)) {
                                k = child.getKey();
                                ru_id.setText(k);
                                ref1 = FirebaseDatabase.getInstance().getReference("Requests").child(k);
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Upload up = snapshot.getValue(Upload.class);
                                        u_spinner.setText(up.getReqType());
                                        ru_id.setText(up.getReqID());
                                        u_device.setText(up.getReqName());
                                        u_model.setText(up.getReqModel());
                                        u_issue.setText(up.getReqIssue());
                                        u_address.setText(up.getReqAddress());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        } //exist end

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }
        });

        u_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    ref= FirebaseDatabase.getInstance().getReference("Requests");
                    Upload upload = new Upload(d_id, d_type, d_name, d_model, d_issue, d_address);
                    ref.child(k).setValue(upload);
                    Toast.makeText(UpdateRequest.this, "Request details are Updated", Toast.LENGTH_SHORT).show();
                }
                }
        });

        u_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_id.setText("");
                u_name.setText("");
                ru_id.setText("");
                u_spinner.setText("");
                u_device.setText("");
                u_model.setText("");
                u_issue.setText("");
                u_address.setText("");
            }
        });

    }


    private Boolean validate(){
        boolean result= false;

        d_type= u_spinner.getText().toString();
        d_id = ru_id.getText().toString();
        d_name= u_device.getText().toString();
        d_model=u_model.getText().toString();
        d_issue= u_issue.getText().toString();
        d_address= u_address.getText().toString();

        if(d_type.isEmpty() || d_id.isEmpty() || d_name.isEmpty() || d_model.isEmpty() || d_issue.isEmpty() || d_address.isEmpty()){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateRequest.this, HomeScreen.class));
        finish();
    }
}