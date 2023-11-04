package serealize;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class LessonInfoObject implements Serializable {
    private static final long serialVersionUID = 2L;
    public String number;
    public String name;
    public String room;

    public LessonInfoObject(String number, String name, String room) {
        this.number = number;
        this.name = name;
        this.room = room;
    }

    protected LessonInfoObject(Parcel in) {
        number = in.readString();
        name = in.readString();
        room = in.readString();
    }
}
