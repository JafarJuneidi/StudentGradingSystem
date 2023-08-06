import java.io.Serializable;

public record UpdateRoleRequest(int roleId, String roleName) implements Request, Serializable {
}
