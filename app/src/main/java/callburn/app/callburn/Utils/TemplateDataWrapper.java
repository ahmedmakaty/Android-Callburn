package callburn.app.callburn.Utils;

import java.io.Serializable;

import callburn.app.callburn.DataModels.Template;

/**
 * Created by Bloom on 18/1/2016.
 */
public class TemplateDataWrapper implements Serializable {

    private Template t;

    public TemplateDataWrapper(Template t) {
        this.t = t;
    }

    public Template getT() {
        return this.t;
    }
}
