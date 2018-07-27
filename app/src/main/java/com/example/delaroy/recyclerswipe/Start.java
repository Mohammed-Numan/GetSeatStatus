package com.example.delaroy.recyclerswipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends Activity {

    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = (Button) findViewById(R.id.bstart);
        startButton.setText("Start..!");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previous = getIntent();
                Company company = (Company) previous.getSerializableExtra("Company");
                Route selectedRoute = (Route) previous.getSerializableExtra("Route");
                Intent i = new Intent(Start.this, MainActivity.class);
                i.putExtra("Company", company);
                i.putExtra("Route", selectedRoute);
                startActivity(i);
            }
        });
    }
}
