package com.skymxc.demo.lesson_24_tab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sky-mxc
 */

public class MsgFragment extends Fragment {
    private View  view;
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            button = new Button(getActivity());

        button.setBackgroundColor(Color.DKGRAY);
        button.setText("Message");
        return button;

    }
}
