package webUtils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import serealize.LessonInfo;
import serealize.LessonsPerDay;

public class HtmlParser implements Serializable {
    private static final long serialVersionUID = 3L;

    private List<String> schoolClasses = new ArrayList<>();
    private final Map<String, Map<String, LessonsPerDay> > allLessons = new HashMap<>();
    public static String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};



    public HtmlParser(String data) {
        Document data1 = Jsoup.parse(data);
        Element table = data1.selectFirst("table");

        if (table != null) {
            Elements schoolClasses = table.getElementsByClass("hcenter bold-left bold-right bold bkblue");

            for (Element schoolClass : schoolClasses) {
                this.schoolClasses.add(schoolClass.text());
            }

            Elements allTr = table.select("tr");
            allTr = new Elements(allTr.subList(2, allTr.size()));

            List<LessonInfo> lessonsInfo = new ArrayList<>();
            for (int i = 0; i < allTr.size()-6; i+=9) {
                Elements lessonNums = new Elements();
                Elements lessonNames = new Elements();
                Elements roomNums = new Elements();

                for (int j = 0; j < 8; ++j) {
                    Element tr = allTr.get(i+j);

                    lessonNums.addAll(tr.getElementsByClass("bold-left midgray larger"));
                    lessonNames.addAll(tr.getElementsByClass("left bold nowrap"));
                    roomNums.addAll(tr.getElementsByClass("right bold"));
                }

                LessonInfo lessons = new LessonInfo(lessonNums, lessonNames, roomNums);
                lessons.convert(schoolClasses.size());

                lessonsInfo.add(lessons);
            }
                for (int k = 0; k < lessonsInfo.get(0).getLessons().size(); k++) {
                    Map<String, LessonsPerDay> lessonsPerDay = new HashMap<>();

                    for (int j = 0; j < 6; ++j) {
                        lessonsPerDay.put(HtmlParser.days[j], lessonsInfo.get(j).getLessons().get(k));
                    }
                    allLessons.put(schoolClasses.get(k).text(), lessonsPerDay);
                }
            }
        }

    public Map<String, Map<String, LessonsPerDay> > getAllLessons() {
        return allLessons;
    }

    public List<String> getClasses() {
        return schoolClasses;
    }

}
