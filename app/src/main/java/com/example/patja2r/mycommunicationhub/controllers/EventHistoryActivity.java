package com.example.patja2r.mycommunicationhub.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.patja2r.mycommunicationhub.R;
import com.example.patja2r.mycommunicationhub.helpers.EventDataBaseHelper;
import com.example.patja2r.mycommunicationhub.models.EventModel;

import java.util.ArrayList;

/**
 * Created by patja2r on 6/24/2016.
 */
public class EventHistoryActivity extends AppCompatActivity {

    private static final String TAG = "EventHistoryActivity";

    private ListView memberList;

    private ArrayList<String> events;
    private ArrayList<EventModel> rawEvents;
    private ArrayAdapter<String> adapter;
    private Button mainMenu;

    private EventDataBaseHelper dbHelper;

    private AppCompatActivity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_history);

        dbHelper = new EventDataBaseHelper(this);

        initiateComponents();


        memberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {

                    EventModel event = rawEvents.get(position);
                    Intent intentToViewEvent = new Intent(activity, ViewEventDialog.class);
                    intentToViewEvent.putExtra("event", event);
                    startActivityForResult(intentToViewEvent, 101);
                }

                catch (Exception e)
                {
                    Toast.makeText(EventHistoryActivity.this, "Exception in setOnItemClickListener: "
                            + e.toString(), Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    /**
     * This Method initiate all components required for the class.
     */
    private void initiateComponents() {

        rawEvents = dbHelper.getAllEvents();

        events = new ArrayList<String>();

        for (EventModel event : rawEvents) {
            events.add(event.getEventDate());
        }
        memberList = (ListView) findViewById(R.id.eventLists);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, events);
        memberList.setAdapter(adapter);


    }
}
