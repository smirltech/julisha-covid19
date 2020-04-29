package org.smirl.julisha.core;

import android.content.Context;

import org.smirl.julisha.core.data.dao.Crud;

import java.io.File;

public class DataUpdater implements Constants {

    public static void populateCases(Context context, final UpdaterListener listener) {


        // Utilities.toastIt(context, "start update...!");

        new Crud(context).get(URL_API, new Crud.OnResponseListener() {
            @Override
            public void onResponse(String response, int code) {
                response = response.replace("null", "1");
                Utilities.toastIt(context, "Mise à jour de données effectuée");
                Julisha.load(response);
                Julisha.setLastUpdate(System.currentTimeMillis());


                try {
                    Julisha.prepareCaseGraphs();
                    Julisha.prepareCaseGraphs2();
                } catch (Exception e) {
                    e.printStackTrace();
                    //DialogFactory.print(context,e.getMessage());
                }

                File baseFolder = FileManager.getBaseDir(context, "julisha");
                if (!baseFolder.exists()) baseFolder.mkdirs();
                File dataFile = new File(baseFolder, "data.json");
                Julisha.save(dataFile);

                if (listener != null) listener.onCompleted();
            }

            @Override
            public void onErrorResponse(String error, int code) {
                Utilities.toastIt(context, "Échec de mise à jour, chargement de données enregistrées!");
                File baseFolder = FileManager.getBaseDir(context, "julisha");
                if (!baseFolder.exists()) baseFolder.mkdirs();
                File dataFile = new File(baseFolder, "data.json");
                Julisha.read(dataFile);

                if (listener != null) listener.onFailed();
            }
        });

    }

    public static void populateLocalCases(Context context, final UpdaterListener listener) {
        File baseFolder = FileManager.getBaseDir(context, "julisha");
        if (!baseFolder.exists()) baseFolder.mkdirs();
        File dataFile = new File(baseFolder, "data.json");
        Julisha.read(dataFile);

        if (listener != null) listener.onCompleted();
    }

    public interface UpdaterListener {
        void onCompleted();

        void onFailed();
    }
}
