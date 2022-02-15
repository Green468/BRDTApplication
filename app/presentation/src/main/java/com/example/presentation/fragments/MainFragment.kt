package com.example.presentation.fragments

import org.json.JSONArray
import org.json.JSONObject
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.pm.ActivityInfo
import android.os.Environment
import com.example.presentation.MainActivity
import org.json.JSONException
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import android.os.Build
import android.content.Intent
import android.util.Log
import com.example.presentation.ViewActivity
import com.example.presentation.R
import android.util.SparseArray
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.presentation.databinding.FragmentMainBinding
import com.example.presentation.fragments.MainFragment
import androidx.appcompat.widget.Toolbar


//import com.example.presentation.VideoViewActivity;
class MainFragment : Fragment() {
    private var binding: FragmentMainBinding? = null
    var picker: NumberPicker? = null
    var pickerVals = arrayOf("50", "20", "100", "0", "100", "20", "50")
    var bttstart: Button? = null
    var videolist: ListView? = null
    var adapter: ArrayAdapter<String?>? = null
    var link: String? = null
    var iotd: String? = null
    lateinit var listItem: Array<String?>
    var isexit = 0
    var jsonArray: JSONArray? = null
    var jobj: JSONObject? = null
    var jsonbody: String? = null
    var url: String? = null
    var videodetail: TextView? = null
    var mToolbar: Toolbar? = null
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        Bundle bundle = getArguments();
//        if (bundle != null) {
//            jsonbody = bundle.getString("videos","");
//            //Json mess reading
//            try {
//                jobj = new JSONObject(jsonbody);
//                jsonArray = jobj.getJSONArray("videos");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            for (int i=0;i< jsonArray.length();i++) {
//                try {
//                    JSONObject subobj = jsonArray.getJSONObject(i);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = FragmentMainBinding.inflate(inflater, container, false)
        //        picker = binding.iotdPicker;
        mToolbar = requireActivity().findViewById<View>(R.id.toolbar_actionbar) as Toolbar
        mToolbar!!.visibility = View.VISIBLE

        videolist = binding!!.videoList
        videodetail = binding!!.videoDetail
        //        picker.setMaxValue(6);
//        picker.setMinValue(0);
//        picker.setDisplayedValues(pickerVals);
        val path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

        //Json mess reading
        if ((activity as MainActivity?)!!.videolist != null) {
            jsonbody = (activity as MainActivity?)!!.videolist
            try {
                jobj = JSONObject(jsonbody)
                jsonArray = jobj!!.getJSONArray("videos")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            //
//            for (int i=0;i< jsonArray.length();i++) {
//                try {
//                    JSONObject subobj = jsonArray.getJSONObject(i);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
        }

        //getting file from storage
//        try {
//            File directory = new File(path);
//            File[] files = directory.listFiles();
//            FragmentManager fm = new FragmentManager() {
//                @Nullable
//                @Override
//                public Fragment findFragmentById(int id) {
//                    return super.findFragmentById(id);
//                }
//            };
//
//            if (files.length !=0)
//            {
//                listItem = new String[files.length];
//
//                for (int i = 0; i < jsonArray.length(); i++)
//                {
//                    Log.d("Files", "FileName:" + files[i].getName());
////                    listItem[i] = files[i].getName();
//                    listItem[i] = jsonArray.getJSONObject(i).toString();
//                }
//                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,
//                        android.R.id.text1,
//                        listItem);
//
//            }else Log.e("Files", "Folder not found:");
//        }
//        catch (Exception e){
//            Log.e("Files", "Folder not found:");
//        }
        if (jsonArray != null) {
            listItem = arrayOfNulls(jsonArray!!.length())
            for (i in 0 until jsonArray!!.length()) {
                try {
                    listItem[i] = jsonArray!!.getJSONObject(i)["name"].toString()
                } catch (jsonException: JSONException) {
                    jsonException.printStackTrace()
                }
            }
            adapter = ArrayAdapter(
                requireActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1,
                listItem
            )
        } else Log.e("Files", "Folder not found:")
        videolist!!.adapter = adapter
        videolist!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            link = parent.getItemAtPosition(position) as String
            try {
                videodetail!!.text =
                    "Time:" + jsonArray!!.getJSONObject(position)["current_time"].toString() + "/n" +
                            "Duration:" + jsonArray!!.getJSONObject(position)["time"].toString() + "/n" +
                            "Feedback:"
                url = jsonArray!!.getJSONObject(position)["url"].toString()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

//                iotd = pickerVals[picker.getValue()];
            Log.i("Select:", link!!)
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.btStartview.setOnClickListener {
            val intent = Intent(activity, ViewActivity::class.java)
//            val reallink = loadYoutube("https://youtu.be/K2fkCcjzBrQ")
            intent.putExtra("link", "https://youtu.be/K2fkCcjzBrQ")
            startActivity(intent)

            Log.i("Mainview:", "startvideo")
        }
        bottomNavigationView =
            requireActivity().findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView!!.visibility = View.VISIBLE
    }

    private fun loadYoutube(url: String): String? {
        val mediaurl = arrayOf<String?>(null)
        object : YouTubeExtractor(requireActivity()) {
            public override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>,
                vMeta: VideoMeta
            ) {
                if (ytFiles != null) {
                    isexit = 1
                    val itag = 22
                    val downloadUrl = ytFiles[itag].url
                    Log.i("youtubeplayer:", "Opening$downloadUrl")
                    mediaurl[0] = downloadUrl
                }
            }
        }.extract(url, true, true)
        return mediaurl[0]
    }

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
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}