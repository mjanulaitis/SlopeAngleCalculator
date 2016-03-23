package com.avatech.slopeangle.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avatech.slopeangle.R;

//Displayed when a menu item is selected has not yet been implemented.
public class NotImplementedYetFragment extends Fragment
{
    public NotImplementedYetFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_not_implemented_yet, container, false);
    }
}
