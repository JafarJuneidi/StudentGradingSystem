package com.jafar;

import java.io.Serializable;

public record User(String id, String name, String role) implements Serializable {
}
