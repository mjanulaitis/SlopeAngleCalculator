package com.avatech.slopeangle.views;

import android.databinding.DataBindingUtil;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avatech.slopeangle.R;
import com.avatech.slopeangle.viewmodels.SlopeAngleViewModel;
import com.avatech.slopeangle.databinding.FragmentOnSlopeBinding;

//When the phone is in an upright orientation, displays the current angle of the
//long edge relative to the ground.  Uses databinding to automatically update
//the angle.
public class OnSlopeFragment extends Fragment
{
    private static MainActivity activity;

    public OnSlopeFragment()
    {
    }

    public void setActivity(MainActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FragmentOnSlopeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_slope, container, false);
        binding.setVm(MainActivity.viewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        activity.onResumeViewModel();
    }

    @Override
    public void onPause()
    {
        activity.viewModel.onPause();
        super.onPause();
    }


}
