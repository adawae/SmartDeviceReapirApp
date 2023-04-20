package com.example.smartdevicesrepairapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewRequest extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    EditText r_id,model, issue, name, address;
    Button submit, calculate, clear;
    String selectedComplaint;
    private DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    TextView cost;
    String d_id, d_name, d_model, d_issue, costt, d_address;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] comps = getResources().getStringArray(R.array.typeOfComplaint);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comps);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        r_id= (EditText) findViewById(R.id.r_id);
        name= (EditText) findViewById(R.id.device);
        model= (EditText) findViewById(R.id.model);
        issue= (EditText) findViewById(R.id.issue);
        cost= (TextView) findViewById(R.id.cost);
        address= (EditText) findViewById(R.id.address);

        submit= (Button) findViewById(R.id.submit);
        calculate= (Button) findViewById(R.id.calculate);
        clear= (Button) findViewById(R.id.clear);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    cost.setText("Cost is: 10 OMR");
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               r_id.setText("");
               name.setText("");
               model.setText("");
               issue.setText("");
               address.setText("");
               cost.setText("");
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate1()){
                    ref= FirebaseDatabase.getInstance().getReference("Requests");
                    Upload upload = new Upload(d_id, selectedComplaint, d_name, d_model, d_issue, d_address);
                    ref.child(ref.push().getKey()).setValue(upload);
                    Toast.makeText(NewRequest.this, "Request is submitted", Toast.LENGTH_SHORT).show();
                    Toast.makeText(NewRequest.this, "Technician will be at your address in one hour", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedComplaint = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), selectedComplaint, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private Boolean validate(){
        boolean result= false;

        d_id = r_id.getText().toString();
        d_name= name.getText().toString();
        d_model= model.getText().toString();
        d_issue= issue.getText().toString();
        d_address= address.getText().toString();

        if(d_id.isEmpty() || d_name.isEmpty() || d_model.isEmpty() || d_issue.isEmpty() || d_address.isEmpty()){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private Boolean validate1(){
        boolean result= false;

        d_id = r_id.getText().toString();
        d_name= name.getText().toString();
        d_model= model.getText().toString();
        d_issue= issue.getText().toString();
        costt= cost.getText().toString();
        d_address= address.getText().toString();

        if(d_id.isEmpty() || d_name.isEmpty() || d_model.isEmpty() || d_issue.isEmpty() || d_address.isEmpty() || costt.isEmpty()){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NewRequest.this, HomeScreen.class));
        finish();
    }
}