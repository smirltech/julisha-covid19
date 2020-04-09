package org.smirl.julisha.core.data.rdc;

public class Ville extends Place {
    /**
     * Column names of Commune table in the database
     * Purpose is to facilitate in order to avoid errors in _option handling
     */
    public static String TABLE_NAME = RDC.VILLE_TABLE;


    public static class COLUMN {
        public static final String
                ID = "id",
                NOM = "nom",
                PROVINCE_ID = "province_id";
    }

    public int id;
    public int province_id;

}

