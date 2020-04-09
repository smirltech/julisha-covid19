package org.smirl.julisha.core.data.dao.sql;

/**
 * @author Marien MUPENDA
 * Date 13/12/2019
 * Time  22:05
 */
public class Condition {
    private boolean escape;


    public interface OPERATOR {
        String EQUALS = "=",
                GREATER = ">",
                LESS = "<",
                GREATER_OR_EQUALS = ">=",
                LESS_OR_EQUALS = "<=",
                NOT_EQUALS = LESS + GREATER;


    }

    public interface SEPARATOR {
        String AND = "AND",
                OR = "OR";

    }


    public String separator, key, operator;
    public Object value;

    public Condition(String key, Object value) {
        this(key, value, OPERATOR.EQUALS, SEPARATOR.AND);
    }

    public static Condition build(String key, Object value) {
        return new Condition(key, value);
    }


    public Condition(String key, Object value, String opertator) {
        this(key, value, opertator, SEPARATOR.AND);
    }

    public static Condition build(String key, Object value, String opertator) {
        return new Condition(key, value, opertator);
    }

    public static Condition build(String custom) {
        return new Condition("(", ")", custom);
    }


    public Condition(String key, Object value, String opertator, String separator) {
        this.key = key;
        this.value = ((escape && value instanceof String) ? "'" + ((String) value).replace("'", "''") + "'" : value);
        this.operator = opertator;
        this.separator = separator;

        this.escape = true;
    }


    public static Condition build(String key, Object value, String opertator, String separator) {
        return new Condition(key, value, opertator, separator);
    }

    public void setEscape(boolean escape) {
        this.escape = escape;
    }

    @Override
    public String toString() {
        return key + operator + value;
    }
}
