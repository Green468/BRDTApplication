//package com.example.presentation.fragments;
//
//import android.app.AlertDialog;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.presentation.MainActivity;
//import com.example.presentation.R;
//
//public class BaseFragment extends Fragment {
//
//    public String entityName = "";
//    public MainActivity getMainActivity(){
//        return (MainActivity) getActivity();
//    }
//
//    /**
//     * Diplaying fragment view for selected item
//     * */
//    public void displayDetail(BaseFragment mDetailFragment){
//        if (mDetailFragment != null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .replace(R.id.nav_host_fragment_content_main, mDetailFragment).addToBackStack(null).commit();
//        } else {
//            // error in creating fragment
//            Log.e("MainActivity", "Error in creating detail fragment");
//        }
//    }
//
//    public CharSequence getTitle(){
//        return getMainActivity().getSupportActionBar().getTitle();
//    }
//
//    public void setTitle(CharSequence title){
//        getMainActivity().getSupportActionBar().setTitle(title);
//    }
//
////    public SpannableString resizeString(String source){
////        int startSplit = source.indexOf("-");
////        source = source.substring(0, startSplit+1) +"\n" +source.substring(startSplit+1, source.length());
////        SpannableString ss1=  new SpannableString(source);
////        ss1.setSpan(new RelativeSizeSpan(2f), (startSplit+1), source.length(), 0);
////        return ss1;
////    }
//
//    public void processJsonMessage(Object jsonObject){
//
//    }
//
//    public void showAlertDialog(LayoutInflater mLayoutInflater, String message){
////        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getMainActivity());
////        View dialogView = mLayoutInflater.inflate(R.layout.dialog, null);
////        alertDialogBuilder.setView(dialogView);
////
////        TextView messageView = (TextView) dialogView.findViewById(R.id.message);
////        Button enterBtn = (Button) dialogView.findViewById(R.id.enterBtn);
////
////        final AlertDialog alertDialog = alertDialogBuilder.create();
////
////        messageView.setText(message);
////        enterBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                alertDialog.cancel();
////            }
////        });
////        alertDialog.show();
//
//    }
//}
