package org.smirl.julisha.ui.main.views;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import org.smirl.julisha.core.Julisha;
import org.smirl.julisha.ui.main.models.MapProv;
import org.smirl.julisha.ui.main.models.TableData;

import java.io.IOException;
import java.util.ArrayList;

import static org.smirl.julisha.core.Constants.*;

public class MapMe extends View {

    private Paint paint;

    AssetManager assetManager;

    ArrayList<MapProv> mapProvs = new ArrayList<>();

    Bitmap drc_base;

    public MapMe(Context context, AttributeSet attrs) {
        super(context, attrs);
        assetManager = context.getAssets();


        for (TableData td : Julisha.getProvincesTableData()) {
            try {
                // mapProvs.add(new MapProv(assetManager, td.id, td.infected, Julisha.maxCase()));
                mapProvs.add(new MapProv(assetManager, td.id, (td.infected - td.healed - td.dead), MAX_RISK_LEVEL));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            drc_base = BitmapFactory.decodeStream(assetManager.open("rdc/map/drc_base.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        paint = new Paint();

        drc_base = Bitmap.createScaledBitmap(drc_base, getWidth(), getHeight(), true);
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(drc_base, 0, 0, paint);

        for (int v = 0; v < mapProvs.size(); v++) {
            mapProvs.get(v).draw(canvas, paint, getWidth(), getHeight());
        }

        paint.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
    }

}
