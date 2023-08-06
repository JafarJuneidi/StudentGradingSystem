import java.io.Serializable;

public record UpdateUserRequest(int id, String username, String password, int roleId) implements Request, Serializable {
}
