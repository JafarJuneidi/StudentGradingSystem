import java.io.Serializable;

public record AddCourseRequest(String courseName, int instructorId) implements Request, Serializable {
}
