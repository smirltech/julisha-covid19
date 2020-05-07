package org.smirl.julisha.ui.main.models;

import java.util.ArrayList;

public class Provinces extends ArrayList<Province> {

    public Province getProvince(int id){
        for(Province p : this){
            if(p.id == id) return  p;
        }
        return null;
    }
}
