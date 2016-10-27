package com.skymxc.lesson_36_view_override;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by sky-mxc
 */
public class CircleImageView extends ImageView {
    private static final String TAG = "CircleImageView";

    private int outWidth = 2;
    private int outColor = Color.RED;

    private Paint paint;

    public CircleImageView(Context context) {
        this(context,null,0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        canvas.drawColor(Color.YELLOW);
        paint.setColor(outColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,paint);
        drawShader(canvas);



//       Bitmap bmp=  getCircleBitmap();
//        canvas.drawBitmap(bmp,0,0,paint);

    }

    private void drawShape() {
        ////        paint.setARGB(100,240,0,200);
//        paint.setAlpha(50);
//        //画笔样式  ；填充：Style.FILL ;边框Style.STROKE;填充+边框： Style.FILL_AND_STROKE
//        paint.setStyle(Paint.Style.STROKE);
//        //设置边框笔头样式;圆形=Cap.ROUND;矩形=Cap.SQUARE
//        paint.setStrokeCap(Paint.Cap.SQUARE);
//        //设置过度时边框样式 平板=BEVEL;圆=ROUND;直角=MITER
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        //笔宽的 10px
//        paint.setStrokeWidth(5);
////        paint.setTextAlign(Paint.Align.CENTER);
////        paint.setTextSize(20);
//        //交叉模式
////        paint.setXfermode();
//
//        Rect rect = new Rect(10,10,110,110);
//        //矩形 圆形
//        canvas.drawRect(rect,paint);
//        canvas.drawCircle(60,60,45,paint);
//        RectF rectF= new RectF(10,150,210,250);
//        //矩形
//        paint.setStrokeWidth(1);
//        paint.setColor(Color.RED);
//        canvas.drawRect(rectF,paint);
//        //扇形
//        paint.setStrokeWidth(2);
//        paint.setColor(Color.GRAY);
//        canvas.drawArc(rectF,0,45,false,paint);
//
//        //线
//        paint.setColor(Color.GREEN);
//        //连接矩形的左上和右下
//        canvas.drawLine(10,150,210,250,paint);
//
//        //通过点连线的方式 绘制三角
//        paint.setColor(Color.BLUE);
//        //必须两个点两个点的来
//        float[] points = {10,300,200,300,200,300,200,500,10,300,200,500};
//        canvas.drawLines(points,paint);
//
//        //中间线
//        paint.setColor(Color.YELLOW);
//        canvas.drawLine(250,0,250,700,paint);
//
//        //椭圆
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.FILL);
//        rectF.set(10,550,110,650);
//        canvas.drawOval(rectF,paint);
//
//        //路径 矩形
//        paint.setColor(Color.MAGENTA);
//        Path path = new Path();
//        path.moveTo(200,60);
//        path.lineTo(250,10);
//        path.lineTo(300,60);
//        path.lineTo(250,110);
//        path.lineTo(200,60);
//        canvas.drawPath(path,paint);
//
//        paint.setColor(Color.CYAN);
//        paint.setTextSize(80);
//        paint.setStyle(Paint.Style.STROKE);
//        //绘制文字
//        canvas.drawText("MXC",300,200,paint);
//
//        //Bitmap
//        paint.setColor(Color.LTGRAY);
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        canvas.drawBitmap(bmp,320,10,paint);    //原始尺寸大小
//
//        Rect  src = new Rect(0,0,bmp.getWidth(),bmp.getHeight()/2); //绘制 bmo的上半部分
//        Rect dst = new Rect(370,10,bmp.getWidth()+370,10+bmp.getHeight()/2);
//        canvas.drawBitmap(bmp,src,dst,paint);
    }

    private Bitmap getCircleBitmap(){
        Drawable mDrawable = getDrawable();
        if (mDrawable == null) return null;
        if (mDrawable instanceof BitmapDrawable){
            Bitmap bmp = ((BitmapDrawable)mDrawable).getBitmap();
            if (bmp == null) return null;
            paint  = new Paint();
            paint.setAntiAlias(true);
            //创建空的位图
            Bitmap output = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            //创建画板，以位图进行创建
            Canvas canvas= new Canvas(output);
            //Bitmap就成了 透明的图片
            canvas.drawColor(Color.TRANSPARENT);

            //画一个圆形 和图像大小一致
            paint.setColor(Color.WHITE);
            canvas.drawCircle(output.getWidth()/2,output.getHeight()/2,output.getWidth()/2,paint);

//            //paint 相交模式 必须在 两者中间定义  显示交叉的地方  ;前面是 dst;后面是 src
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//
//            //绘制 Bitmap
            Rect src = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
            RectF dst = new RectF(0,0,output.getWidth(),output.getHeight());
            canvas.drawBitmap(bmp,src,dst,paint);
            return  output;


        }

        return  null;
    }

    private void drawShader(Canvas canvas) {
        Drawable mDrawable = getDrawable();
        if (mDrawable == null) return;
        if (mDrawable instanceof BitmapDrawable){
            Bitmap bmp = ((BitmapDrawable)mDrawable).getBitmap();
            if (bmp == null) return;
            //图片缩放，参数2 目标宽度，参数3目标高度，参数4 是否过滤
            bmp = Bitmap.createScaledBitmap(bmp,getWidth(),getHeight(),true);
            //着色器
            Shader shader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint = new Paint();
            paint.setShader(shader);
            canvas.drawCircle(getWidth()/2,getWidth()/2,(getWidth()-outWidth*2)/2,paint);
        }
    }

    public void initAttrs(AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(attrs,R.styleable.CircleImageView);
        int len =  array.length();
        for(int i=0;i<len;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.CircleImageView_out_color:
                    this.outColor = array.getColor(attr,Color.GREEN);
                    break;
                case R.styleable.CircleImageView_out_width:
                    this.outWidth = (int) array.getDimension(attr,2);
                    Log.i(TAG, "initAttrs: outWidth="+this.outWidth);
                    break;
            }
        }
        array.recycle();
    }


}
