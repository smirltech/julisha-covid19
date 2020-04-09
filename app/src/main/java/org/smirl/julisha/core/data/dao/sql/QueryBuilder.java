package org.smirl.julisha.core.data.dao.sql;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Marien MUPENDA
 * Date 13/12/2019
 * Time  22:53
 * <p>
 * QueryBuilder definie de methode utiles a construction des requettes SQL
 */

public class QueryBuilder {
    private String table;
    private String[] columns;
    private Condition[] conditions;
    private Order order;
    private Junction[] junctions;
    private Entry[] entries;
    private Limit limit;


    /**
     * Specifie les noms des colonnes ou seront selectionnees les donnees.
     *
     * @param projectionIn
     * @return
     */
    public QueryBuilder select(String... projectionIn) {
        columns = projectionIn;
        return this;
    }

    /**
     * Specifie le nom de la table cible
     *
     * @param table
     * @return
     */
    public QueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    /**
     * Definie les conditions de selions
     *
     * @param conds
     * @return
     */
    public QueryBuilder where(Condition... conds) {
        this.conditions = conds;
        return this;
    }


    public QueryBuilder where(String column, Object value) {
        this.conditions = new Condition[1];
        this.conditions[0] = new Condition(column, value);
        return this;
    }

    public QueryBuilder limit(int value) {
        this.limit = new Limit(value);
        return this;
    }


    public QueryBuilder order(String column, String order) {
        this.order = new Order(column, order);
        return this;
    }

    private Object check4null(Object object) {
        return (object != null) ? object : "";
    }


    private String obj2Str(Object[] items, String sep) {
        return obj2Str(items, "", sep);
    }

    private String obj2Str(Object[] items) {
        return obj2Str(items, "");
    }

    private String obj2Str(Object[] items, String def, String sep) {
        StringBuilder conds = new StringBuilder();

        if (items != null && items.length > 0) {
            conds = new StringBuilder();
            for (int i = 0; i < items.length; i++) {
                Object item = items[i];

                conds.append(item);
                if (i == items.length - 1) break;
                conds.append(sep);
            }
        } else {
            conds.append(def);
        }
        return conds.toString();
    }


    private String getConds() {
        StringBuilder conds = new StringBuilder();
        if (conditions != null && conditions.length > 0) {
            conds = new StringBuilder("\nWHERE ");
            for (int i = 0; i < conditions.length; i++) {

                Condition item = conditions[i];

                String sep = item.separator.trim();
                if (i > 0 && i <= conditions.length) {
                    conds.append(" " + sep + " ");
                }

                conds.append(item);

            }
        }
        return conds.toString();
    }


    public QueryBuilder join(Junction... junctions) {
        if (this.junctions == null || this.junctions.length == 0) {
            this.junctions = junctions;
        } else {
            ArrayList<Junction> tempList = new ArrayList<>();

            tempList.addAll(Arrays.asList(this.junctions));
            tempList.addAll(Arrays.asList(junctions));

            this.junctions = tempList.toArray(this.junctions);
        }
        return this;

    }

    public QueryBuilder insert(Entry... entries) {
        this.entries = entries;
        return this;
    }

    public QueryBuilder update(Entry... entries) {
        this.entries = entries;
        return this;
    }

    public QueryBuilder into(String table) {
        this.table = table;
        return this;
    }


    /* MUTATEURS */

    public String get() {
        String sql = String.format("SELECT %s FROM %s %s %s %s %s", obj2Str(columns, "*", ","), check4null(table), obj2Str(junctions), getConds(), check4null(order), check4null(limit));
        clean();
        return sql;
    }

    public String put() {
        String sql = String.format("INSERT INTO %s SET %s", table, obj2Str(entries, ","));
        clean();
        return sql;

    }

    public String set() {
        String sql = String.format("UPDATE %s SET %s %s", table, obj2Str(entries, ","), getConds());
        clean();
        return sql;

    }

    /* ------ FIN  MUTATEURS ---------*/

    private void clean() {
        this.table = null;
        this.columns = null;
        this.conditions = null;
        this.order = null;
        this.junctions = null;
        this.entries = null;
        this.limit = null;
    }

}
