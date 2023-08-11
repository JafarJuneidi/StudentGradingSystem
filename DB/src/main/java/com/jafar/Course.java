package com.jafar;

import java.io.Serializable;

public record Course(int id, String name, String instructor) implements Serializable {
}