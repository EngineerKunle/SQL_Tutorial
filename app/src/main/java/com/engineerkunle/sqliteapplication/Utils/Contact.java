package com.engineerkunle.sqliteapplication.Utils;

/**
 * Created by EngineerKunle on 26/09/2016.
 */

public class Contact {

    private long _id;
    private String _name;
    private String _phone_number;

    public Contact(long _id, String _name, String _phone_number) {
        this._id = _id;
        this._name = _name;
        this._phone_number = _phone_number;
    }

    public Contact(String _name, String _phone_number) {
        this._name = _name;
        this._phone_number = _phone_number;
    }

    public Contact() {

    }

    public void setId(long _id) {
        this._id = _id;
    }

    public long getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public String getPhoneNumber() {
        return _phone_number;
    }

    public void setPhoneNumber(String _phone_number) {
        this._phone_number = _phone_number;
    }
}
