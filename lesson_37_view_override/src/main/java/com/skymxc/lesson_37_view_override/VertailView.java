package com.skymxc.lesson_37_view_override;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sky-mxc
 */
public class VertailView extends ViewGroup {
    private static final String TAG = "VertailView";

    private int measureSelfWidth;
    private int measureSelfHeight;

    public VertailView(Context context) {
        this(context,null);
    }

    public VertailView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VertailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 布局
     * @param changed 是否发生改变
     * @param l 当前控件的l
     * @param t 当前控件的t
     * @param r 当前控件的r
     * @param b 当前控件的b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftP = getPaddingLeft();
        int topP = getPaddingTop();
        for (int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            int w = child.getMeasuredWidth();
            int h = child.getMeasuredHeight();
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int ml = lp.leftMargin;
            int mt = lp.topMargin;
            int mr = lp.rightMargin;
            int mb = lp.bottomMargin;
            topP+=mt;
            //设定子控件的位置
            child.layout(leftP+ml,topP,leftP+ml+w,topP+h);
            topP+=h+mb;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: widthMeasureSpec="+widthMeasureSpec+";heightMeasureSpec="+heightMeasureSpec);
        //获取控件宽高的显示模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //宽高默认为建议的最小宽渡和高度
        int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        measureSelf(widthMeasureSpec, heightMeasureSpec);


        if(widthMode == MeasureSpec.AT_MOST){ //包裹的情况下
            width=measureSelfWidth;
        }
        if (heightMode == MeasureSpec.AT_MOST){  //包裹的情况
            height= measureSelfHeight;
        }

        //设置自身的测量宽高
        setMeasuredDimension(width,height);
    }

    private void measureSelf(int widthMeasureSpec, int heightMeasureSpec) {
        //测量所有子控件的尺寸
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        measureSelfWidth=0;
        measureSelfHeight=0;

        for (int i=0;i<getChildCount();i++){
            //获取到子控件的尺寸及外边距
            View child = getChildAt(i);
           LayoutParams lp= (LayoutParams) child.getLayoutParams();
            int ml = lp.leftMargin;
            int mt = lp.topMargin;
            int mr= lp.rightMargin;
            int mb = lp.bottomMargin;
            int w = child.getMeasuredWidth()+ml+mr;
            int h = child.getMeasuredHeight()+mt+mb;
            measureSelfHeight+=h;
           measureSelfWidth= Math.max(measureSelfWidth,w);
        }
        measureSelfHeight+=getPaddingBottom()+getPaddingTop();
        measureSelfWidth+=getPaddingLeft()+getPaddingRight();
    }

    //=======================重写三个方法 让控件支持 margin

    /**
     * xml设置 LayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    /**
     * 根据一个LayoutParams创键；
     * 代码设置LayoutParams
     * @param p
     * @return
     */
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /**
     * 默认；未给LayoutParams
     * @return
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {

        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }



    public class LayoutParams  extends  MarginLayoutParams{

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
