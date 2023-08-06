import com.github.freva.asciitable.AsciiTable;
import com.jafar.Role;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public record GetRolesResponse(List<Role> roles) implements Response, Serializable {
    public void print() {
        if (roles.size() == 0) {
            System.out.println("========== No Roles yet ==========");
            return;
        }

        Field[] fields = Role.class.getDeclaredFields();
        String[] headers = new String[fields.length];
        String[][] rows = new String[roles.size()][];

        for (int i = 0; i < fields.length; i++) {
            headers[i] = fields[i].getName();
        }

        for (int i = 0; i < roles.size(); i++) {
            Role role = roles.get(i);
            String[] row = new String[fields.length];
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    row[j] = String.valueOf(fields[j].get(role));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rows[i] = row;
        }
        System.out.println(AsciiTable.getTable(headers, rows));
    }
}
