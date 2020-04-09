package org.smirl.julisha.core.data.rdc;

public class Commune extends Place{
    /**
     * Column names of Commune table in the database
     * Purpose is to facilitate in order to avoid errors in _option handling
     */

    public  static String TABLE_NAME = RDC.COMMUNE_TABLE;

    public static class COLUMN {
        public static final String
                ID = "id",
                NOM = "nom",
                VILLE_ID = "ville_id";
    }

    public int id;
    public int ville_id;

}
