package serealize;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LessonsPerDay implements Serializable {
    private static final long serialVersionUID = 1L;
    public List<LessonInfoObject> lessons = new ArrayList<>();

    public void add(LessonInfoObject lesson) {
        lessons.add(lesson);
    }

    public LessonInfoObject get(int i) {
        return lessons.get(i);
    }

    @NonNull
    @Override
    public String toString() {
        return lessons.toString();
    }
}
