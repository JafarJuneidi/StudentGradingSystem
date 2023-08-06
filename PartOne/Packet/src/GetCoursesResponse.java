import com.github.freva.asciitable.AsciiTable;
import com.jafar.Course;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public record GetCoursesResponse(List<Course> courses) implements Response, Serializable {
    public void print() {
        if (courses.size() == 0) {
            System.out.println("========== No Courses yet ==========");
            return;
        }

        Field[] fields = Course.class.getDeclaredFields();
        String[] headers = new String[fields.length];
        String[][] rows = new String[courses.size()][];

        for (int i = 0; i < fields.length; i++) {
            headers[i] = fields[i].getName();
        }

        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            String[] row = new String[fields.length];
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    row[j] = String.valueOf(fields[j].get(course));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rows[i] = row;
        }
        System.out.println(AsciiTable.getTable(headers, rows));
    }
}
