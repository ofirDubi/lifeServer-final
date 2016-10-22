package com.example.user.navigatelifesaver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by User on 8/27/2016.
 */
public class ReadFragment extends android.app.Fragment {

    ImageView imgTakenPhoto ;
    private static final int CAM_REQUEST = 1313;
    public Bitmap thumbnail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_read, container, false);

        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE) ;
        startActivityForResult(cameraintent , CAM_REQUEST);

        return rootView;
    }


}
