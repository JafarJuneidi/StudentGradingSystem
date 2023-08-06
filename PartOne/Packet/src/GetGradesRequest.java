import java.io.Serializable;

public record GetGradesRequest(String studentId, String courseId) implements Request, Serializable {
}
