package callburn.app.callburn.Utils;

import java.io.Serializable;
import java.util.ArrayList;

import callburn.app.callburn.DataModels.Contact;

public class DataWrapper implements Serializable {

    private ArrayList<Contact> contacts;

    public DataWrapper(ArrayList<Contact> data) {
        this.contacts = data;
    }

    public ArrayList<Contact> getContacts() {
        return this.contacts;
    }

}
