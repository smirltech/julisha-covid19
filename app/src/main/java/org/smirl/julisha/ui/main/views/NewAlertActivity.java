package org.smirl.julisha.ui.main.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import org.smirl.julisha.core.DialogFactory;
import org.smirl.julisha.core.data.dao.Crud;
import org.smirl.julisha.core.data.location.LocationPicker;
import org.smirl.julisha.ui.main.models.Alert;
import org.smirl.julisha.R;

import static org.smirl.julisha.core.InputValidator.*;

public class NewAlertActivity extends AppCompatActivity {


    private EditText phone, user;

    private Crud crud;


    private int commune_id;
    private TextView tv_commune, tv_smt;
    private  String symptomes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_new);

        crud = new Crud(this);

        phone = findViewById(R.id.edt_phone);
        user = findViewById(R.id.edt_user);
        tv_smt = findViewById(R.id.tv_smt);
        tv_commune = findViewById(R.id.tv_commune);

        tv_commune.setOnClickListener(v -> setCommune());
        tv_smt.setOnClickListener(v -> setSymptomes());

       // Toast.makeText(getCtx(), "Completement operatonnel !", Toast.LENGTH_SHORT).show();


        findViewById(R.id.btn_commune).setOnClickListener(v -> setCommune());
        findViewById(R.id.btn_smt).setOnClickListener(v -> setSymptomes());


        findViewById(R.id.btn_submit).setOnClickListener(view -> {

            //    Toast.makeText(getCtx(), "Encours d'implementation !", Toast.LENGTH_SHORT).show();
            if (isValidName(user) && isValidPhone(phone) && isSet(getCtx(), "Veillez reseigner la commune du cas", commune_id) && isSet(getCtx(), "Veillez renseigner les symptomes du cas", symptomes)) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(Alert.COLUMN.PHONE, phone.getText().toString())
                            .put(Alert.COLUMN.USER, user.getText().toString())
                            .put(Alert.COLUMN.SYMPTOMES, symptomes)
                            .put(Alert.COLUMN.COMMUNE_ID, commune_id);

                    // Popper.print(Signup.this, obj.toString(3));

                    crud.setShowProgressDialog(true);
                    crud.put(Alert.TABLE_NAME, obj, new Crud.OnResponseListener() {
                        @Override
                        public void onResponse(String response, int code) {
                            Toast.makeText(getCtx(), "Your informations has been submitted!", Toast.LENGTH_SHORT).show();
                            finish();
                            // DialogFactory.print(getCtx(),response);
                        }

                        @Override
                        public void onErrorResponse(String error, int code) {
                            Toast.makeText(getCtx(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                        }
                    });

                } catch (JSONException e) {
                    DialogFactory.printError(getCtx(), e.toString());
                }

            }

        });


    }

    private Context getCtx() {
        return this;
    }


    private void setCommune() {

        //  Toast.makeText(this, Constants.RDC_URL, Toast.LENGTH_SHORT).show();

        LocationPicker.pickLocation(getCtx(), LocationPicker.VALUE_COMMUNE, (dataId, dataName) -> {
            commune_id = dataId;
            tv_commune.setText(dataName.toUpperCase());
        });

    }


    private void setSymptomes() {

        //  Toast.makeText(this, Constants.RDC_URL, Toast.LENGTH_SHORT).show();


        CheckBox smt_1, smt_2, smt_3, smt_4, smt_5, smt_6, smt_7, smt_8, smt_9, smt_10;
        View view = getLayoutInflater().inflate(R.layout.dialog_smt, null);


        smt_1 = view.findViewById(R.id.smt_1);
        smt_2 = view.findViewById(R.id.smt_2);
        smt_3 = view.findViewById(R.id.smt_3);
        smt_4 = view.findViewById(R.id.smt_4);
        smt_5 = view.findViewById(R.id.smt_5);
        smt_6 = view.findViewById(R.id.smt_6);
        smt_7 = view.findViewById(R.id.smt_7);
        smt_8 = view.findViewById(R.id.smt_8);
        smt_9 = view.findViewById(R.id.smt_9);
        smt_10 = view.findViewById(R.id.smt_10);


        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button valider = view.findViewById(R.id.btn_submit);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getSymptomes(smt_1, smt_2, smt_3, smt_4, smt_5, smt_6, smt_7, smt_8, smt_9, smt_10);
                if (!s.isEmpty())
                    tv_smt.setText(symptomes);
                dialog.dismiss();
            }
        });


    }

    private String getSymptomes(CheckBox... checkBoxes) {


        symptomes ="";

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                symptomes += "- " + checkBox.getText().toString() + "\n";
            }
        }
        return symptomes;
    }

}
