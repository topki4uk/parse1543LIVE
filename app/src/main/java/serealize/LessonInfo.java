package serealize;

import androidx.annotation.NonNull;

import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LessonInfo implements Serializable {
    private final List<LessonInfoObject > lessonsInfo = new ArrayList<>();
    private final List<LessonsPerDay > lessons = new ArrayList<>();

    public LessonInfo(Elements lessonNums, Elements lessonNames, Elements roomNums) {
        for (int i = 0; i < lessonNames.size(); ++i) {
            LessonInfoObject lessonInfo = new LessonInfoObject(
                    lessonNums.get(i).text(),
                    lessonNames.get(i).text(),
                    roomNums.get(i).text()
            );
            lessonsInfo.add(lessonInfo);
        }
    }

    public void convert(int allClassNum) {
        for (int i = 0; i < allClassNum; i++) {
            LessonsPerDay lessonsPerDay = new LessonsPerDay();
            // List<LessonInfoObject> lessonsPerDay = new ArrayList<>();

            for (int j = 0; j < 8; ++j) {
                lessonsPerDay.add(
                        lessonsInfo.get(
                                i + (allClassNum*j)
                        )
                );
            }

            lessons.add(lessonsPerDay);
        }
    }

    public ArrayList<LessonsPerDay> getLessons() {
        return (ArrayList<LessonsPerDay>) lessons;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder finalString = new StringBuilder();

        for (LessonsPerDay lesson : lessons) {
            for (LessonInfoObject lessonObject : lesson.lessons) {
                finalString.append(lessonObject);
            }
            finalString.append("\n");
        }

        return finalString.toString();
    }
}
