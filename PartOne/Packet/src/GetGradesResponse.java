import com.github.freva.asciitable.AsciiTable;
import com.jafar.StudentGrade;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public record GetGradesResponse(List<StudentGrade> grades) implements Response, Serializable {
    public void print() {
        if (grades.size() == 0) {
            System.out.println("========== No Grades yet ==========");
            return;
        }

        Field[] fields = StudentGrade.class.getDeclaredFields();
        String[] headers = new String[fields.length];
        String[][] rows = new String[grades.size()][];

        for (int i = 0; i < fields.length; i++) {
            headers[i] = fields[i].getName();
        }

        for (int i = 0; i < grades.size(); i++) {
            StudentGrade studentGrade = grades.get(i);
            String[] row = new String[fields.length];
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    row[j] = String.valueOf(fields[j].get(studentGrade));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rows[i] = row;
        }
        System.out.println(AsciiTable.getTable(headers, rows));
    }
}
