package com.example.patja2r.mycommunicationhub.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.patja2r.mycommunicationhub.R;
import com.example.patja2r.mycommunicationhub.models.EventModel;
import com.example.patja2r.mycommunicationhub.models.PersonModel;

import java.util.List;

/**
 * Created by patja2r on 6/22/2016.
 */
public class ViewEventDialog extends AppCompatActivity {

    private EditText etGroupType;
    private EditText etEventType;
    private EditText etStartTime;
    private EditText etEndTime;
    private EditText etReminderOptions;
    private EditText etLocation;
    private EditText etMembers;
    private Button btDone;
    private Button btDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO - fill in here
        setContentView(R.layout.view_event);
        initiateComponents();
        Intent newIntent = getIntent();
        EventModel event = getEvent(newIntent);
        viewEvent(event);

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    /**
     * Create a EventModel object via the recent trip that
     * was passed to TripViewer via an Intent.
     *
     * i The Intent that contains
     * the most recent Event data.
     *
     * @return The Event that was most recently
     * passed to EventViewer, or null if there
     * is none.
     */

    private void viewEvent(EventModel event) {

        etGroupType.setText(event.getGroupType());
        etEventType.setText(event.getEventType());
        etStartTime.setText(event.getStartTime());
        etEndTime.setText(event.getEndTime());
        etReminderOptions.setText(event.getReminderOption());
        etLocation.setText(event.getLocation());

        if(event.getEventMembers() != null && !event.getEventMembers().isEmpty()) {
            List<PersonModel> persons = event.getEventMembers();
            String totalAttendee = "";
            for (PersonModel person: persons) {
                String attendee = person.getName() + person.getEmail() + person.getPhone() + "\n";
                totalAttendee += attendee;
            }
            etMembers.setText(totalAttendee);
        }
    }

    /**
     * Populate the View using a Event model.
     *
     * The Event model used to
     * populate the View.
     */

    private EventModel getEvent(Intent i) {
        // TODO - fill in here
        return (EventModel) i.getExtras().getParcelable("trip");
    }

    private void initiateComponents() {

        etGroupType = (EditText) findViewById(R.id.etGroupType);
        etEventType = (EditText) findViewById(R.id.etEventType);
        etStartTime = (EditText) findViewById(R.id.etStartTime);
        etEndTime = (EditText) findViewById(R.id.etEndTime);
        etReminderOptions = (EditText) findViewById(R.id.etReminderOptions);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etMembers = (EditText) findViewById(R.id.etMembers);
    }


}
