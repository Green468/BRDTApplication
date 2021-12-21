//package com.example.presentation;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.graphics.Camera;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.ViewGroup;
//import android.widget.Chronometer;
//import android.widget.FrameLayout;
//import android.widget.MediaController;
//import android.widget.TextView;
//import android.widget.VideoView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.presentation.databinding.ActivityCameraViewBinding;
//import com.example.presentation.databinding.ActivityMainoneviewBinding;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
//
//import java.io.File;
//
//public class CameraViewActivity extends AppCompatActivity {
//    Preview preview = new Preview(this);
//    ActivityCameraViewBinding binding;
//    FrameLayout camview1, camview2;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityCameraViewBinding.inflate(getLayoutInflater());
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        try {
//            this.getSupportActionBar().hide();
//        }
//        catch (NullPointerException e){}
//        setContentView(binding.getRoot());
//
//    }
//
//    private void releaseCameraAndPreview() {
//        preview.setCamera(null);
//        if (camera != null) {
//            camera.release();
//            camera = null;
//        }
//    }
//    public void setCamera(Camera camera) {
//        if (mCamera == camera) { return; }
//
//        stopPreviewAndFreeCamera();
//
//        mCamera = camera;
//
//        if (mCamera != null) {
//            List<Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
//            supportedPreviewSizes = localSizes;
//            requestLayout();
//
//            try {
//                mCamera.setPreviewDisplay(holder);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Important: Call startPreview() to start updating the preview
//            // surface. Preview must be started before you can take a picture.
//            mCamera.startPreview();
//        }
//    }
//}
//class Preview extends ViewGroup implements SurfaceHolder.Callback {
//
//    SurfaceView surfaceView;
//    SurfaceHolder holder;
//
//    Preview(Context context) {
//        super(context);
//
//        surfaceView = new SurfaceView(context);
//        addView(surfaceView);
//
//        // Install a SurfaceHolder.Callback so we get notified when the
//        // underlying surface is created and destroyed.
//        holder = surfaceView.getHolder();
//        holder.addCallback(this);
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//    }
//
//    @Override
//    public void surfaceCreated(@NonNull SurfaceHolder holder) {
//
//    }
//
//    @Override
//    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
//        // Surface will be destroyed when we return, so stop the preview.
//        if (mCamera != null) {
//            // Call stopPreview() to stop updating the preview surface.
//            mCamera.stopPreview();
//        }
//    }
//    /**
//     * When this function returns, mCamera will be null.
//     */
//    private void stopPreviewAndFreeCamera() {
//
//        if (mCamera != null) {
//            // Call stopPreview() to stop updating the preview surface.
//            mCamera.stopPreview();
//
//            // Important: Call release() to release the camera for use by other
//            // applications. Applications should release the camera immediately
//            // during onPause() and re-open() it during onResume()).
//            mCamera.release();
//
//            mCamera = null;
//        }
//    }
//}