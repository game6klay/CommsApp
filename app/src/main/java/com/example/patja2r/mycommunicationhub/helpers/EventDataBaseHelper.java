package com.example.patja2r.mycommunicationhub.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.patja2r.mycommunicationhub.models.EventModel;
import com.example.patja2r.mycommunicationhub.models.PersonModel;

import java.util.ArrayList;

/**
 * Created by patja2r on 6/22/2016.
 */


public class EventDataBaseHelper extends SQLiteOpenHelper {


    /*
    * SQLiteOpenHelper is base class where we have the the onCreate and onUpgrade methods- similar concepts as before
    * This class takes care of opening the database if it exists , creating it if it does not and upgrading it as necessary.
    * Then in our implementation getWritableDatabase() calls the object SQLiteDatabase, this object is used inside
    * the app to perform operations insert , delete etc
    * Not to forget SQLite is a database exclusive to the app in android, every app has is own SQLite database
    * */

    // Name of the database
    private static final String DATABASE_NAME = "mycommshub.events.db";
    private static final int DATABASE_VERSION = 1;
    // Table columns
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EVENT_DATE = "eventDate";
    public static final String COLUMN_GROUP_TYPE = "groupType";
    public static final String COLUMN_EVENT_TYPE = "eventType";
    public static final String COLUMN_START_TIME = "startTime";
    public static final String COLUMN_END_TIME = "endTime";
    public static final String COLUMN_REMINDER_OPTIONS = "reminderOptions";
    public static final String COLUMN_EVENT_LOCATION = "eventLocations";
    public static final String TABLE_MEMBERS = "members";
    public static final String COLUMN_MEMBER_NAME = "name";
    public static final String COLUMN_EVENT_ID = "eventId";
    private Context context;

    private static final String TABLE_EVENTS_CREATE = "create table "
            + TABLE_EVENTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_EVENT_DATE
            + " text not null, " + COLUMN_GROUP_TYPE + " text not null, " + COLUMN_EVENT_TYPE + " text not null, " + COLUMN_START_TIME + " text not null, "
            + COLUMN_END_TIME + " text not null," + COLUMN_REMINDER_OPTIONS + "text not null," + COLUMN_EVENT_LOCATION + "text not null);";

    private static final String TABLE_MEMBERS_CREATE = "create table "
            + TABLE_MEMBERS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_EVENT_ID
            + " integer not null, " + COLUMN_MEMBER_NAME + " text not null);";

    // Database helper constructor
    public EventDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables

        try {
            db.execSQL(TABLE_EVENTS_CREATE);
            db.execSQL(TABLE_MEMBERS_CREATE);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        onCreate(db);

    }

    // Adding new event
    public long addEvent(EventModel event) {

        /*
        * ContentValues class is similar to the Map data structure in the Java
        * For every column specify the name of the column as key and the data to be put as value
        *
        * */

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_DATE, event.getEventDate());
        values.put(COLUMN_GROUP_TYPE, event.getGroupType());
        values.put(COLUMN_EVENT_TYPE, event.getEventType());
        values.put(COLUMN_START_TIME, event.getStartTime());
        values.put(COLUMN_END_TIME, event.getEndTime());
        values.put(COLUMN_REMINDER_OPTIONS,event.getReminderOption());
        values.put(COLUMN_EVENT_LOCATION,event.getLocation());


        // Inserting Row
        long id = db.insert(TABLE_EVENTS, null, values);

        for (PersonModel person : event.getEventMembers()) {
            values = new ContentValues();
            values.put(COLUMN_MEMBER_NAME, person.getName());
            values.put(COLUMN_EVENT_ID, id);
            db.insert(TABLE_MEMBERS, null, values);
        }
        db.close();

        return id;
    }

    // Getting single event
    public EventModel getEvent (String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Grab an event based on name
        Cursor cursor = db.query(TABLE_EVENTS, new String[]{COLUMN_ID,
                        COLUMN_EVENT_DATE, COLUMN_EVENT_TYPE,COLUMN_GROUP_TYPE}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        String eventDate = cursor.getString(0);
        String groupType = cursor.getString(1);
        String eventType = cursor.getString(2);
        String startTime = cursor.getString(3);
        String endTime = cursor.getString(4);
        String reminderOptions = cursor.getString(5);
        String eventLocation = cursor.getString(6);

        // Grab all members of the grabbed event
        cursor = db.query(TABLE_MEMBERS, new String[] { COLUMN_ID,
                        COLUMN_MEMBER_NAME, COLUMN_EVENT_ID }, COLUMN_EVENT_ID + "=?",
                new String[] { String.valueOf(eventDate) }, null, null, null, null);

        ArrayList<PersonModel> eventMembers = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            PersonModel person = new PersonModel(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            eventMembers.add(person);
        }

        EventModel event = new EventModel(eventDate, groupType, eventType, startTime,endTime,reminderOptions,eventLocation,eventMembers);
        return event;
    }

    private ArrayList<PersonModel> getEventMembers(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Grab all members of the grabbed trip
        Cursor cursor = db.query(TABLE_MEMBERS, new String[] { COLUMN_ID,
                        COLUMN_MEMBER_NAME, COLUMN_EVENT_ID }, COLUMN_EVENT_ID + "=?",
                new String[] { String.valueOf(eventId) }, null, null, null, null);

        ArrayList<PersonModel> eventMembers = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Log.d("Retrieval", cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME)));
            PersonModel person = new PersonModel(cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME)), "", "");
            eventMembers.add(person);
        }
        return eventMembers;
    }

    // Getting All Trips
    public ArrayList<EventModel> getAllEvents() {
        ArrayList<EventModel> eventList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            EventModel event = new EventModel(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6), getEventMembers(cursor.getInt(0)));
            eventList.add(event);
        }
        cursor.close();
        return eventList;
    }

    // Getting trips Count
    public int getTripCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }


}
