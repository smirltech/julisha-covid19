package org.smirl.julisha.ui.main.models;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.io.IOException;

import static org.smirl.julisha.core.Constants.*;

public class MapProv {
    private int id;
    private int value, max;
    private int tintColor;
    private Bitmap bmp;

    public MapProv(AssetManager assetManager, int id, int value, int max) throws IOException {
        this.id = id;
        this.value = value;
        this.max = max;
        int alfa = value;
        // int alfa = (int) ((value * 255) / max);
        int tt = RISK_THRESHOLD_LEVEL;
        //if (value > 0 && (alfa + tt) < 255) alfa += tt;
        if (value > 0) alfa += tt;

        if (alfa > 255) alfa = 255;

        this.tintColor = Color.argb(alfa, 255, 0, 0);

        bmp = BitmapFactory.decodeStream(assetManager.open("rdc/map/prov/" + id + ".png"));
    }

    public void draw(Canvas canvas, Paint paint, int parentW, int parentH) {
        bmp = Bitmap.createScaledBitmap(bmp, parentW, parentH, true);
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, 0, 0, paint);
    }
}
