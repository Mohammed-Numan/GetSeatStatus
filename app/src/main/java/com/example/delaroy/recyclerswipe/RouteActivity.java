package com.example.delaroy.recyclerswipe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RouteActivity extends Activity implements MyRecyclerViewAdapter.ItemClickListener {

    List<Route> routeNames = new ArrayList<>();
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore dref = FirebaseFirestore.getInstance();
    Company c;
    DocumentReference dr = dref.collection("Company").document("Microsoft");

    @Override
    public void onStart(){
        super.onStart();
        dr.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    routeNames.clear();
                    c = documentSnapshot.toObject(Company.class);
                    List<Route> routes = c.getRoutes();
                    for(Route r: routes)
                        routeNames.add(r);
                    setAdapter();
                }else if(e!=null){
                    Log.d(TAG, "Got an Exception", e);
                }else{
                    Log.d(TAG, "Does Not Exist");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        Toolbar t = (Toolbar)findViewById(R.id.toolbarroute);
        t.setTitle("Routes...");
        recyclerView = (RecyclerView) findViewById(R.id.rvroute);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setAdapter();
    }

    public void setAdapter(){
        adapter = new MyRecyclerViewAdapter(this, routeNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(RouteActivity.this, Start.class);
        i.putExtra("Company",c);
        i.putExtra("Route", c.getRoutes().get(position));
        Toast t = Toast.makeText(this,"The Route selected is "+c.getRoutes().get(position),Toast.LENGTH_LONG);
        t.show();
        startActivity(i);
    }
}
