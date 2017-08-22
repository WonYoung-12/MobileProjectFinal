package com.example.user.mobileproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Handler;

/**
 * Created by user on 2016-05-29.
 */
public class Fragment_mypage extends Fragment{
    String nickname;
    long id;
    String profile;
    TextView textname,textid;
    GlobalApplication app;
    ImageView image_profile;

    public Fragment_mypage(){};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        app = (GlobalApplication) getActivity().getApplicationContext();

        id = app.user.main_user.getId();
        nickname = app.user.main_user.getNickname();
        profile = app.user.kakaoTalkProfile.getProfileImageUrl();

        textname = (TextView) view.findViewById(R.id.fragment_mypage_nickname);

       image_profile = (ImageView) view.findViewById(R.id.fragment_mypage_profile);

        textname.setText(nickname);

        Glide.with(this).load(profile).into(image_profile);

        return view;
    }
}
