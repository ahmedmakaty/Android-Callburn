package callburn.app.callburn.DataModels;

import java.io.Serializable;
import java.util.List;

import callburn.app.callburn.DataModels.Contact;

/**
 * Created by Bloom on 15/1/2016.
 */
public class Group implements Serializable {

    private String name, image;
    private List<Contact> contacts;

    public Group(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {

        this.name = name;
    }
}
