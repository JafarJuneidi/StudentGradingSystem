import java.io.Serializable;

public record OperationResponse(boolean success) implements Response, Serializable {
    public void print() {
        if (success()) {
            System.out.println("Operation executed successfully");
        } else {
            System.out.println("Failed to execute operation");
        }
    }
}

