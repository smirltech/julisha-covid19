package org.smirl.julisha.ui.main.models;

import org.smirl.julisha.core.Julisha;

public class Ville {
   public int id;
   public int province_id;
   public String nom;

    public Ville(int id, int province_id, String nom) {
        this.id = id;
        this.province_id = province_id;
        this.nom = nom;
    }

    public String getProvince(){
        return Julisha.provinces().getProvince(province_id).nom;
    }
}
