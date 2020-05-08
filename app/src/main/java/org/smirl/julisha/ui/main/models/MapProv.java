package org.smirl.julisha.ui.main.models;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.Build;

import java.io.IOException;

import static org.smirl.julisha.core.Constants.*;

public class MapProv {
    private int id;
    private int value, max;
    private int tintColor;
    private Bitmap bmp;
    private boolean showText = false;
    private int lx = 0, ly = 0;
    private String name;

    public MapProv(AssetManager assetManager, int id, String name, int value, int max) throws IOException {
        this.id = id;
        this.name = name;
        this.value = value;
        this.max = max;
        int alfa = value;

        int tt = RISK_THRESHOLD_LEVEL;
        //if (value > 0 && (alfa + tt) < 255) alfa += tt;
        if (value > 0) alfa += tt;

        if (alfa > 255) alfa = 255;

        this.tintColor = Color.argb(alfa, 255, 0, 0);

        bmp = BitmapFactory.decodeStream(assetManager.open("rdc/map/prov/" + id + ".png"));
        bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);

    }

    public void draw(float scaleDensity, Canvas canvas, Paint paint, int parentW, int parentH) {
        bmp = Bitmap.createScaledBitmap(bmp, parentW, parentH, true);
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, 0, 0, paint);
        if (showText) {
            paint.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
            paint.setTextSize(10*scaleDensity);
            int tl = (int) paint.measureText(name.toUpperCase());
            canvas.drawRoundRect(lx - 5, ly - 105, lx + tl + 5, ly + 15, 10, 10, paint);
            paint.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN));

            paint.setColor(Color.BLACK);
            canvas.drawText(name.toUpperCase(), lx, ly - 50, paint);
            int tu = (int) paint.measureText("" + value);
            canvas.drawText("" + value, lx + ((tl - tu) / 2), ly + 5, paint);
            // canvas.drawRect(lx, ly, lx+300, ly+300, paint);
            System.out.println(id + " has been touched at " + lx + " ; " + ly);
        }
    }
    public boolean isTouched(int x, int y) {
        float k = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Color cf = bmp.getColor(x, y);
            k = cf.alpha();

        } else {
            k = Color.alpha(bmp.getPixel(x, y));

        }
        if (k > 0) {
            lx = x;
            ly = y;
            showText = !showText;

        } else {
            showText = false;

        }


        return k > 0;
    }
}
