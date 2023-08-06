import java.io.Serializable;

public record DeleteUserRequest(int userId) implements Request, Serializable {
}
