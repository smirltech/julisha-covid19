package org.smirl.julisha.core;

import android.util.Log;

import org.smirl.julisha.ui.main.models.*;

import fnn.smirl.simple.Serializer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Julisha {
    public long lastUpdate = 0L;
    public Cases cases = new Cases();
    public Villes villes = new Villes();
    public Provinces provinces = new Provinces();

    public CaseGraphs villeCaseGraphs = new CaseGraphs();
    public CaseGraphs provinceCaseGraphs = new CaseGraphs();

    public CaseGraphs caseGraphs = new CaseGraphs();
    public CaseGraphs caseGraphs2 = new CaseGraphs();

    private static Julisha julisha = new Julisha();

    private Julisha() {

    }

    public static Cases cases() {
        return julisha.cases;
    }

    public static Cases cases(int province_id) {
        return julisha.cases.cases(province_id);
    }

    public static Cases casesVille(int ville_id) {
        return julisha.cases.casesVille(ville_id);
    }

    public static Cases casesProvince(int province_id) {
        return julisha.cases.casesProvince(province_id);
    }

    public static Villes villes() {
        return julisha.villes;
    }

    public static Provinces provinces() {
        return julisha.provinces;
    }

    public static CaseGraphs getVilleCaseGraphs(int ville_id) {
        prepareVilleCaseGraphs(ville_id);
        return julisha.villeCaseGraphs;
    }

    public static CaseGraphs getProvinceCaseGraphs(int province_id) {
        prepareProvinceCaseGraphs(province_id);
        return julisha.provinceCaseGraphs;
    }

    public static CaseGraphs getCaseGraphs() {
        return julisha.caseGraphs;
    }

    public static CaseGraphs getCaseGraphs2() {
        return julisha.caseGraphs2;
    }

    public static long getLastUpdate() {
        return julisha.lastUpdate;
    }

    public static void setLastUpdate(long lastUpdate) {
        julisha.lastUpdate = lastUpdate;
    }

    public static String getType(int type) {
        switch (type) {
            case 1:
                return "infectés";
            case 2:
                return "décédés";
            case 3:
                return "guéris";
            default:
                return "iconnus";
        }
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
        julisha.cases = sampleCases();
    }

    /**
     * ./End Sample data for testing purposes only
     **/
    //loads
    public static void loadProvinces(Provinces p) {
        julisha.provinces = p;
    }

    public static void loadVilles(Villes v) {
        julisha.villes = v;
    }

    public static void load(Cases c) {
        julisha.cases = c;
    }

    //loads from json
    public static void loadProvinces(String jsonString) {
        julisha.provinces = new Serializer().fromJson(jsonString, Provinces.class);
    }

    public static void loadVilles(String jsonString) {
        julisha.villes = new Serializer().fromJson(jsonString, Villes.class);
    }

    public static void load(String jsonString) {
        julisha.cases = new Serializer().fromJson(jsonString, Cases.class);
    }

    //adds

    public static void addProvince(Province province) {
        julisha.provinces.add(province);
    }

    public static void addVille(Ville ville) {
        julisha.villes.add(ville);
    }

    public static void add(Case cas) {
        julisha.cases.add(cas);
    }

    //gets

    public static Province getProvince(int province_id) {
        return julisha.provinces.getProvince(province_id);
    }

    public static Ville getVille(int ville_id) {
        return julisha.villes.getVille(ville_id);
    }

    public static Ville get(int ville_id) {
        return julisha.villes.getVille(ville_id);
    }


    //summary
    public static CasesSummary countrySummary() {
        return julisha.cases.summarizeCountry();
    }

    //max
    public static int maxCase() {
        return julisha.cases.max();
    }

    public static ArrayList<TableData> getProvincesTableData() {
        ArrayList<TableData> td = new ArrayList<>();

        HashSet<Integer> fs = cases().getProvinceIds();
        for (final int c : fs.toArray(new Integer[]{})) {
            td.add(new TableData(c, provinces().getProvince(c).nom, cases().numberP(c, 1), cases().numberP(c, 2), cases().numberP(c, 3)));
        }
        Collections.sort(td);
        return td;
    }

    public static TableData getVilleTableData(int villeid) {
        Cases cs = null;
        if (villeid < 1) return null;
        else cs = Julisha.casesVille(villeid);
        String vname = villes().getVille(villeid).nom;
       // System.out.println(vname + " >> " + cs.size());
        if (cs == null) return new TableData(villeid, vname, 0, 0, 0);
        return new TableData(villeid, vname, cs.number(villeid, 1), cs.number(villeid, 2), cs.number(villeid, 3));
    }

    public static ArrayList<TableData> getVillesTableData(int provinceid) {
        ArrayList<TableData> td = new ArrayList<>();
        Cases cs = null;
        if (provinceid < 1) cs = Julisha.cases();
        else cs = Julisha.cases(provinceid);

        HashSet<Integer> fs = cs.getVilleIds();
        for (final int c : fs.toArray(new Integer[]{})) {
            td.add(new TableData(c, villes().getVille(c).nom, cs.number(c, 1), cs.number(c, 2), cs.number(c, 3)));
        }
        Collections.sort(td);
        return td;
    }

    public static void prepareProvinceCaseGraphs(int province_id) {
        julisha.provinceCaseGraphs.clear();
        CaseGraph initt = new CaseGraph(0, "0");
        initt.healed = 0;
        initt.dead = 0;
        initt.infected = 0;
        julisha.provinceCaseGraphs.newCaseGraph(initt);

        int cnt = 1;
        Calendar caln = Calendar.getInstance();
        Calendar cal0 = Calendar.getInstance();
        cal0.set(2020, 2, 10);
        SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd");
        String dNow = myFormatObj.format(caln.getTime());


        while (cal0.compareTo(caln) < 1) {
            String formattedDate = myFormatObj.format(cal0.getTime());
            julisha.provinceCaseGraphs.newCaseGraph(new CaseGraph(cnt++, formattedDate));
            if (formattedDate.equalsIgnoreCase(dNow)) break;
            cal0.add(Calendar.DATE, 1);
        }

        //System.out.println("CaseGraphs count : " + getCaseGraphs().size());

        for (int i = 0; i < casesProvince(province_id).size(); i++) {
            Case c = casesProvince(province_id).get(i);
            CaseGraph ds = julisha.provinceCaseGraphs.getCaseGraph(c.date);
            //Case c = cases.getCase(ds.date);
            //System.out.println("ds: " + ds.id);
            // System.out.println(c.toString());

            switch (c.type) {
                case 1:
                    ds.infected += c.nombre;
                    break;
                case 2:
                    ds.dead += c.nombre;
                    break;
                case 3:
                    ds.healed += c.nombre;
                    break;
                default:
                    ds.infected += 0;
                    ds.dead += 0;
                    ds.healed += 0;

            }

        }

        for (int i = 1; i < julisha.provinceCaseGraphs.size(); i++) {
            CaseGraph ds = julisha.provinceCaseGraphs.get(i);
            CaseGraph pr = julisha.provinceCaseGraphs.get(i - 1);

            ds.infected += pr.infected;
            ds.dead += pr.dead;
            ds.healed += pr.healed;


            // System.out.println(ds.toString());
        }


    }

    public static void prepareVilleCaseGraphs(int ville_id) {
        julisha.villeCaseGraphs.clear();
        CaseGraph initt = new CaseGraph(0, "0");
        initt.healed = 0;
        initt.dead = 0;
        initt.infected = 0;
        julisha.villeCaseGraphs.newCaseGraph(initt);

        int cnt = 1;
        Calendar caln = Calendar.getInstance();
        Calendar cal0 = Calendar.getInstance();
        cal0.set(2020, 2, 10);
        SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd");
        String dNow = myFormatObj.format(caln.getTime());


        while (cal0.compareTo(caln) < 1) {
            String formattedDate = myFormatObj.format(cal0.getTime());
            julisha.villeCaseGraphs.newCaseGraph(new CaseGraph(cnt++, formattedDate));
            if (formattedDate.equalsIgnoreCase(dNow)) break;
            cal0.add(Calendar.DATE, 1);
        }

        //System.out.println("CaseGraphs count : " + getCaseGraphs().size());

        for (int i = 0; i < casesVille(ville_id).size(); i++) {
            Case c = casesVille(ville_id).get(i);
            CaseGraph ds = julisha.villeCaseGraphs.getCaseGraph(c.date);
            //Case c = cases.getCase(ds.date);
            //System.out.println("ds: " + ds.id);
            // System.out.println(c.toString());

            switch (c.type) {
                case 1:
                    ds.infected += c.nombre;
                    break;
                case 2:
                    ds.dead += c.nombre;
                    break;
                case 3:
                    ds.healed += c.nombre;
                    break;
                default:
                    ds.infected += 0;
                    ds.dead += 0;
                    ds.healed += 0;

            }

        }

        for (int i = 1; i < julisha.villeCaseGraphs.size(); i++) {
            CaseGraph ds = julisha.villeCaseGraphs.get(i);
            CaseGraph pr = julisha.villeCaseGraphs.get(i - 1);

            ds.infected += pr.infected;
            ds.dead += pr.dead;
            ds.healed += pr.healed;


            // System.out.println(ds.toString());
        }


    }

    public static void prepareCaseGraphs() {
        julisha.caseGraphs.clear();
        CaseGraph initt = new CaseGraph(0, "0");
        initt.healed = 0;
        initt.dead = 0;
        initt.infected = 0;
        julisha.caseGraphs.newCaseGraph(initt);

        int cnt = 1;
        Calendar caln = Calendar.getInstance();
        Calendar cal0 = Calendar.getInstance();
        cal0.set(2020, 2, 10);
        SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd");
        String dNow = myFormatObj.format(caln.getTime());


        while (cal0.compareTo(caln) < 1) {
            String formattedDate = myFormatObj.format(cal0.getTime());
            julisha.caseGraphs.newCaseGraph(new CaseGraph(cnt++, formattedDate));
            if (formattedDate.equalsIgnoreCase(dNow)) break;
            cal0.add(Calendar.DATE, 1);
        }

        //System.out.println("CaseGraphs count : " + getCaseGraphs().size());

        for (int i = 0; i < julisha.cases.size(); i++) {
            Case c = julisha.cases.get(i);
            CaseGraph ds = julisha.caseGraphs.getCaseGraph(c.date);
            //Case c = cases.getCase(ds.date);
            //System.out.println("ds: " + ds.id);
            // System.out.println(c.toString());

            switch (c.type) {
                case 1:
                    ds.infected += c.nombre;
                    break;
                case 2:
                    ds.dead += c.nombre;
                    break;
                case 3:
                    ds.healed += c.nombre;
                    break;
                default:
                    ds.infected += 0;
                    ds.dead += 0;
                    ds.healed += 0;

            }

        }

        for (int i = 1; i < julisha.caseGraphs.size(); i++) {
            CaseGraph ds = julisha.caseGraphs.get(i);
            CaseGraph pr = julisha.caseGraphs.get(i - 1);

            ds.infected += pr.infected;
            ds.dead += pr.dead;
            ds.healed += pr.healed;


            // System.out.println(ds.toString());
        }


    }

    public static void prepareCaseGraphs2() {

        julisha.caseGraphs2.clear();
        // CaseGraph initt = new CaseGraph(0, "0");

        //initt.infected = 0;
        //julisha.caseGraphs2.newCaseGraph(initt);

        int cnt = 1;
        Calendar caln = Calendar.getInstance();
        Calendar cal0 = Calendar.getInstance();
        cal0.set(2020, 2, 10);
        SimpleDateFormat myFormatObj = new SimpleDateFormat("yyyy-MM-dd");
        String dNow = myFormatObj.format(caln.getTime());


        while (cal0.compareTo(caln) < 1) {
            String formattedDate = myFormatObj.format(cal0.getTime());
            julisha.caseGraphs2.newCaseGraph(new CaseGraph(cnt++, formattedDate));
            if (formattedDate.equalsIgnoreCase(dNow)) break;
            cal0.add(Calendar.DATE, 1);
        }

        //Log.e("CaseGraphs","CaseGraphs: " +julisha.caseGraphs2.get(35).date);

        for (int i = 0; i < julisha.cases.size(); i++) {
            Case c = julisha.cases.get(i);
            CaseGraph ds = julisha.caseGraphs2.getCaseGraph(c.date);
          //  System.err.println("case at " + ds.date);
            if (ds == null) System.err.println("failed at " + i + " => " + c.date);
            switch (c.type) {
                case 1:
                    ds.infected += c.nombre;
                    break;
                case 2:
                    ds.dead += c.nombre;
                    break;
                case 3:
                    ds.healed += c.nombre;
                    break;
                default:
                    ds.infected = 0;
                    ds.dead = 0;
                    ds.healed = 0;
            }
        }
    }


    public static void save(File file) {
        new Serializer().serialize(file.getAbsolutePath(), julisha, Julisha.class);
    }

    public static void read(File file) {
        julisha = new Serializer().deserialize(file.getAbsolutePath(), Julisha.class);
    }
}
