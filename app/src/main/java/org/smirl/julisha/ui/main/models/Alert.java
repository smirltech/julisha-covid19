package org.smirl.julisha.ui.main.models;

import fnn.smirl.simple.Serializer;

public class Alert {
    public static final String TABLE_NAME = "alert";

    public static class COLUMN {
        public static final String
                ID = "id",
                USER = "user",
                PHONE = "phone",
                COMMUNE_ID = "commune_id",
                SYMPTOMES = "symptomes";


        public static Object getID() {
            return TABLE_NAME + "." + ID;
        }
    }

    public int id;
    public String nom;
    public int ville_id;
    public String phone;
    public String opt;
    public String opt_id;
    public String ville;
    public String ecole;
    public int xp;
    public int pro;
    public double s_wzc;
    public double r_wzc;
    public double wzc;
    public String province;
    public String password;


    public String getId() {
        String _id = this.id + "";
        String prefx = "";

        for (int i = 0; i < 5 - (_id.length()); i++) {
            prefx += "0";
        }

        return prefx + _id;
    }

    @Override
    public String toString() {
        return new Serializer().toJson(this);
    }
}
