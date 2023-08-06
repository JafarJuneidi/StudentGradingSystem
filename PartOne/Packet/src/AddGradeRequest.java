import java.io.Serializable;

public record AddGradeRequest(int studentId, int courseId, int grade) implements Request, Serializable {
}
