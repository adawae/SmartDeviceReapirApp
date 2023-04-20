package com.example.smartdevicesrepairapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteRequest extends AppCompatActivity {
    EditText d_id, d_name;
    TextView rd_id, d_device, d_model, d_issue, d_address, d_spinner;
    String srd_id, sd_device, sd_model, sd_issue, sd_address, sd_spinner, sd_type;
    Button d_search, d_delete, d_clear;
    private DatabaseReference ref,ref1;
    private FirebaseAuth firebaseAuth;
    String d_ID, d_n;
    String input_name, input;
    String k;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_request);

        d_id= (EditText) findViewById(R.id.d_id);
        d_name= (EditText) findViewById(R.id.d_name);

        rd_id= (TextView) findViewById(R.id.rd_id);
        d_spinner= (TextView) findViewById(R.id.d_spinner);
        d_device= (TextView) findViewById(R.id.d_device);
        d_model= (TextView) findViewById(R.id.d_model);
        d_issue= (TextView) findViewById(R.id.d_issue);
        d_address= (TextView) findViewById(R.id.d_address);

        d_search= (Button) findViewById(R.id.d_search);
        d_delete= (Button) findViewById(R.id.d_delete);
        d_clear= (Button) findViewById(R.id.d_clear);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        ref= FirebaseDatabase.getInstance().getReference("Requests");


        d_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input= d_id.getText().toString();
                input_name= d_name.getText().toString();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child: snapshot.getChildren()) { // 👈 loop over the child nodes
                            d_ID = child.child("reqID").getValue().toString();
                            d_n= child.child("reqName").getValue().toString();
                            if(d_ID.equals(input) && d_n.equals(input_name)) {
                              //  Toast.makeText(DeleteRequest.this, "Data exist " +d_ID, Toast.LENGTH_SHORT).show();
                                k= child.getKey();
                                rd_id.setText(k);
                                ref1= FirebaseDatabase.getInstance().getReference("Requests").child(k);
                                ref1.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Upload up= snapshot.getValue(Upload.class);
                                        d_spinner.setText(up.getReqType());
                                        rd_id.setText(up.getReqID());
                                        d_device.setText(up.getReqName());
                                        d_model.setText(up.getReqModel());
                                        d_issue.setText(up.getReqIssue());
                                        d_address.setText(up.getReqAddress());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }
        });

        d_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref= FirebaseDatabase.getInstance().getReference("Requests").child(k);
                ref.removeValue();
                Toast.makeText(DeleteRequest.this, "Request is Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DeleteRequest.this, HomeScreen.class));
                finish();
            }
        });

        d_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d_id.setText("");
                d_name.setText("");
                rd_id.setText("");
                d_spinner.setText("");
                d_device.setText("");
                d_model.setText("");
                d_issue.setText("");
                d_address.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeleteRequest.this, HomeScreen.class));
        finish();
    }

}