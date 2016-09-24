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

public class ContactsFragment extends Fragment {
    private View  view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = new Button(getActivity());
        }

        view.setBackgroundColor(Color.GRAY);
        return view;

    }
}
