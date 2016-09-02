package com.skymxc.demo.lession_14_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

/**
 * Created by sky-mxc
 */
public class MyDialog extends Dialog {
    public MyDialog(Context context) {
        super(context,R.style.MyDialog);
    }

    private TimePicker timePicker ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mydialog);
        timePicker = (TimePicker) findViewById(R.id.timepicker);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                Log.e("Tag","========="+hour+":"+minute);
            }
        });
    }
}
