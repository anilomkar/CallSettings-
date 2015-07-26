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

    public PrefixerBean(String name, String startingWith, String replaceWith) {
        this.name = name;
        this.startingWith = startingWith;
        this.replaceWith = replaceWith;
    }

    public PrefixerBean(Parcel in) {
        this.name = in.readString();
        this.startingWith = in.readString();
        this.replaceWith = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(startingWith);
        out.writeString(replaceWith);
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
}
