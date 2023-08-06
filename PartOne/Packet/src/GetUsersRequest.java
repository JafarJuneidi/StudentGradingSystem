import java.io.Serializable;

public record GetUsersRequest(String roleId) implements Request, Serializable {
}
