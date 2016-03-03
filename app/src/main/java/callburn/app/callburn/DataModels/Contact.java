package callburn.app.callburn.DataModels;

import java.io.Serializable;

/**
 * Created by Bloom on 11/1/2016.
 */
public class Contact implements Serializable {

    private String name, number;
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Contact() {
    }
}
