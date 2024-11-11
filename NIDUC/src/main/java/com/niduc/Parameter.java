package com.niduc;

import java.util.ArrayList;
import java.util.List;

public class Parameter {
    private final String id;
    private final Class<?> type;
    private final String displayName;

    public Parameter(String id, Class<?> type, String displayName) {
        this.id = id;
        this.type = type;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getID() {
        return this.id;
    }

    public Class<?> getType() {
        return this.type;
    }

    public static Parameter findById(List<Parameter> parameters, String id) {
        for (Parameter parameter : parameters) {
            if (parameter.getID().equals(id)) {
                return parameter;
            }
        }
        return null;
    }
}
