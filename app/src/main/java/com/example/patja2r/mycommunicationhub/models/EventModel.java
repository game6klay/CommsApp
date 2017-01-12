package com.example.patja2r.mycommunicationhub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patja2r on 6/20/2016.
 */
public class EventModel implements Parcelable {

    public String getGroupType() {
        return groupType;
    }

    public String getEventType() {
        return eventType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getReminderOption() {
        return reminderOption;
    }

    public List<PersonModel> getEventMembers() {
        return eventMembers;
    }

    public String getLocation() {
        return location;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setReminderOption(String reminderOption) {
        this.reminderOption = reminderOption;
    }

    public void setEventMembers(List<PersonModel> eventMembers) {
        this.eventMembers = eventMembers;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    private String eventDate;
    private String groupType;
    private String eventType;
    private String startTime;
    private String endTime;
    private String reminderOption;
    private List<PersonModel> eventMembers;
    private String location;

    public EventModel(String eventDate, String groupType, String eventType, String startTime, String endTime, String reminderOption, String location, ArrayList<PersonModel> eventMembers)
    {
        this.eventDate= eventDate;
        this.groupType= groupType;
        this.eventType= eventType;
        this.startTime= startTime;
        this.endTime= endTime;
        this.reminderOption= reminderOption;
        this.eventMembers= eventMembers;
        this.location= location;
    }

    protected EventModel(Parcel in) {
        eventDate = in.readString();
        groupType = in.readString();
        eventType = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        reminderOption = in.readString();
        eventMembers = in.createTypedArrayList(PersonModel.CREATOR);
        location = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventDate);
        dest.writeString(this.groupType);
        dest.writeString(this.eventType);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.reminderOption);
        dest.writeTypedList(eventMembers);
        dest.writeString(this.location);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        public EventModel createFromParcel(Parcel source) {
            return new EventModel(source);
        }

        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };
}
