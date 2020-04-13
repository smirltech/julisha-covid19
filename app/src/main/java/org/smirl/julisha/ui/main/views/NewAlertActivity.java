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
import static org.smirl.julisha.ui.main.models.Alert.COLUMN.ACCOUNT_TYPE;

public class NewAlertActivity extends AppCompatActivity {


    private EditText phone;

    private Crud crud;


    private int commune_id;
    private TextView tv_commune, tv_smt;
    private String symptomes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_new);

        crud = new Crud(this);

        phone = findViewById(R.id.edt_phone);
        tv_smt = findViewById(R.id.tv_smt);
        tv_commune = findViewById(R.id.tv_commune);

        tv_commune.setOnClickListener(v -> setCommune());
        tv_smt.setOnClickListener(v -> setSymptomes());

        Toast.makeText(getCtx(), "En cours d'implementation !", Toast.LENGTH_SHORT).show();


        findViewById(R.id.btn_commune).setOnClickListener(v -> setCommune());
        findViewById(R.id.btn_smt).setOnClickListener(v -> setSymptomes());


        findViewById(R.id.btn_submit).setOnClickListener(view -> {

        //    Toast.makeText(getCtx(), "Encours d'implementation !", Toast.LENGTH_SHORT).show();
            if (isValidPhone(phone) && isSet(getCtx(), "Veillez reseigner votre commune", commune_id) && isSet(getCtx(), "Veillez reseigner votre les symptomes", symptomes)) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(Alert.COLUMN.PHONE, phone.getText().toString())
                            .put(Alert.COLUMN.ACCOUNT_TYPE, ACCOUNT_TYPE)
                            .put(Alert.COLUMN.SYMPTOMES, symptomes)
                            .put(Alert.COLUMN.COMMUNE_ID, commune_id)
                            .put(Alert.COLUMN.LAT, 0)
                            .put(Alert.COLUMN.LONG, 0);

                    // Popper.print(Signup.this, obj.toString(3));

                    //dbc.signup(obj);

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
                tv_smt.setText(getSymptomes(smt_1, smt_2, smt_3, smt_4, smt_5, smt_6, smt_7, smt_8, smt_9, smt_10));
                dialog.dismiss();
            }
        });


    }

    private String getSymptomes(CheckBox... checkBoxes) {
        String s = "";

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                s += "- " + checkBox.getText().toString() + "\n";
            }
        }

        return s;
    }

}
