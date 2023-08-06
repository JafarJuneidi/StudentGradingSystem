import java.io.Serializable;

public record UpdateGradeRequest(int studentId, int courseId, int grade) implements Request, Serializable {
}
