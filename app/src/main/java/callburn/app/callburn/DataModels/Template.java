package callburn.app.callburn.DataModels;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Bloom on 13/1/2016.
 */
public class Template implements Serializable {

    private String name, status, message, price, url, group;
    private long date, duration;
    private boolean text;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isText() {
        return text;
    }

    public void setText(boolean text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Template() {
    }

    public Template(String name, String message, boolean text, String url, long d, String g) throws IOException {
        this.message = message;
        this.text = text;
        this.url = url;
        this.duration = d;
        this.group = g;
        this.date = System.currentTimeMillis();
        this.name = name;

    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
