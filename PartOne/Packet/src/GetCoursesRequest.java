import java.io.Serializable;

public record GetCoursesRequest(String instructorId) implements Request, Serializable {
}
