import java.io.Serializable;

public record AddRoleRequest(String roleName) implements Request, Serializable {
}
