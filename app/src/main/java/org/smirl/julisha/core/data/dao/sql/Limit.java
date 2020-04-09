package org.smirl.julisha.core.data.dao.sql;

public class Limit {
    private int value;

    public Limit(int value) {
        this.value = value;
    }

    public static Limit build(int value) {
        return new Limit(value);
    }

    @Override
    public String toString() {
        return "LIMIT " + value;
    }

}
