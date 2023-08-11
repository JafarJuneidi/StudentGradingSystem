import com.jafar.User;

import java.util.Optional;
import java.util.Scanner;

public class View {
    public static Request getUsersView(Scanner scanner) {
        System.out.println("Provide info (0 for all roles):");
        System.out.print("role_id> ");
        String roleId = Integer.toString(scanner.nextInt());
        System.out.println("You selected 'GetUsers' for role_id: " + roleId);
        return new GetUsersRequest(!roleId.equals("0") ? roleId: null);
    }

    public static Request getCoursesView() {
        System.out.println("You selected 'GetCourses'");
        return new GetCoursesRequest(null);
    }

    public static Request getRolesView(Scanner scanner) {
        System.out.println("You selected 'GetRoles'");
        return new GetRolesRequest();
    }

    public static Request getGradesView(Scanner scanner) {
        System.out.println("You selected 'GetGrades'");
        return new GetGradesRequest(null, null);
    }

    public static Request addUserView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("username> ");
        String username = scanner.next();
        System.out.print("password> ");
        String password = scanner.next();
        System.out.print("role_id> ");
        int userRoleId = scanner.nextInt();

        System.out.println("Adding user: {username: " + username + ", password: " + password + ", role_id: " + userRoleId);
        return new AddUserRequest(username, password, userRoleId);
    }

    public static Request addCourseView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("course_name> ");
        String courseName = scanner.nextLine();
        System.out.print("instructor_id> ");
        int instructorId = scanner.nextInt();

        System.out.println("Adding course: {course_name: " + courseName + ", instructor_id: " + instructorId);
        return new AddCourseRequest(courseName, instructorId);
    }
    public static Request addRoleView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("role_name> ");
        String roleName = scanner.nextLine();

        System.out.println("Adding role: {role_name: " + roleName);
        return new AddRoleRequest(roleName);
    }
    public static Request addGradeView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("student_id> ");
        int studentId = scanner.nextInt();
        System.out.print("course_id> ");
        int courseId = scanner.nextInt();
        System.out.print("grade> ");
        int grade = scanner.nextInt();

        System.out.println("Adding grade: {student_id: " + studentId + ", course_id: " + courseId + ", grade: " + grade);
        return new AddGradeRequest(studentId, courseId, grade);
    }

    public static Request updateUserView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("user_id> ");
        int userId = scanner.nextInt();
        System.out.print("new username> ");
        String username = scanner.next();
        System.out.print("new password> ");
        String password = scanner.next();
        System.out.print("new role_id> ");
        int userRoleId = scanner.nextInt();

        System.out.println("Updating user: {id: " + userId + ", username: " + username + ", password: " + password + ", role_id: " + userRoleId);
        return new UpdateUserRequest(userId, username, password, userRoleId);
    }

    public static Request updateCourseView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("course_id> ");
        int courseId = Integer.parseInt(scanner.nextLine());
        System.out.print("new course_name> ");
        String courseName = scanner.nextLine();
        System.out.print("new instructor_id> ");
        int instructorId = scanner.nextInt();

        System.out.println("Updating course: {id: " + courseId + ", course_name: " + courseName + ", instructor_id: " + instructorId);
        return new UpdateCourseRequest(courseId, courseName, instructorId);
    }

    public static Request updateRoleView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("role_id> ");
        int roleId = scanner.nextInt();
        System.out.print("new role_name> ");
        String roleName = scanner.next();

        System.out.println("Updating role: {id: " + roleId + ", role_name: " + roleName);
        return new UpdateRoleRequest(roleId, roleName);
    }

    public static Request updateGradeView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("student_id> ");
        int studentId = scanner.nextInt();
        System.out.print("course_id> ");
        int courseId = scanner.nextInt();
        System.out.print("grade> ");
        int grade = scanner.nextInt();

        System.out.println("Updating grade: {student_id: " + studentId + ", course_id: " + courseId + ", grade: " + grade);
        return new UpdateGradeRequest(studentId, courseId, grade);
    }

    public static Optional<Request> deleteUserView(Scanner scanner, User currentUser) {
        System.out.println("Provide info:");
        System.out.print("user_id> ");
        int userId = scanner.nextInt();

        if (Integer.toString(userId).equals(currentUser.id())) {
            System.out.println("Can't delete yourself!");
            return Optional.empty();
        }

        System.out.println("If you delete a student, all his grades will be deleted as well!");
        System.out.print("Are you sure you want to continue (Y/n)> ");
        String res = scanner.next();
        if (!res.equals("Y")) {
            return Optional.empty();
        }

        return Optional.of(new DeleteUserRequest(userId));
    }

    public static Request deleteGradeView(Scanner scanner) {
        System.out.println("Provide info:");
        System.out.print("student_id> ");
        int studentId = scanner.nextInt();
        System.out.print("course_id> ");
        int courseId = scanner.nextInt();

        return new DeleteGradeRequest(studentId, courseId);
    }

    // Student Views
    public static Request getStudentGradesView(String studentId) {
        System.out.println("You selected 'GetStudentGrades'");
        return new GetGradesRequest(studentId, null);
    }

    public static Request getStudentCourseGradesView(Scanner scanner, String studentId) {
        System.out.println("Provide info:");
        System.out.print("course_id> ");
        String courseId = Integer.toString(scanner.nextInt());
        System.out.println("You selected 'GetStudentCourseGrades' for course_id: " + courseId);

        return new GetGradesRequest(studentId, courseId);
    }
}