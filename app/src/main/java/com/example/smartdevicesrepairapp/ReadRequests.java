package com.example.smartdevicesrepairapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReadRequests extends AppCompatActivity {
    private ListView listview2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<String> list2;
    ArrayAdapter<String> adapter;
    Upload upload;
    FirebaseAuth firebaseAuth;
    private ProgressBar mProgressCircle;
    TextView label;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_requests);


        listview2= (ListView)findViewById(R.id.listview2);
        list2= new ArrayList<>();
        adapter= new ArrayAdapter<String>(this,R.layout.user_info2,R.id.infoTxt2, list2);
        upload = new Upload();
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference("Requests");
        label= (TextView) findViewById(R.id.label);
        label.setPaintFlags(label.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    upload = ds.getValue(Upload.class);
                    list2.add("Request ID: "+upload.getReqID()+ "\n" +"Request Category: "+ upload.getReqType() +"\n"
                            + "Device Name: "+ upload.getReqName() +" \n" + "Model: "+upload.getReqModel() +"\n"
                            + "Issue: " +upload.getReqIssue()+ "\n" + "Address: " + upload.getReqAddress());
                }
                listview2.setAdapter(adapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReadRequests.this, HomeScreen.class));
        finish();
    }
}