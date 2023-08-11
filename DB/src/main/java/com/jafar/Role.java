package com.jafar;

import java.io.Serializable;

public record Role(String id, String name) implements Serializable {
}
