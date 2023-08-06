package com.jafar;

import java.io.Serializable;

public class Role implements Serializable {
    private String id;
    private String name;

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
