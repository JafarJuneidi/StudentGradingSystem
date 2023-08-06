package com.jafar;

import java.io.Serializable;

public record Student(String id, String name) implements Serializable {
}