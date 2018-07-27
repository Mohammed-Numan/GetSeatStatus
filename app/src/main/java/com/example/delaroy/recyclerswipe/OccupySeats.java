package com.example.delaroy.recyclerswipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class OccupySeats extends AppCompatActivity {

    int totalNumberOfSeats;
    int numberOfSeatsAvailable;
    int passengersEntered;
    EditText numberOfPassengersEt;
    TextView numberOfSeatsAvailabletv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupy_seats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle data = getIntent().getExtras();
        numberOfSeatsAvailable = data.getInt("NumberOfSeatsAvailable");
        totalNumberOfSeats = data.getInt("totalNumberOfSeats");

        numberOfSeatsAvailabletv = (TextView) findViewById(R.id.tvnumberofseatsoccupied);
        numberOfSeatsAvailabletv.setText("Number Of Seats Available : "+numberOfSeatsAvailable);

        numberOfPassengersEt = (EditText) findViewById(R.id.etnumberofpassengers);
        numberOfPassengersEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    numberOfPassengersEt = (EditText) findViewById(R.id.etnumberofpassengers);
                    try {
                        passengersEntered = Integer.parseInt(String.valueOf(numberOfPassengersEt.getText()));
                    }catch (Exception e){
                        passengersEntered = 0;
                    }
                    numberOfSeatsAvailable -= passengersEntered;
                    OccupySeats.super.onBackPressed();
                }
                return false;
            }
        });

    }

    @Override
    public void finish(){
        Intent i = new Intent();
        i.putExtra("numberOfSeatsDecremented", totalNumberOfSeats-numberOfSeatsAvailable);
        setResult(RESULT_OK, i);
        super.finish();
    }

}