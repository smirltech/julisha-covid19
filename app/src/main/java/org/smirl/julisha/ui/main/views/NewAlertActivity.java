package org.smirl.julisha.ui.main.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import org.smirl.julisha.core.Constants;
import org.smirl.julisha.core.DialogFactory;
import org.smirl.julisha.core.Utilities;
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
    private TextView tv_commune, tv_location;
    private String diagonstic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_new);

        crud = new Crud(this);

        phone = findViewById(R.id.edt_phone);

        tv_location = findViewById(R.id.tv_location);
        tv_commune = findViewById(R.id.tv_commune);
        tv_commune.setOnClickListener(v -> setCommune());

        findViewById(R.id.btn_commune).setOnClickListener(v -> setCommune());


        findViewById(R.id.btn_submit).setOnClickListener(view -> {

            if (isValidPhone(phone) && isSet(getCtx(), "Veillez reseigner votre commune", commune_id) && isSet(getCtx(), "Veillez reseigner votre commune", diagonstic)) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(Alert.COLUMN.PHONE, phone.getText().toString())
                            .put(Alert.COLUMN.ACCOUNT_TYPE, ACCOUNT_TYPE)
                            .put(Alert.COLUMN.DIAGNOSTIC, diagonstic)
                            .put(Alert.COLUMN.COMMUNE_ID, commune_id)
                            .put(Alert.COLUMN.LAT, 0)
                            .put(Alert.COLUMN.LONG, 0);

                    // Popper.print(Signup.this, obj.toString(3));

                    // dbc.signup(obj);

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

}
