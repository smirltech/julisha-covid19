package org.smirl.julisha.core;

import org.smirl.julisha.ui.main.models.*;

import fnn.smirl.simple.Serializer;

public class Julisha {
    private static Cases cases = new Cases();
    private static Villes villes = new Villes();
    private static Provinces provinces = new Provinces();

    private Julisha(){}

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

    /** Sample data for testing purposes only **/
    public static Cases sampleCases(){
        Cases cc = new Cases();
        cc.add(new Case(1, 1, 1, 1, "2020-02-21", 5));
        cc.add(new Case(2, 1, 1, 2, "2020-02-21", 1));
        cc.add(new Case(3, 1, 1, 3, "2020-02-21", 3));
        cc.add(new Case(4, 3, 2, 1, "2020-02-22", 3));
        return cc;
    }

    public static void generateSampleCases() {
        cases = sampleCases();
    }
    /** ./End Sample data for testing purposes only **/
    //loads

    public static void loadProvinces(Provinces p){
        provinces = p;
    }

    public static void loadVilles(Villes v){
        villes = v;
    }

    public static void load(Cases c){
        cases = c;
    }

    //loads from json
    public static void loadProvinces(String jsonString){
        provinces = new Serializer().fromJson(jsonString, Provinces.class);
    }

    public static void loadVilles(String jsonString){
        villes = new Serializer().fromJson(jsonString, Villes.class);
    }

    public static void load(String jsonString){
       cases = new Serializer().fromJson(jsonString, Cases.class);
    }

    //adds

    public static void addProvince(Province province){
        provinces.add(province);
    }

    public static void addVille(Ville ville){
        villes.add(ville);
    }

    public static void add(Case cas){
        cases.add(cas);
    }

    //gets

    public static Province getProvince(int province_id){
       return provinces.getProvince(province_id);
    }

    public static Ville getVille(int ville_id){
        return villes.getVille(ville_id);
    }

    public static Ville get(int ville_id){
        return villes.getVille(ville_id);
    }


    //summary
    public static CasesSummary countrySummary(){
        return cases.summarizeCountry();
    }
}
