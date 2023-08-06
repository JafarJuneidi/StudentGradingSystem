import com.github.freva.asciitable.AsciiTable;
import com.jafar.User;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public record GetUsersResponse(List<User> users) implements Response, Serializable {
    public void print() {
        if (users.size() == 0) {
            System.out.println("========== No Users yet ==========");
            return;
        }

        Field[] fields = User.class.getDeclaredFields();
        String[] headers = new String[fields.length];
        String[][] rows = new String[users.size()][];

        for (int i = 0; i < fields.length; i++) {
            headers[i] = fields[i].getName();
        }

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String[] row = new String[fields.length];
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    row[j] = String.valueOf(fields[j].get(user));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rows[i] = row;
        }
        System.out.println(AsciiTable.getTable(headers, rows));
    }
}
