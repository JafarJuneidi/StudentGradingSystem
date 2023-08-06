import java.io.Serializable;

public record AddUserRequest(String username, String password, int roleId) implements Request, Serializable {
}
