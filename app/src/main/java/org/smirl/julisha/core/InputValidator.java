package org.smirl.julisha.core;

import android.content.Context;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class InputValidator {
    // validating email id
    public static boolean isValidPhone(EditText edt, String error) {
        if (edt != null) {
            String phone = edt.getText().toString();
            if (phone.trim().length() == 10) {
                return true;
            } else {
                edt.setError(error);
                return false;
            }
        }
        return false;
    }

    public static boolean isValidPhone(EditText edt) {
        if (edt != null) {
            String phone = edt.getText().toString();
            if (phone.trim().length() == 10) {
                return true;
            } else {
                edt.setError("Numero invalid (10 chiffres)");
                return false;
            }
        }
        return false;
    }

    // validating password with retype password
    public static boolean isValidPassword(EditText pswd1, EditText pswd2, String error) {
        if (pswd1 != null & pswd2 != null) {
            String pass1 = pswd1.getText().toString();
            String pass2 = pswd2.getText().toString();


            if (pass1.trim().length() > 0 && pass1.equals(pass2)) {
                return true;
            } else {
                pswd2.setError(error);
                return false;
            }
        }
        return false;
    }

    public static boolean isValidPassword(EditText pswd1, EditText pswd2) {
        if (pswd1 != null & pswd2 != null) {
            String pass1 = pswd1.getText().toString();
            String pass2 = pswd2.getText().toString();


            if (pass1.trim().length() > 0 && pass1.equals(pass2)) {
                return true;
            } else {
                pswd2.setError("Mots de pas non indentiques");
                return false;
            }
        }
        return false;
    }

    public static boolean isValidName(EditText edt, String error) {
        return isValidEdt(error, 3, edt);
    }

    public static boolean isValidName(EditText edt) {
        return isValidEdt("Nom d'utilisateur invalid", 3, edt);
    }


    public static boolean isValidEdt(String error, int superTo, EditText... edts) {
        for (EditText edt : edts)
            if (edt != null) {
                String str = edt.getText().toString();
                if (str.trim().length() < superTo) {
                    edt.setError(error);
                    return false;
                }
            }
        return true;
    }

    public static boolean isValidProgress(Context ctx, String error, RatingBar ratingBar) {
        if (ratingBar != null && ratingBar.getProgress() > 0) {

            return true;
        } else {
            Toast.makeText(ctx, error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean isSet(Context ctx, String msg, int... values) {
        if (values == null || values.length == 0) return false;
        try {
            for (int v : values)
                if (v <= 0) {
                    Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
                    return false;
                }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    public static boolean isSet(Context ctx, String msg, String... values) {


        try {
            for (String v : values)
                if (v.trim().length() <= 0) {
                    return false;
                }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
}
