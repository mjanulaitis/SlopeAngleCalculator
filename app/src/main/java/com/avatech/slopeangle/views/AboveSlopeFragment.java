package com.avatech.slopeangle.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avatech.slopeangle.R;

//Displays the camera's view and overlays the angle of orientation relative to
//the location being pointed at.
public class AboveSlopeFragment extends Fragment
{
    public AboveSlopeFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_above_slope, container, false);
    }
}
