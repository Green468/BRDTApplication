package com.example.presentation.fragments;

import static android.os.SystemClock.sleep;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.ActivityInfo;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.lifecycle.LifecycleOwner;

import com.example.presentation.R;
import com.example.presentation.databinding.FragmentCameraViewBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class CameraViewFragment extends Fragment {
    private FragmentCameraViewBinding binding;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    BottomNavigationView bottomNavigationView;

//    ActivityCameraViewBinding binding;
    PreviewView camview1, camview2;
    public static CameraViewFragment newInstance() {
        CameraViewFragment fragment = new CameraViewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCameraViewBinding.inflate(inflater, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        camview1 = binding.cameraview1;
        camview2 = binding.cameraview2;


        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(getActivity()));

        bottomNavigationView =  (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.INVISIBLE);
    }
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder().build();
        Preview preview2 = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        CameraSelector cameraSelector2 = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();


        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        // enable the following line if RGBA output is needed.
                        //.setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER).setImageQueueDepth(6)
                        .build();

        imageAnalysis.setAnalyzer(Executors.newFixedThreadPool(1),
                new ImageAnalyser());

        preview.setSurfaceProvider(camview1.getSurfaceProvider());
        preview2.setSurfaceProvider(camview2.getSurfaceProvider());

        try {
            // Unbind use cases before rebinding
//            cameraProvider.unbindAll();

            // Bind use cases to camera
//            cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview);
            cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector2, preview2,imageAnalysis);


        } catch(Exception e) {
            Log.e("CameraX:", "Use case binding failed", e);
        }

//        final Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//            }
//        }, 32);


    }
    private class ImageAnalyser implements ImageAnalysis.Analyzer {

        @Override
        public void analyze(@NonNull ImageProxy image) {
            ByteBuffer imageBuffer = image.getPlanes()[0].getBuffer();
            StringBuilder sb = new StringBuilder();
            sb.append("format:" + image.getFormat()).append("\n")
                    .append(image.getWidth() + " x " + image.getHeight()).append("\n\n");

            for (int i=0; i<image.getPlanes().length; i++) {
                sb.append("pixel stride:").append(image.getPlanes()[i].getPixelStride())
                        .append("\nrow stride:").append(image.getPlanes()[i].getRowStride())
                        .append("\n");
            }
            Log.i("Camera:",sb.toString());
            image.close();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
