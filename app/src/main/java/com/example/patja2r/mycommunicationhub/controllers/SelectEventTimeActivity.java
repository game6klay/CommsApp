package com.example.patja2r.mycommunicationhub.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.patja2r.mycommunicationhub.R;

// Array of options --> ArrayAdapter --> ListView
// List view: {views: da_items.xml}
/**
 * Created by patja2r on 6/16/2016.
 */
public class SelectEventTimeActivity extends AppCompatActivity{

    private TextView tvEventDate;
    private String eventDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        populateListView();
        registerClickCallback();

        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        eventDate = getIntent().getExtras().getString("Event Date");
        tvEventDate = (TextView) findViewById(R.id.tvEventDate);
        tvEventDate.setText(eventDate);
    }


    private void populateListView() {
        // Create list of items
        String[] myItems = {"12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM",
                "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM"};

        // Build Adapter
        // TODO: CHANGE THE [[ to a less than, ]] to greater than.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, // Context for the activity.
                R.layout.time_item, // Layout to use (create)
                myItems); // Items to be displayed // Configure the list view.
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

    private void registerClickCallback() {
        final ListView list = (ListView) findViewById(R.id.listView);

        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setItemsCanFocus(false);

        if (list != null) {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                // TODO: CHANGE THE [[ to a less than, ]] to greater than.
                public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                    list.setSelected(true);
                    TextView textView = (TextView) viewClicked;
                    Bundle bundle = new Bundle();
                    bundle.putString("Event Date", eventDate);
                    Intent intent = new Intent(SelectEventTimeActivity.this,AddEventActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }

}