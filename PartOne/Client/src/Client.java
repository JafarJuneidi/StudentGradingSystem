import com.jafar.User;

import java.net.*;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    static User user = null;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Response sendRequest(Request request) {
        Response res = null;
        try {
            out.writeObject(request);
            res = (Response) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Client client = new Client();
        client.startConnection("127.0.0.1", 6666);

        while (user == null) {
            System.out.println("Login");

            System.out.print("username> ");
            String username = scanner.nextLine();

            System.out.print("password> ");
            String password = scanner.nextLine();

            LoginResponse response = (LoginResponse) client.sendRequest(new LoginRequest(username, password));
            user = response.user();
        }

        while (true) {
            switch (user.role()) {
                case "Admin": {
                    showAdminOptions();
                    break;
                }
                case "Instructor": {
                    showInstructorOptions();
                    break;
                }
                case "Student": {
                    showStudentOptions();
                    break;
                }
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); // to prevent future nextLine problems

            if (choice == 100) {
                System.out.println("Bye");
                break;
            }

            switch (user.role()) {
                case "Admin": {
                    handleAdminChoice(choice, scanner);
                    break;
                }
                case "Instructor": {
                    handleInstructorChoice(choice, scanner);
                    break;
                }
                case "Student": {
                    handleStudentChoice(choice, scanner);
                    break;
                }
            }

        }

        client.stopConnection();
        scanner.close();
    }

    private static void showAdminOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. GetUsers");
        System.out.println("2. GetCourses");
        System.out.println("3. GetRoles");
        System.out.println("4. GetGrades");
        System.out.println("5. AddUser");
        System.out.println("6. AddCourse");
        System.out.println("7. AddRole");
        System.out.println("8. AddGrade");
        System.out.println("9. UpdateUser");
        System.out.println("10. UpdateCourse");
        System.out.println("11. UpdateRole");
        System.out.println("12. UpdateGrade");
        System.out.println("13. DeleteStudent");
        System.out.println("14. DeleteGrade");
        System.out.println("100. Exit");
        System.out.print("choose> ");
    }

    private static void showInstructorOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. GetStudents");
        System.out.println("2. GetCourses");
        System.out.println("3. GetInstructorCourses");
        System.out.println("4. GetGrades");
        System.out.println("5. AddInstructorGrade");
        System.out.println("6. UpdateInstructorGrade");
        System.out.println("7. DeleteInstructorGrade");
        System.out.println("100. Exit");
        System.out.print("choose> ");
    }

    private static void showStudentOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. GetCourses");
        System.out.println("2. GetStudentGrades");
        System.out.println("3. GetStudentCourseGrades");
        System.out.println("100. Exit");
        System.out.print("choose> ");
    }

    private static void handleAdminChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                ((GetUsersResponse) sendRequest(View.getUsersView(scanner))).print();
                break;
            case 2:
                ((GetCoursesResponse) sendRequest(View.getCoursesView(scanner))).print();
                break;
            case 3:
                ((GetRolesResponse) sendRequest(View.getRolesView(scanner))).print();
                break;
            case 4:
                ((GetGradesResponse) sendRequest(View.getGradesView(scanner))).print();
                break;
            case 5:
                ((OperationResponse) sendRequest(View.addUserView(scanner))).print();
                break;
            case 6:
                ((OperationResponse) sendRequest(View.addCourseView(scanner))).print();
                break;
            case 7:
                ((OperationResponse) sendRequest(View.addRoleView(scanner))).print();
                break;
            case 8:
                ((OperationResponse) sendRequest(View.addGradeView(scanner))).print();
                break;
            case 9:
                ((OperationResponse) sendRequest(View.updateUserView(scanner))).print();
                break;
            case 10:
                ((OperationResponse) sendRequest(View.updateCourseView(scanner))).print();
                break;
            case 11:
                ((OperationResponse) sendRequest(View.updateRoleView(scanner))).print();
                break;
            case 12:
                ((OperationResponse) sendRequest(View.updateGradeView(scanner))).print();
                break;
            case 13:
                Optional<Request> optionalRequest = View.deleteUserView(scanner, user);
                if (optionalRequest.isPresent()) {
                    ((OperationResponse) sendRequest(optionalRequest.get())).print();
                }
                break;
            case 14:
                ((OperationResponse) sendRequest(View.deleteGradeView(scanner))).print();
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
    }

    private static void handleInstructorChoice(int choice, Scanner scanner) {
//        System.out.println("1. GetStudents");
//        System.out.println("2. GetCourses");
//        System.out.println("3. GetInstructorCourses");
//        System.out.println("4. GetGrades");
//        System.out.println("5. AddInstructorGrade");
//        System.out.println("6. UpdateInstructorGrade");
//        System.out.println("7. DeleteInstructorGrade");
        switch (choice) {
            case 1:
                ((GetUsersResponse) sendRequest(View.getUsersView(scanner))).print();
                break;
            case 2:
                ((GetGradesResponse) sendRequest(View.getStudentGradesView(user.id()))).print();
                break;
            case 3:
                ((GetGradesResponse) sendRequest(View.getStudentCourseGradesView(scanner, user.id()))).print();
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
    }

    private static void handleStudentChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                ((GetCoursesResponse) sendRequest(View.getCoursesView(scanner))).print();
                break;
            case 2:
                ((GetGradesResponse) sendRequest(View.getStudentGradesView(user.id()))).print();
                break;
            case 3:
                ((GetGradesResponse) sendRequest(View.getStudentCourseGradesView(scanner, user.id()))).print();
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
                break;
        }
    }
}