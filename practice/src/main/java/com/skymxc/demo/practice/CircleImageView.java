package com.skymxc.demo.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by sky-mxc
 */
public class CircleImageView extends ImageView {

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取到drawable资源
        Drawable drawable = getDrawable();

        Log.e("Tag","=======onDraw======drawable:"+drawable);
        //没有src就返回
        if (drawable == null){
            return ;
        }
        //没有初始化就停
        if (getWidth()==0|| getHeight() == 0){
            return ;
        }
        //必须是Bitmap
        if (!( drawable instanceof BitmapDrawable)){
            return;
        }
        //获取到位图
        Bitmap b =((BitmapDrawable) drawable).getBitmap();

        if (b == null){
            return;
        }
        //TODO copy 未解
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888,true);

        //宽度
        int w = getWidth();

        Bitmap roundBitmap = getCroppedBitmap(bitmap,w);

        canvas.drawBitmap(roundBitmap,0,0,null);
    }

    /**
     * 初始 Bitmap对象的缩放裁剪过程
     * @param bmp 位图
     * @param radius 圆形图片直径大小
     * @return 返回一个圆形的缩放裁剪过后的Bitmap 对象
     */
    public static Bitmap getCroppedBitmap(Bitmap bmp,int radius){
        Bitmap sbmp;
        //比较初始Bitmap宽高和给定的圆形直径，判断是否需要缩放裁剪Bitmap对象
        if (bmp.getWidth() != radius || bmp.getHeight() != radius){
            //TODO createScaledBitmap这个方法是干啥的
            sbmp= Bitmap.createScaledBitmap(bmp,radius,radius,false);
        }else{
            sbmp=bmp;
        }

        //根据 宽度高度 创建一个Bitmap 32位颜色
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),sbmp.getHeight(), Bitmap.Config.ARGB_8888);

        //画布 将Bitmap放入画布
        Canvas canvas = new Canvas(output);

        //画笔
        final Paint paint = new Paint();

        //矩形 矩形 和传入的Bitmap一样的宽高
        final Rect rect = new Rect(0,0,sbmp.getWidth(),sbmp.getHeight());

        //这一系列方法是干啥的啊
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        //这应该是 颜色吧 透明吗？ 画布的背景色吧
        canvas.drawARGB(0,0,0,0);
        //画笔颜色
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth()/2+0.7f,sbmp.getHeight()/2+0.7f,sbmp.getWidth()/2+0.1f,paint);
        //设置两张图片的相交模式 ，在这里就是上面绘制的Circle和下面绘制的Bitmap
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp,rect,rect,paint);
        return output;
    }
}
