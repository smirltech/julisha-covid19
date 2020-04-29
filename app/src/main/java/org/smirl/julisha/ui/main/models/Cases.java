package org.smirl.julisha.ui.main.models;

import org.smirl.julisha.core.Julisha;

import java.util.ArrayList;
import java.util.HashSet;

public class Cases extends ArrayList<Case> {

  public Cases cases(int provinceid) {
    Cases cc = new Cases();
    for (Case c : this) {
     // Ville v = Julisha.getVille(c.ville_id);
      if (c.province_id == provinceid) cc.add(c);
    }
    return cc;
  }

  public Cases casesVille(int villeid) {
    Cases cc = new Cases();
    for (Case c : this) {
      if (c.ville_id == villeid) cc.add(c);
    }
    return cc;
  }

  public Cases casesProvince(int provinceid) {
    Cases cc = new Cases();
    for (Case c : this) {
      if (c.province_id == provinceid) cc.add(c);
    }
    return cc;
  }

  public Cases casesBy(int type) {
    Cases cc = new Cases();
    for (Case c : this) {
      if (c.type == type) cc.add(c);
    }
    return cc;
  }

  public Case getCase(String date) {
    for (Case c : this) {
      if (c.date.equalsIgnoreCase(date)) return c;
    }
    return null;
  }

  public Cases getCases(String date) {
    Cases cc = new Cases();
    for (Case c : this) {
      if (c.date.equalsIgnoreCase(date)) cc.add(c);
    }
    return cc;
  }

  public int number(int villeid, int type) {
    int n = 0;
    for (Case c : this) {
      if (c.ville_id == villeid && c.type == type) n += c.nombre;
    }
    return n;
  }

  public int max() {
   return summarizeCountry().infected;
  }

  public int numberP(int provinceid, int type) {
    int n = 0;
    for (Case c : this) {
      if (Julisha.getVille(c.ville_id).province_id == provinceid && c.type == type) n += c.nombre;
    }
    return n;
  }

  public CasesSummary summarize(int villeid) {
    int i = 0, d = 0, h = 0;
    CasesSummary ss = new CasesSummary();
    ss.id = villeid;

    for (Case c : this) {
      if (ss.id > 0 && c.ville_id == ss.id) {
        if (c.type == 1) i += c.nombre;
        if (c.type == 2) d += c.nombre;
        if (c.type == 3) h += c.nombre;
      }
    }

    ss.infected = i;
    ss.dead = d;
    ss.healed = h;
    return ss;
  }

  public CasesSummary summarizeProvince(int provinceid) {
    int i = 0, d = 0, h = 0;
    CasesSummary ss = new CasesSummary();
    ss.id = provinceid;

    for (Case c : this) {
      if (ss.id > 0 && c.province_id == ss.id) {
        if (c.type == 1) i += c.nombre;
        if (c.type == 2) d += c.nombre;
        if (c.type == 3) h += c.nombre;
      }
    }

    ss.infected = i;
    ss.dead = d;
    ss.healed = h;
    return ss;
  }

  public CasesSummary summarizeCountry() {
    int i = 0, d = 0, h = 0;
    CasesSummary ss = new CasesSummary();
    ss.id = 0;

    for (Case c : this) {
      if (c.type == 1) i += c.nombre;
      if (c.type == 2) d += c.nombre;
      if (c.type == 3) h += c.nombre;
    }

    ss.infected = i;
    ss.dead = d;
    ss.healed = h;
    return ss;
  }

  public HashSet<Integer> getVilleIds() {
    HashSet<Integer> s = new HashSet();
    for (Case i : this) {
      s.add(i.ville_id);
    }

    return s;
  }

  public HashSet<Integer> getProvinceIds() {
    HashSet<Integer> s = new HashSet();
    for (Case i : this) {
      s.add(i.province_id);
    }

    return s;
  }
}
