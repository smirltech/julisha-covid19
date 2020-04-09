package org.smirl.julisha.core.data.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import fnn.smirl.simple.Serializer;
import org.smirl.julisha.core.DialogFactory;

import org.smirl.julisha.core.data.dao.Crud;

import org.smirl.julisha.core.data.rdc.*;

import static org.smirl.julisha.core.data.rdc.RDC.*;

import org.smirl.julisha.R;

public class LocationPicker {
    static Provinces provinces = null;
    static Villes villes = null;
    static Communes communes = null;
    static View signup_prog = null;
    static Crud dbm;

    public static final int VALUE_PROVINCE = 1;
    public static final int VALUE_VILLE = 2;
    public static final int VALUE_COMMUNE = 3;
    static int VALUE;


    static int data_id = 0;
    static String data_nom = "";


    private static Button valider;
    private static View province_bloc;
    private static View ville_bloc;
    private static View commune_bloc;

    private static TextView tv_title;


    public static void pickLocation(Context ctx, int value, final OnDataSelection onDataSelection) {
        dbm = new Crud(ctx);
        dbm.setShowDialog(false);

        VALUE = value;

        View alert_view = LayoutInflater.from(ctx).inflate(R.layout.picker_location, null);

        Spinner province = alert_view.findViewById(R.id.picker_provinces);
        Spinner commune = alert_view.findViewById(R.id.picker_commune);
        Spinner ville = alert_view.findViewById(R.id.picker_villes);

        province_bloc = alert_view.findViewById(R.id.province_bloc);
        ville_bloc = alert_view.findViewById(R.id.ville_bloc);
        commune_bloc = alert_view.findViewById(R.id.commune_bloc);

        province_bloc.setVisibility(View.GONE);
        ville_bloc.setVisibility(View.GONE);
        commune_bloc.setVisibility(View.GONE);

        signup_prog = alert_view.findViewById(R.id.picker_prog);

        tv_title = alert_view.findViewById(R.id.tv_title);


        final AlertDialog loc = new AlertDialog.Builder(ctx).create();
        loc.setView(alert_view);
        loc.setCancelable(true);
        loc.setCanceledOnTouchOutside(true);
        loc.show();

        valider = alert_view.findViewById(R.id.btn_submit);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDataSelection != null)
                    onDataSelection.onDataSet(data_id, data_nom);
                loc.dismiss();
            }
        });
        loadProvinces(ctx, province, ville, commune);


    }

    private static void loadProvinces(final Context context, final Spinner province, final Spinner ville, final Spinner commune) {

        dbm.get(PROVINCE_URL, new Crud.OnResponseListener() {
            @Override
            public void onResponse(String response, int code) {
                signup_prog.setVisibility(View.INVISIBLE);

                try {
                    provinces = new Serializer().fromJson(response, Provinces.class);
                    province.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, provinces));
                    province_bloc.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    DialogFactory.printError(context, response);
                }
            }

            @Override
            public void onError(String error, int code) {
                DialogFactory.printError(context, error);

            }
        });


        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (VALUE == VALUE_PROVINCE) {
                    tv_title.setText("CHOISIR PROVINCE");

                    data_id = provinces.get(i).id;
                    data_nom = provinces.get(i).nom;
                    valider.setVisibility(View.VISIBLE);
                } else {
                    loadVilles(context, provinces.get(i).id, ville, commune);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private static void loadVilles(final Context context, int prov_id, final Spinner ville, final Spinner commune) {
        String ville_url = VILLE_URL + "?provinceid=" + prov_id;

        dbm.get(ville_url, new Crud.OnResponseListener() {
            @Override
            public void onResponse(String response, int code) {
                signup_prog.setVisibility(View.INVISIBLE);

                try {
                    villes = new Serializer().fromJson(response, Villes.class);
                    ville.setAdapter(new ArrayAdapter<Ville>(context, android.R.layout.simple_list_item_1, villes));
                    ville_bloc.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    DialogFactory.printError(context, response);
                }

            }

            @Override
            public void onError(String error, int code) {

            }
        });


        ville.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (VALUE == VALUE_VILLE) {
                    tv_title.setText("CHOISIR VILLE");

                    data_id = villes.get(i).id;
                    data_nom = villes.get(i).nom;
                    valider.setVisibility(View.VISIBLE);
                } else {
                    loadCommunes(context, villes.get(i).id, commune);
                    signup_prog.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private static void loadCommunes(final Context context, int ville_id, final Spinner commune) {
        String commune_url = COMMUNE_URL + "?villeid=" + ville_id;

        dbm.get(commune_url, new Crud.OnResponseListener() {
            @Override
            public void onResponse(String response, int code) {
                signup_prog.setVisibility(View.INVISIBLE);

                try {
                    communes = new Serializer().fromJson(response, Communes.class);
                    commune.setAdapter(new ArrayAdapter<Commune>(context, android.R.layout.simple_list_item_1, communes));
                    commune_bloc.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    DialogFactory.printError(context, response);
                }
            }

            @Override
            public void onError(String error, int code) {

            }
        });

        commune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (VALUE == VALUE_COMMUNE) {
                    tv_title.setText("CHOISIR COMMUNE");
                    signup_prog.setVisibility(View.VISIBLE);

                    data_id = communes.get(i).id;
                    data_nom = communes.get(i).nom;
                    valider.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
