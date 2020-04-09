package org.smirl.julisha.core.data.dao.sql;

/**
 * @author Marien MUPENDA
 * Date 14/12/2019
 * Time  16:05
 */

public class Junction {
    private String table, type;
    private Condition condition;

    public Junction(String table, Condition condition, String type) {
        this.condition = condition;
        this.table = table;
        this.type = type;

        condition.setEscape(false);
    }

    public Junction(String table, Condition condition) {
        this(table, condition, "");
    }

    public static Junction build(String table, Condition condition) {
        return new Junction(table, condition);
    }

    public static Junction build(String table, Condition condition, String type) {
        return new Junction(table, condition, type);
    }


    @Override
    public String toString() {
        return "\n" + getType() + "JOIN " + table + " ON " + condition;
    }

    private String getType() {
        return (type.trim().length() > 0) ? type + " " : "";
    }
}
