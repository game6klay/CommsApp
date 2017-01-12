package com.example.patja2r.mycommunicationhub.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by patja2r on 6/20/2016.
 */
public class PersonModel implements Parcelable {
    private String name;
    private String email;
    private String phone;

    // Member fields should exist here, what else do you need for a event
    // Please add additional fields

    public PersonModel(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phone);
    }



    protected PersonModel(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
    }

    /**
     * Parcelable creator. Do not modify this function.
     */

    public static final Creator<PersonModel> CREATOR = new Creator<PersonModel>() {
        public PersonModel createFromParcel(Parcel source) {
            return new PersonModel(source);
        }

        public PersonModel[] newArray(int size) {
            return new PersonModel[size];
        }
    };



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
