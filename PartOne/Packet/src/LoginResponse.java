import com.jafar.User;

import java.io.Serializable;

public record LoginResponse(User user) implements Response, Serializable {
}
