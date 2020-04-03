package org.smirl.julisha.core;

import org.smirl.julisha.ui.main.models.*;

import fnn.smirl.simple.Serializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class Julisha {
  private static Cases cases = new Cases();
  private static Villes villes = new Villes();
  private static Provinces provinces = new Provinces();
  private static CaseGraphs caseGraphs = new CaseGraphs();

  private Julisha() {
  }

  public static Cases cases() {
    return cases;
  }

  public static Cases cases(int province_id) {
    return cases.cases(province_id);
  }

  public static Villes villes() {
    return villes;
  }

  public static Provinces provinces() {
    return provinces;
  }

  public static CaseGraphs caseGraphs() {
    // prepareCaseGraphs();
    return caseGraphs;
  }

  /**
   * Sample data for testing purposes only
   **/
  public static Cases sampleCases() {
    Cases cc = new Cases();
   /*
   cc.add(new Case(1, 1, 1, 1, "2020-02-21", 5));
    cc.add(new Case(2, 1, 1, 2, "2020-02-21", 1));
    cc.add(new Case(3, 1, 1, 3, "2020-02-21", 3));
    cc.add(new Case(4, 3, 2, 1, "2020-02-22", 3));
    */
    return cc;
  }

  public static void generateSampleCases() {
    cases = sampleCases();
  }

  /**
   * ./End Sample data for testing purposes only
   **/
  //loads
  public static void loadProvinces(Provinces p) {
    provinces = p;
  }

  public static void loadVilles(Villes v) {
    villes = v;
  }

  public static void load(Cases c) {
    cases = c;
  }

  //loads from json
  public static void loadProvinces(String jsonString) {
    provinces = new Serializer().fromJson(jsonString, Provinces.class);
  }

  public static void loadVilles(String jsonString) {
    villes = new Serializer().fromJson(jsonString, Villes.class);
  }

  public static void load(String jsonString) {
    cases = new Serializer().fromJson(jsonString, Cases.class);
  }

  //adds

  public static void addProvince(Province province) {
    provinces.add(province);
  }

  public static void addVille(Ville ville) {
    villes.add(ville);
  }

  public static void add(Case cas) {
    cases.add(cas);
  }

  //gets

  public static Province getProvince(int province_id) {
    return provinces.getProvince(province_id);
  }

  public static Ville getVille(int ville_id) {
    return villes.getVille(ville_id);
  }

  public static Ville get(int ville_id) {
    return villes.getVille(ville_id);
  }


  //summary
  public static CasesSummary countrySummary() {
    return cases.summarizeCountry();
  }

  //max
  public static int maxCase() {
    return cases.max();
  }

  public static void prepareCaseGraphs() {
    caseGraphs.clear();
    CaseGraph initt = new CaseGraph(0, "0");
    initt.healed = 0;
    initt.dead = 0;
    initt.infected = 0;
    caseGraphs.newCaseGraph(initt);

    int cnt = 1;
    Calendar caln = Calendar.getInstance();
    Calendar cal0 = Calendar.getInstance();
    cal0.set(2020, 2, 10);
    // System.out.println("Date then === " + cal0.getTime().toString());
    //  System.out.println("Date now === " + caln.getTime().toString());
    SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd");
    String dNow = myFormatObj.format(caln.getTime());


    while (cal0.compareTo(caln) < 1) {
      String formattedDate = myFormatObj.format(cal0.getTime());
      caseGraphs.newCaseGraph(new CaseGraph(cnt++, formattedDate));
      if (formattedDate.equalsIgnoreCase(dNow)) break;
      cal0.add(Calendar.DATE, 1);
    }

    for (int i = 1; i < caseGraphs().size(); i++) {
      CaseGraph ds = caseGraphs.get(i);
      Case c = cases.getCase(ds.date);
      //System.out.println("ds: " + ds.id);
      if (c == null) {
        c = new Case(0, 0, 0, 9, ds.date, 0);
      }
      CaseGraph pr = caseGraphs.getCaseGraph(ds.id - 1);
      switch (c.type) {
        case 1:
          ds.infected = pr.infected + c.nombre;
          ds.dead = pr.dead;
          ds.healed = pr.healed;
          break;
        case 2:
          ds.infected = pr.infected;
          ds.dead = pr.dead + c.nombre;
          ds.healed = pr.healed;
          break;
        case 3:
          ds.infected = pr.infected;
          ds.dead = pr.dead;
          ds.healed = pr.healed + c.nombre;
          break;
        default:
          ds.infected = pr.infected;
          ds.dead = pr.dead;
          ds.healed = pr.healed;

      }

      // System.out.println("CaseGraphs : " + caseGraphs.size());
    }
  }


}
