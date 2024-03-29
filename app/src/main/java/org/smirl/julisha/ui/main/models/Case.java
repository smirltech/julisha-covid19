package org.smirl.julisha.ui.main.models;

import org.smirl.julisha.core.DateUtils;
import org.smirl.julisha.core.Julisha;

import java.text.ParseException;
import java.util.Locale;

public class Case {
    public int id;
    public int ville_id;
    public int province_id;
    public int type; // 1=infectés, 2=décédés, 3=guéris
    public String date;
    public String timestamp;
    public int nombre;

    public Case(int id, int ville_id, int province_id, int type, String date, int nombre) {
        this.id = id;
        this.ville_id = ville_id;
        this.province_id = province_id;
        this.type = type;
        this.date = date;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Case{" +
                "id=" + id +
                ", ville_id=" + ville_id +
                ", province_id=" + province_id +
                ", type=" + type +
                ", date='" + date + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", nombre=" + nombre +
                '}';
    }

    public String toText() {
        String tty = Julisha.getType(type).toUpperCase();
        if (nombre < 2) tty = tty.substring(0, tty.length() - 1);
        return Julisha.getVille(ville_id).nom.toUpperCase() /*+ " - " + Julisha.getProvince(province_id).nom.toUpperCase()*/ + " : " +
                nombre + " " + tty;
    }
}
