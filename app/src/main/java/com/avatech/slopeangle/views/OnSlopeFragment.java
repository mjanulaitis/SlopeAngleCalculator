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

public class OnSlopeFragment extends Fragment
{
    private SlopeAngleViewModel viewModel;

    public OnSlopeFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        SensorManager sensorManager = (SensorManager)getContext().getSystemService(android.content.Context.SENSOR_SERVICE);

        viewModel = new SlopeAngleViewModel(sensorManager, getResources());

        FragmentOnSlopeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_slope, container, false);
        binding.setVm(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume()
    {
        viewModel.onResume();
        super.onResume();
    }

    @Override
    public void onPause()
    {
        viewModel.onPause();
        super.onPause();
    }
}
