package com.example.patja2r.mycommunicationhub.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.patja2r.mycommunicationhub.R;

/**
 * Created by patja2r on 6/16/2016.
 */
public class EventTypeActivity extends AppCompatActivity {


    private TextView tvEventDate;
    private Button bSelectTime;
    private String eventDate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type);

        eventDate = getIntent().getExtras().getString("Event Date");
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        tvEventDate.setText(eventDate);

        bSelectTime = (Button) findViewById(R.id.bSelectTime);

        bSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("Event Date", eventDate);
                Intent intent = new Intent(EventTypeActivity.this,SelectEventTimeActivity.class );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
