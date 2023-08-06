import com.jafar.*;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Optional;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    ObjectInputStream in;
    private DB db = new DB();

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                Request request = (Request) in.readObject();
                out.writeObject(processRequest(request));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Response processRequest(Request request) {
        if (request instanceof LoginRequest loginRequest) {
            Optional<User> user = db.login(loginRequest.username(), loginRequest.password());
            return user.map(LoginResponse::new).orElse(new LoginResponse(null));
        } else if (request instanceof GetGradesRequest getGradesRequest) {
            List<StudentGrade> grades = db.getStudentGrades(getGradesRequest.studentId(), getGradesRequest.courseId());
            return new GetGradesResponse(grades);
        } else if (request instanceof GetCoursesRequest getCoursesRequest) {
            List<Course> courses = db.getCourses(getCoursesRequest.instructorId());
            return new GetCoursesResponse(courses);
        } else if (request instanceof GetUsersRequest getUsersRequest) {
            List<User> users = db.getUsers(getUsersRequest.roleId());
            return new GetUsersResponse(users);
        } else if (request instanceof GetRolesRequest getRolesRequest) {
            List<Role> roles = db.getRoles();
            return new GetRolesResponse(roles);
        } else if (request instanceof AddUserRequest addUserRequest) {
            boolean result = db.addUser(addUserRequest.username(), addUserRequest.password(), addUserRequest.roleId());
            return new OperationResponse(result);
        } else if (request instanceof AddCourseRequest addCourseRequest) {
            boolean result = db.addCourse(addCourseRequest.courseName(), addCourseRequest. instructorId());
            return new OperationResponse(result);
        } else if (request instanceof  AddRoleRequest addRoleRequest) {
            boolean result = db.addRole(addRoleRequest.roleName());
            return new OperationResponse(result);
        } else if (request instanceof AddGradeRequest addGradeRequest) {
            boolean result = db.addGrade(addGradeRequest.studentId(), addGradeRequest.courseId(), addGradeRequest.grade());
            return new OperationResponse(result);
        } else if (request instanceof UpdateUserRequest updateUserRequest) {
            boolean result = db.updateUser(updateUserRequest.id(), updateUserRequest.username(), updateUserRequest.password(), updateUserRequest.roleId());
            return new OperationResponse(result);
        } else if (request instanceof UpdateCourseRequest updateCourseRequest) {
            boolean result = db.updateCourse(updateCourseRequest.courseId(), updateCourseRequest.courseName(), updateCourseRequest.instructorId());
            return new OperationResponse(result);
        } else if (request instanceof UpdateRoleRequest updateRoleRequest) {
            boolean result = db.updateRole(updateRoleRequest.roleId(), updateRoleRequest.roleName());
            return new OperationResponse(result);
        } else if (request instanceof UpdateGradeRequest updateGradeRequest) {
            boolean result = db.updateGrade(updateGradeRequest.studentId(), updateGradeRequest.courseId(), updateGradeRequest.grade());
            return new OperationResponse(result);
        } else if (request instanceof DeleteUserRequest deleteUserRequest) {
            boolean result = db.deleteUser(deleteUserRequest.userId());
            return new OperationResponse(result);
        } else if (request instanceof DeleteGradeRequest deleteGradeRequest) {
            boolean result = db.deleteGrade(deleteGradeRequest.studentId(), deleteGradeRequest.courseId());
            return new OperationResponse(result);
        }
        return null;
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(6666);
    }
}