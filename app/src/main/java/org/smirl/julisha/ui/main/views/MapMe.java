package org.smirl.julisha.ui.main.views;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
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

    private int WIDTH = 200, HEIGHT = 200;

    Bitmap drc_base;

    public MapMe(Context context, AttributeSet attrs) {
        super(context, attrs);
        assetManager = context.getAssets();

        for (TableData td : Julisha.getProvincesTableData()) {
            try {
                //// mapProvs.add(new MapProv(assetManager, td.id, td.infected, Julisha.maxCase()));
                mapProvs.add(new MapProv(assetManager, td.id, td.name, (td.infected - td.healed - td.dead), MAX_RISK_LEVEL));
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
        //   System.out.println("========width: " + w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        paint = new Paint();
        paint.setAntiAlias(true);
        float scaleDensity = getResources().getDisplayMetrics().scaledDensity;

        drc_base = Bitmap.createScaledBitmap(drc_base, WIDTH, HEIGHT, true);
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(drc_base, 0, 0, paint);

        for (int v = 0; v < mapProvs.size(); v++) {
            mapProvs.get(v).draw(scaleDensity, canvas, paint, WIDTH, HEIGHT);
        }

        paint.setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // System.out.println("touched : " +event.getX() + " ; " + event.getY());
        int x = (int)event.getX();
        int y = (int)event.getY();

        for (int v = 0; v < mapProvs.size(); v++) {
            mapProvs.get(v).isTouched(x, y);
            invalidate();
        }

        return super.onTouchEvent(event);

    }
}
