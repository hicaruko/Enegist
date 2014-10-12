package com.jakapong.enegist.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakapong.enegist.R;
import com.jakapong.enegist.utils.Constants;
import com.jakapong.enegist.utils.SessionManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by jakapong on 10/11/14 AD.
 */

public class ExaminationFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    DisplayImageOptions options;
    private SessionManager session;


    private  String strText  ;
    private  String strUrl  ;
    private  String strLink  ;
    private  int Fogus  ;

    public ExaminationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        final Bundle args = getArguments();
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        ImageView image = (ImageView) rootView.findViewById(R.id.imageView);
        final ProgressBar spinner = (ProgressBar) rootView.findViewById(R.id.loading);

        // change fogus session
        session = new SessionManager(getActivity().getApplicationContext());
        session.UpdatePageSession(Integer.parseInt(args.get(Constants.ExaminationFOGUS).toString()));

        strText = args.get(Constants.ExaminationTEXT).toString();
        strUrl =args.get(Constants.ExaminationURL).toString();
        strLink =args.get(Constants.ExaminationLINK).toString();

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();


        textView.setText(strText);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(strLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
              //  Toast.makeText(v.getContext(), strLink, Toast.LENGTH_SHORT).show();
             }
        });

        ImageLoader.getInstance().displayImage(strUrl, image, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }
             @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
            }
        });

        return rootView;
    }



}
