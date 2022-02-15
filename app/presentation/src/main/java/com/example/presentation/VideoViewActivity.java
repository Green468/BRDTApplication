//package com.example.presentation;
//
//import static java.lang.Math.abs;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.util.SparseArray;
//import android.view.Window;
//import android.widget.Chronometer;
//import android.widget.MediaController;
//import android.widget.TextView;
//import android.widget.VideoView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.example.presentation.databinding.ActivityMainBinding;
//import com.example.presentation.databinding.ActivityMainoneviewBinding;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
//import com.google.android.exoplayer2.extractor.ExtractorsFactory;
//import com.google.android.exoplayer2.source.ExtractorMediaSource;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import at.huber.youtubeExtractor.VideoMeta;
//import at.huber.youtubeExtractor.YouTubeExtractor;
//import at.huber.youtubeExtractor.YtFile;
//
//public class VideoViewActivity extends AppCompatActivity {
//    TextView timeview,timeview2,title;
//    Chronometer counter;
//    public int ispause=1,isplay=0,isstop=1,isprepared1=0,isprepared2=0, isseek1=0,isseek2=0,isexit=0,isyoutube=0;
//    // creating a variable for exoplayerview.
//    SimpleExoPlayerView exoPlayerView;
//    // creating a variable for exoplayer
//    SimpleExoPlayer exoPlayer;
//
//
//    String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
//    File file = new File(dir + File.separator +"sync_log" +".csv");
//
//    String videoURL2 = "https://drive.google.com/file/d/1qOxOe1KFCouA8oBfdJAdTdN_Xg6TU1Ii/view?fbclid=IwAR1vDQiptdxwALI3y-796jYjI1mAZw7mtbD9yVnPUjR15qokSHu6ohm8u0Q";
//    String link,iotd;
//    long delta;
//    private String YOUTUBE_VIDEO_ID = "DHKxoARmjLU";
//    private String BASE_URL = "https://www.youtube.com";
//    private String mYoutubeLink = BASE_URL + "/watch?v=" + YOUTUBE_VIDEO_ID;
//    // Storage Permissions
//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };
//    ActivityMainoneviewBinding binding;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//         binding = ActivityMainoneviewBinding.inflate(getLayoutInflater());
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        try
//        {
//            this.getSupportActionBar().hide();
//        }
//        catch (NullPointerException e){}
//        setContentView(binding.getRoot());
//
////        link = intent.getStringExtra("link");
////        iotd = intent.getStringExtra("iotd");
//        link = "images003_c_f4_l.mp4";
//        iotd = "96";
//        Log.i("Receive:",link +"  "+iotd );
//        delta = abs(Integer.parseInt(iotd)/2);
//        // Find your VideoView in your video_main.xml layout
////        simpleVideoView1 = (VideoView) findViewById(R.id.simpleVideoView1);
////        simpleVideoView2 = (VideoView) findViewById(R.id.simpleVideoView2);
//        timeview = (TextView) findViewById(R.id.timeview);
//        timeview2 = (TextView) findViewById(R.id.timeview2);
//        title = (TextView) findViewById(R.id.title);
//        counter = (Chronometer) findViewById(R.id.chrono);
//
////        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//        exoPlayerView = findViewById(R.id.idExoPlayerViewOne);
//        String file_link = dir+"/"+link;
//        title.setText(link);
//
//
//        ActivityCompat.requestPermissions(this,PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
//
//        if (isyoutube == 1) loadYoutube(videoURL2);
//        else {
//            isexit=1;
//            playDichoptic("https://iotd.terasoftvn.com/protectedVideos/video001_f2_l.mp4");
//        }
//
//        counter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
//            public void onChronometerTick(Chronometer arg0) {
//                if (isexit == 1){
//                    final long countUp = exoPlayer.getCurrentPosition();
//                    Log.i("chrono:", "videoview1: " +countUp);
//
//
//                    //Calibration IOTD
//
//                    int count = Math.round(countUp / 1000);
//                    long duration = exoPlayer.getDuration();
//                    duration = duration / 1000;
//                    long a = (long) count;
//                    String countText;
//                    if (count % 60000 <= 9) {
//                        countText = (a / 60000) + ":0" + (a % 60000);
//                    } else {
//                        countText = (a / 60000) + ":" + (a % 60000);
//                    }
//                    String durationText;
//                    if (duration % 60000 <= 9) {
//                        durationText = (duration / 60000) + ":0" + (duration % 60000);
//                    } else {
//                        durationText = (duration / 60000) + ":" + (duration % 60000);
//                    }
//                    timeview.setText(countUp + " IOID:" + iotd);
//                    if(countUp>120000  ){
//                    }
//                }
//
//            }
//        });
//        counter.start();
////
////        if (mediaControls == null) {
////            // create an object of media controller class
////            mediaControls = new MediaController(MainActivity.this);
////            mediaControls.setAnchorView(simpleVideoView1);
////        }
////        // set the media controller for video view
////        simpleVideoView1.setMediaController(mediaControls);
////        // set the uri for the video view
////        simpleVideoView1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cliptest));
////        simpleVideoView2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cliptest));
////
////        // start a video
//////        simpleVideoView1.start();
////
////        // implement on completion listener on video view
////        simpleVideoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
////            @Override
////            public void onCompletion(MediaPlayer mp) {
////                Toast.makeText(getApplicationContext(), "Thank You...!!!", Toast.LENGTH_LONG).show(); // display a toast when an video is completed
////            }
////        });
////        simpleVideoView1.setOnErrorListener(new MediaPlayer.OnErrorListener() {
////            @Override
////            public boolean onError(MediaPlayer mp, int what, int extra) {
////                Toast.makeText(getApplicationContext(), "Oops An Error Occur While Playing Video...!!!", Toast.LENGTH_LONG).show(); // display a toast when an error is occured while playing an video
////                return false;
////            }
////        });
////    }
//    }
//
//    private void writeToFile(long log1,long log2) {
//        try {
//            // if file doesnt exists, then create it
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            // Revoke newLine() method
//            fw.append(String.valueOf(log1)+','+String.valueOf(log2)+','+String.valueOf(log1 - log2));
//            fw.append("\n");
//            fw.flush();
//            fw.close();
////            bw.newLine();
////            bw.write();
////            bw.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private String loadYoutube(String url){
//        String youtubeLink = "https://youtu.be/6VjF638VObA";
//        final String[] mediaurl = {null};
//        new YouTubeExtractor(this) {
//            @Override
//            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
//                if (ytFiles != null) {
//                    isexit=1;
//                    int itag = 22;
//                    String downloadUrl = ytFiles.get(itag).getUrl();
//                    Log.i("youtubeplayer:", "Opening"+downloadUrl);
//                    mediaurl[0] = downloadUrl;
//                    playDichoptic(downloadUrl);
//                }
//            }
//        }.extract(videoURL2,true,true);
//        return mediaurl[0];
//    }
//
//    public void playDichoptic(String url){
//        try {
//
//            // bandwisthmeter is used for
//            // getting default bandwidth
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//
//            // track selector is used to navigate between
//            // video using a default seekbar.
//            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//
//            // we are adding our track selector to exoplayer.
//            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//            // we are parsing a video url
//            // and parsing its video uri.
////            Uri videouri = Uri.parse("asset:///clip.mp4");
////            Uri videouri = Uri.parse(RawResourceDataSource.buildRawResourceUri(R.raw.cliptest).toString());
//
////            Uri videouri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cliptest);
//            Uri videouri = Uri.parse(url);
//
//            // we are creating a variable for datasource factory
//            // and setting its user agent as 'exoplayer_view'
//            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
//
//            // we are creating a variable for extractor factory
//            // and setting it to default extractor factory.
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//            // we are creating a media source with above variables
//            // and passing our event handler as null,
//            MediaSource mediaSource = new ExtractorMediaSource(videouri, new DefaultDataSourceFactory(this, "exoplayer_video"),new DefaultExtractorsFactory(),null, null);
//
//
//            // inside our exoplayer view
//            // we are setting our player
//            exoPlayerView.setPlayer(exoPlayer);
//            //hide controller
//            exoPlayerView.setUseController(false);
//
//            // we are preparing our exoplayer
//            // with media source.
//            exoPlayer.prepare(mediaSource);
//
//            // we are setting our exoplayer
//            // when it is ready.
//            exoPlayer.setPlayWhenReady(true);
//
//        } catch (Exception e) {
//            // below line is used for
//            // handling our errors.
//            Log.e("TAG", "Error : " + e.toString());
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        exoPlayer.stop();
//        super.onPause();
//    }
//}
