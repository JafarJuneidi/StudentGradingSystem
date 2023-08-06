import java.io.Serializable;

public record DeleteGradeRequest(int studentId, int courseId) implements Request, Serializable {
}
