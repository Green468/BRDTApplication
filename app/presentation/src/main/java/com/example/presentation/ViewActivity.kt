package com.example.presentation

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Environment
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.presentation.opengl.StereoIotdCameraView
import com.green.stereovideodemo.IGetDeviceRotation
import com.green.stereovideodemo.IStereoVideoView
import com.green.stereovideodemo.StereoIotdVideoView
import java.text.DecimalFormat
import android.os.Bundle as Bundle1
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.presentation.fragments.VisualAcuityTestFragment


lateinit var staticMediaPlayer: MediaPlayer

class ViewActivity : AppCompatActivity(), IGetDeviceRotation, TextureView.SurfaceTextureListener {
    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    //inter pupil distance
    val ipd : Float? = 63F
    val w : Float?  = 65F
    var curposition: Int = 0
    var videoSource = ""
    var dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

    private var speechRecognizer: SpeechRecognizer? = null
    var speechIntent: Intent? = null
    var txtview: TextView? = null
    var layout:FrameLayout? = null
    var videoUri = Uri.parse(videoSource)

    val rootView:ViewGroup by lazy { findViewById(android.R.id.content) }
    val mediaPlayer: MediaPlayer by lazy {

        MediaPlayer.create(applicationContext, videoUri).apply {
            //isLooping = true
            setOnErrorListener { mp, what, extra ->
                Log.e("Green", "Green mediaplayer error: $what | $extra")
                false
            }
            setOnCompletionListener {
                Log.e("Green", "Green mediaplayer complete")
            }
        }
    }
    var stereoVideoView: IStereoVideoView? = null

    var videoWidthMm = 10f
    var videoDistanceMm = 10f

    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras!=null) {
            var url:String = extras.getString("link").toString()
            videoSource = url;
            videoUri = Uri.parse("https://iotd.terasoftvn.com/protectedVideos/video001.mp4")
        }
//
        ActivityCompat.requestPermissions(
            this,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE
        )

        staticMediaPlayer = mediaPlayer

        Log.d("Green", "Green external storage file: " + videoUri)

        setContentView(R.layout.activity_view)
        txtview = findViewById(R.id.txtview)
        layout = findViewById<FrameLayout>(R.id.frm_video)!!

        videoWidthMm = w!!
        videoDistanceMm = ipd!!
        updateVideoAttributes()

        // Check permission and start the camera
        if (allPermissionsGranted()) {
            startCamera()
        }
        else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION
            )
        }

        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(this@ViewActivity)
        speechRecognizer!!.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle1) {
                Log.i("Speech to text", "Ready")
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray) {}
            override fun onEndOfSpeech() {
            }
            override fun onError(error: Int) {
                speechRecognizer?.startListening(speechIntent)

            }
            override fun onResults(results: Bundle1) {
                val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Log.i("Speech to text", data!![0].toString())
                txtview!!.setText(data!![0].toString())
                when (data.get(0).toString()){
                    "video" -> {
                        changetoVideo()
                        if (mediaPlayer!=null){
                            mediaPlayer.seekTo(curposition)
                            mediaPlayer.start()
                        }
                    }
                    "camera" -> {
                        changetoCam()
                        if (mediaPlayer!=null){
                            mediaPlayer.pause()
                            curposition =mediaPlayer.currentPosition
                        }
                    }
                    "stop" -> {
                        if (mediaPlayer!=null){
                            mediaPlayer.stop()
                        }
                        speechRecognizer!!.stopListening()
                        speechRecognizer!!.destroy()
                        val i = Intent(this@ViewActivity, MainActivity::class.java)
                        i.putExtra("fragment","6");
                        startActivity(i)
                    }
                    "test" ->{
                        if (mediaPlayer!=null){
                            mediaPlayer.pause()
                        }
                        speechRecognizer!!.stopListening()
                        if(savedInstanceState == null) { // initial transaction should be wrapped like this
                            val mFragment = VisualAcuityTestFragment.newInstance()
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.nav_host_fragment_container, mFragment)
                                .commitAllowingStateLoss()
                        }
                    }
                    else -> Log.i("Speech to text", data!![0].toString())
                }
//                speechRecognizer?.startListening(speechIntent)

            }

            override fun onPartialResults(partialResults: Bundle1) {}
            override fun onEvent(eventType: Int, params: Bundle1) {}
        })
        // setup speechrecognizer intent
        speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent!!.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechIntent!!.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text")
        // reduce the delay of Internet quality
        // reduce the delay of Internet quality
        speechIntent!!.putExtra(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH,
            RecognizerIntent.EXTRA_PREFER_OFFLINE
        )

        speechRecognizer!!.startListening(speechIntent)

    }

    private fun updateVideoAttributes() {
        val fmt = DecimalFormat("###,###,##0.00")

        stereoVideoView?.setVideoAttributes(videoWidthMm, videoDistanceMm)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (allPermissionsGranted()) {
                startCamera()
            }
            else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    lateinit var textureView : TextureView

    private fun startCamera() {
        stereoVideoView = StereoIotdVideoView(this, mediaPlayer).apply {
//        stereoVideoView = StereoIotdCameraView(this, this, this).apply{
            layoutParams =  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setIotdValue(0)
        }
//            stereoVideoView = StereoIotdVideoView(this, mediaPlayer).apply {
//            layoutParams =  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//            setIotdValue(0)
//        }

        updateVideoAttributes()
        layout?.addView(stereoVideoView as View)

//        textureView = TextureView(this)
//        var layoutParam = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        textureView.layoutParams = layoutParam
//        textureView.surfaceTextureListener = this
//        findViewById<FrameLayout>(R.id.frm_video).addView(textureView as View)
    }

    class SeekBarChangedListener(val cb: (Int) -> Unit): SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            cb(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    private fun changetoCam(){
        layout?.removeAllViews()
        stereoVideoView = StereoIotdCameraView(this, this, this).apply{
            layoutParams =  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setIotdValue(0)
        }
        updateVideoAttributes()
        layout?.addView(stereoVideoView as View)
        speechRecognizer?.startListening(speechIntent)
    }

    private fun changetoVideo(){
        layout?.removeAllViews()
        stereoVideoView = StereoIotdVideoView(this, mediaPlayer).apply {
            layoutParams =  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setIotdValue(0)
        }
        updateVideoAttributes()
        layout?.addView(stereoVideoView as View)
        speechRecognizer?.startListening(speechIntent)

    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

//    private fun getOutDirrectory(): File {
//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
//        }
//        return if (mediaDir != null && mediaDir.exists())
//            mediaDir else filesDir
//    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSION = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun getRotation(): Int {
        var rotation: Int = 0
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                rotation = display!!.rotation
            }
        } catch (exc: NoSuchMethodError) {
            rotation = windowManager.defaultDisplay.rotation
        }
        return rotation
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        val s = Surface(surface)
        mediaPlayer.setSurface(s)
        mediaPlayer.start()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        speechRecognizer!!.stopListening()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        speechRecognizer!!.stopListening()

    }
}