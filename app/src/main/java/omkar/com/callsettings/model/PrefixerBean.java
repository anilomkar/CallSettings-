package omkar.com.callsettings.model;

import android.os.Parcel;
import android.os.Parcelable;

import omkar.com.callsettings.Prefixer;

/**
 * Created by AnilOmkar on 20/7/15.
 */
public class PrefixerBean implements Parcelable {
    private String name;
    private String startingWith;
    private String replaceWith;
    private boolean isEnabled;

    public PrefixerBean(String name, String startingWith, String replaceWith) {
        this.name = name;
        this.startingWith = startingWith;
        this.replaceWith = replaceWith;
        this.isEnabled = true;
    }

    public PrefixerBean(Parcel in) {
        this.name = in.readString();
        this.startingWith = in.readString();
        this.replaceWith = in.readString();
        this.isEnabled = (in.readByte() == 1);
    }

    public String getStartingWith() {
        return startingWith;
    }

    public String getReplaceWith() {
        return replaceWith;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(startingWith);
        out.writeString(replaceWith);
        out.writeByte(isEnabled ? Byte.valueOf("1") : Byte.valueOf("0"));
    }

    public static final Parcelable.Creator<PrefixerBean> CREATOR = new Parcelable.ClassLoaderCreator<PrefixerBean>() {

        @Override
        public PrefixerBean createFromParcel(Parcel in, ClassLoader loader) {
            return new PrefixerBean(in);
        }

        @Override
        public PrefixerBean createFromParcel(Parcel in) {
            return new PrefixerBean(in);
        }

        @Override
        public PrefixerBean[] newArray(int size) {
            return new PrefixerBean[size];
        }

    };

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(null == o) return false;
        if(!(o instanceof PrefixerBean)) return false;

        PrefixerBean other = (PrefixerBean) o;
        if(!this.name.equals(other.name)) return false;
        if(!this.startingWith.equals(other.startingWith)) return false;
        if(!this.replaceWith.equals(other.replaceWith)) return false;
        if(this.isEnabled != other.isEnabled) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + startingWith.hashCode() + replaceWith.hashCode() + (isEnabled ? 1 : 0);
    }
}
