package omkar.com.callsettings.model;

import java.io.Serializable;

/**
 * Created by AnilOmkar on 20/7/15.
 */
public class PrefixerBean implements Serializable {
    private String name;
    private String startingWith;
    private String replaceWith;

    public void setStartingWith(String startingWith) {
        this.startingWith = startingWith;
    }

    public String getStartingWith() {
        return startingWith;
    }

    public void setReplaceWith(String replaceWith) {
        this.replaceWith = replaceWith;
    }

    public String getReplaceWith() {
        return replaceWith;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
