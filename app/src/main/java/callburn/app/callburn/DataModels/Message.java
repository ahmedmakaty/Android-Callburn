package callburn.app.callburn.DataModels;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Bloom on 13/1/2016.
 */
public class Message implements Serializable {

    private long date, duration;
    private String status, message, price;
    private boolean text;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private String url;
    private String group;

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

    public Message() {

    }

    public Message(String message, boolean text, String url, long d, String g) throws IOException {
        this.message = message;
        this.text = text;
        this.url = url;
        this.duration = d;
        this.group = g;
        this.date = System.currentTimeMillis();
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
