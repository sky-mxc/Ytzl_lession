package com.skymxc.lesson_37_view_override;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by sky-mxc
 */
public class GGCard extends ImageView {
    private static final String TAG = "GGCard";

    private Bitmap overBmp;
    private Canvas overCanvas;
    private Paint paint;
    private Path path;

    public GGCard(Context context) {
        this(context,null);
    }

    public GGCard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GGCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(40);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged: w="+w+";h="+h);
        init(w,h);

    }

    /**
     * 初始化
     */
    private void init(int w,int h) {
        if (w ==0 || h == 0 ) return;
        overBmp = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        overCanvas = new Canvas(overBmp);
        overCanvas.drawColor(Color.GRAY);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(overBmp,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();   //重置
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                overCanvas.drawPath(path,paint);
                //废弃；重新更新显示
                invalidate();
                break;
        }
        return true;
    }
}
