package org.smirl.julisha.core.data.dao.sql;

/**
 * @author Marien MUPENDA
 * Date 13/12/2019
 * Time  23:05
 */
public class Order {
    public static final String ASC = "ASC", DESC = "DESC";
    private final String column;
    private final String order;

    public Order(String column, String order) {
        this.column = column;
        this.order = order;
    }

    public static Order build(String column, String order) {
        return new Order(column, order);
    }


    @Override
    public String toString() {
        return "\nORDER BY " + column + " " + order;
    }
}
