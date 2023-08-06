import java.io.Serializable;

public record UpdateCourseRequest(int courseId, String courseName, int instructorId) implements Request, Serializable {
}
