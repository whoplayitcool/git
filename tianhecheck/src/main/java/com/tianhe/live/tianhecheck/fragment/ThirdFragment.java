package com.tianhe.live.tianhecheck.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianhe.live.tianhecheck.R;


/**
 * Created by ${谭姜维} on 2016/10/18.
 */
public class ThirdFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

//        View view=View.inflate(container.getContext(), R.layout.first_fragment, null);
        View view = View.inflate(container.getContext(), R.layout.third_fragment, null);

        return view;

    }
}
