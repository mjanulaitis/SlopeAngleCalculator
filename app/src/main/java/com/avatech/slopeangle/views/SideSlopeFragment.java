package com.avatech.slopeangle.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avatech.slopeangle.R;

//Uses the camera to take a picture, the user then draws a line, then the app
//calculates the angle of the line relative to the base.
public class SideSlopeFragment extends Fragment
{
    public SideSlopeFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_side_slope, container, false);
    }
}
