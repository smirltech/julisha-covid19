package org.smirl.julisha.ui.main.models;

import java.util.ArrayList;
import java.util.HashSet;

public class Villes extends ArrayList<Ville> {


    public Ville getVille(int id){
        for(Ville p : this){
            if(p.id == id) return  p;
        }
        return null;
    }

    public HashSet<Ville> getVilles(int provinceid){
        HashSet<Ville> vs = new HashSet<>();
        for(Ville p : this){
            if(p.province_id == provinceid) vs.add(p);
        }
        return vs;
    }
}
