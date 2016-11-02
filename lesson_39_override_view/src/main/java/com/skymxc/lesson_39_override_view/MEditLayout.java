package com.skymxc.lesson_39_override_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sky-mxc
 */
public class MEditLayout extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "MEditLayout";

    private static  final int TEXT =3;
    private static  final int NUMBER =1;
    private static  final int PASSWORD =2;

    private TextInputLayout inputLayout;
    private ImageView btClear;

    public MEditLayout(Context context) {
        this(context,null);
    }

    public MEditLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MEditLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs ){

        initThis();
        //加载布局后查找view
        LayoutInflater.from(getContext()).inflate(R.layout.layout_edit,this);
        btClear = (ImageView) findViewById(R.id.bt_clear);
        btClear.setOnClickListener(this);
        inputLayout = (TextInputLayout) findViewById(R.id.text_input_layout);
        inputLayout.getEditText().addTextChangedListener(watcher);
        inputLayout.getEditText().setOnFocusChangeListener(onFocusChangeListener);
        initAttrs(attrs);
    }

    /**
     * 初始化 属性
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        //获取MEditLayout 在attrs中的自定义属性；
      TypedArray array= getContext().obtainStyledAttributes(attrs,R.styleable.MEditLayout);
        //根据TypedArray的长度进行读取
        for (int i=0;i<array.getIndexCount();i++){
            //获取此项属性的id
            int index = array.getIndex(i);
            switch (index){
                case R.styleable.MEditLayout_mDeleteImageSrc:
                    Drawable drawable = array.getDrawable(index);
                    if (drawable!=null){
                        btClear.setImageDrawable(drawable);
                    }
                    break;
                case R.styleable.MEditLayout_mInputType:
                    setInputType(array.getInt(index,TEXT));
                    break;
                case R.styleable.MEditLayout_mHint:
                    inputLayout.setHint(array.getString(index));
                    break;
                case R.styleable.MEditLayout_mMaxLength:
                    setMaxLength(array.getInteger(index,0));
                    break;
                case R.styleable.MEditLayout_mTextSize:
                    int size = array.getDimensionPixelSize(index,20);
                    inputLayout.getEditText().setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
                    break;

            }

        }
    }

    public void setMaxLength(int maxLength){
        if (maxLength >0) {
            inputLayout.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
    }


    private void initThis() {
        setOrientation(HORIZONTAL);
        //防止edit 在显示时获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //作为背景
        setBackgroundResource(R.drawable.select_medit_bg);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            setSelected(b);
            if (b){
                if (inputLayout.getEditText().getText().length()>0){
                    btClear.setVisibility(VISIBLE);
                }
            }else{
                btClear.setVisibility(GONE);
            }
        }
    };

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length()>0){
                btClear.setVisibility(VISIBLE);
            }else{
                btClear.setVisibility(GONE);
            }
        }
    };

    @Override
    public void onClick(View view) {
        inputLayout.getEditText().getText().clear();
    }

    public Editable  getText(){
        return  inputLayout.getEditText().getText();
    }

    public void setText(String text){
        inputLayout.getEditText().getText().clear();
        inputLayout.getEditText().getText().append(text);
    }

    public void setTextColor(int color){
        inputLayout.getEditText().setTextColor(color);
    }

    public void setInputType(int inputType){
        int type = InputType.TYPE_CLASS_TEXT;
        switch (inputType){
            case TEXT:
                type = InputType.TYPE_CLASS_TEXT;
                break;
            case NUMBER:
                type = InputType.TYPE_CLASS_NUMBER;
                break;
            case PASSWORD:
                type = InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;
        }
        inputLayout.getEditText().setInputType(type);
    }
}
