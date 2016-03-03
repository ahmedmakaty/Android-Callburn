package callburn.app.callburn.Utils;

import java.io.Serializable;

import callburn.app.callburn.DataModels.Group;

/**
 * Created by Bloom on 18/1/2016.
 */
public class GroupDataWrapper implements Serializable {

    private Group g;

    public GroupDataWrapper(Group g) {
        this.g = g;
    }

    public Group getG() {
        return this.g;
    }
}
