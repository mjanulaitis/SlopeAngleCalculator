package com.avatech.slopeangle.views;

import android.databinding.DataBindingUtil;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avatech.slopeangle.R;
import com.avatech.slopeangle.databinding.ActivityAboveSlopeBinding;
import com.avatech.slopeangle.pikanji.CameraPreview;
import com.avatech.slopeangle.pikanji.ResizableCameraPreview;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboveSlopeCameraActivity extends AppCompatActivity
{
    private ResizableCameraPreview mPreview;
    private ActivityAboveSlopeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_above_slope);
        binding.setVm(MainActivity.viewModel);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        MainActivity.viewModel.onResume(this);
        createCameraPreview();
    }

    @Override
    protected void onPause()
    {
        MainActivity.viewModel.onPause();

        super.onPause();
        mPreview.stop();
        binding.layout.removeView(mPreview);
        mPreview = null;
    }

    private void createCameraPreview()
    {
        mPreview = new ResizableCameraPreview(this, 0, CameraPreview.LayoutMode.FitToParent, false);
        RelativeLayout.LayoutParams previewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        binding.layout.addView(mPreview, 0, previewLayoutParams);
    }

    public void onRecord(View view)
    {
        MainActivity.viewModel.recordRoll();
        finish();
    }
}
