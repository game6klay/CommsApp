package com.example.patja2r.mycommunicationhub.controllers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.patja2r.mycommunicationhub.R;
import com.example.patja2r.mycommunicationhub.helpers.EventDataBaseHelper;
import com.example.patja2r.mycommunicationhub.models.EventModel;
import com.example.patja2r.mycommunicationhub.models.PersonModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    private static final int CONTACT_PICKER_RESULT = 1;
    private static final String DEBUG_TAG = null;
    private EditText etDisplayName;
    private EditText etDisplayEmail;

    private EditText etStartTime;
    private EditText etEndTime;
    private Spinner sGroupType;
    private Spinner sEventType;
    private Spinner sReminderOptions;
    private EditText etLocation;
    private Button btAddMember;
    private ArrayAdapter<CharSequence> adapterGroupType;
    private ArrayAdapter<CharSequence> adapterEventType;
    private ArrayAdapter<CharSequence> adapterReminderOptions;

    private FloatingActionButton fabViewEvent;
    private FloatingActionButton fabSaveEvent;
    private FloatingActionButton fabCancelEvent;

    private EventDataBaseHelper dbHelper;
    private List<EventModel> eventList;
    private ArrayList<PersonModel> personList = new ArrayList<PersonModel>();
    private PersonModel object;
    private String friendsList;

    private TextView tvEventDate;
    private String eventDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        initiateComponents();


    }

    private void initiateComponents(){

        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        eventDate = getIntent().getExtras().getString("Event Date");
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        tvEventDate.setText(eventDate);

        /*
        * Binding the contact names and emails to the edit texts, later the values of these fields will be passed on to other activities
        * The same follows for the spinner as well
        * */

        etLocation = (EditText) findViewById(R.id.etLocation);
        etDisplayName = (EditText) findViewById(R.id.etDisplayName);

        //button
        btAddMember = (Button) findViewById(R.id.bAddMember);
        fabSaveEvent = (FloatingActionButton) findViewById(R.id.fabSaveEvent);
        fabViewEvent = (FloatingActionButton) findViewById(R.id.fabViewEvent);
        fabCancelEvent = (FloatingActionButton) findViewById(R.id.fabCancelEvent);

        // initializing the spinners
        sGroupType = (Spinner) findViewById(R.id.sGroupType);
        sEventType = (Spinner) findViewById(R.id.sEventType);
        sReminderOptions = (Spinner) findViewById(R.id.sReminderOptions);

        /*
        * The values to be displayed in the drop down of the spinner are first initialized in the String.xml as string arrays
        * Then the values are passed onto the array layout in the adapter associating each spinner with one adapter
        * The adapters create an array of the drop down, for which we need to bind resources in the String xml file and with an inbuilt android layout
        * Finally we set the adapter values with the spinners that we have initialized
        * */
        adapterGroupType = ArrayAdapter.createFromResource(this, R.array.group_type_arrays, android.R.layout.simple_spinner_item);
        adapterEventType = ArrayAdapter.createFromResource(this, R.array.event_type_arrays, android.R.layout.simple_spinner_item);
        adapterReminderOptions = ArrayAdapter.createFromResource(this, R.array.reminder_options_arrays, android.R.layout.simple_spinner_item);

        sGroupType.setAdapter(adapterGroupType);
        sEventType.setAdapter(adapterEventType);
        sReminderOptions.setAdapter(adapterReminderOptions);

        adapterGroupType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEventType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterReminderOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // binding the edit texts of
        etStartTime = (EditText) findViewById(R.id.etStartTime);
        SetTime setTime1 = new SetTime(etStartTime, this);

        etEndTime = (EditText) findViewById(R.id.etEndTime);
        SetTime fromTime2 = new SetTime(etEndTime, this);

       /*
       * Adding event trackers first for three spinners and then buttons to follow
       *
       * Simply adding onItemSelected listeners for now, would be passing on values selected later
       * for the ViewEventActivity
       *
       * */




        sGroupType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)
                        +" is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sEventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)
                        +" is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sReminderOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)
                        +" is selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLaunchContactPicker(view);
            }
        });

        fabSaveEvent = (FloatingActionButton) findViewById(R.id.fabSaveEvent);
        fabSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent();
            }
        });

        fabViewEvent = (FloatingActionButton) findViewById(R.id.fabViewEvent);
        fabViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this,EventHistoryActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCancelEvent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    /*
    * This method basically tries to leverage passing the other android application's contents with the
    * help of the intent mechanism,to pick a contact from the data provided by the Contacts content provider.
    *
    * */

    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    /*
    *  You can grab the result from the contacts picker by implementing the onActivityResult()
    *  method of your Activity. Here you can check that the result matches your requestCode
    *  and that the result was good.
    *
    * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    String email = "";
                    try {
                        Uri result = data.getData();
                        Log.v(DEBUG_TAG, "Got a contact result: "
                                + result.toString());

                        // get the contact id from the Uri
                        String id = result.getLastPathSegment();

                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id },
                                null);

                        int phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);


                        // query for everything email
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] { id },
                                null);

                        int emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

                        // let's just get the first email
                        if (cursor.moveToFirst()) {
                            email = cursor.getString(emailIdx);
                            Log.v(DEBUG_TAG, "Got email: " + email);
                        } else {
                            Log.w(DEBUG_TAG, "No results");
                        }
                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, "Failed to get email data", e);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        EditText emailEntry = (EditText) findViewById(R.id.etDisplayName);


                        emailEntry.setText(email);
                        if (email.length() == 0) {
                            Toast.makeText(this, "No email found for contact.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    break;
            }

        } else {
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu., menu);
        return true;
    }

    public boolean saveEvent(EventModel event)
    {
        dbHelper.addEvent(event);
        Intent intent = new Intent(this, EventHistoryActivity.class);
        intent.putExtra("new_event", event);
        try
        {
            this.setResult(RESULT_OK, intent);
            this.finish();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error! " + e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public boolean persistEvent(EventModel event) {
        if (event == null)
            return false;
        else {
            getIntent().putExtra("event", event);
            setResult(RESULT_OK, getIntent());
            finish();
            return true;
        }
}
    public EventModel createEvent() {

        String eventDate = String.valueOf(tvEventDate.getText());
        String groupType = sGroupType.getSelectedItem().toString();
        String eventType = sEventType.getSelectedItem().toString();
        String startTime = String.valueOf(etStartTime.getText());
        String endTime = String.valueOf(etEndTime.getText());
        String reminderOptions = sReminderOptions.getSelectedItem().toString();
        String location = String.valueOf(etLocation.getText());
        String displayName = String.valueOf(etDisplayName.getText());

        object = new PersonModel(displayName, "", "");
        personList.add(object);
        List<String> eventMembers = new ArrayList<>();
        for(PersonModel person: personList) {
            eventMembers.add(person.getName());
        }
        //ListView memberList = (ListView) findViewById(R.id.lvMembers);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eventMembers);
        //memberList.setAdapter(adapter);

        EventModel event = new EventModel(eventDate,groupType,eventType,startTime,endTime,reminderOptions,location,personList);

        saveEvent(event);

        try
        {
            return event;
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }

    }

    public void cancelEvent() {

        // TODO - fill in here

        setResult(RESULT_CANCELED, getIntent());
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelEvent();
        super.onBackPressed();
    }


}

/*
* Tried to make the TimePickerDialog code reusable for both start time and end time
* Thus made a separate class for both start and end time edit texts
* Implements an interface where a callback is invoked whenever the focus of the view is changed
* Finally the selected time in the dialog is set on the edit text
* */

class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context ctx;

    public SetTime(EditText editText, Context ctx){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.ctx=ctx;

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        if(hasFocus){
            int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = myCalendar.get(Calendar.MINUTE);
            new TimePickerDialog(ctx, this, hour, minute, true).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        this.editText.setText( hourOfDay + ":" + minute);
    }

}
