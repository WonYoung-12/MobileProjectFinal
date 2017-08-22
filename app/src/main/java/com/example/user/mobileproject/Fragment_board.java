package com.example.user.mobileproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016-05-29.
 */
public class Fragment_board extends Fragment{


    ViewPager pager;

    public Fragment_board(){};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_board, container , false);

        pager = (ViewPager)view.findViewById(R.id.board_pager);

        /////////////////////리시트 프레그먼트 연결, pager와 연결//////////////////////
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment_board_nomal());
        fragmentList.add(new Fragment_board_secret());
        BoardViewPageAdapter adapter = new BoardViewPageAdapter(getChildFragmentManager(), fragmentList);

        pager.setAdapter(adapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)view.findViewById(R.id.board_tabs);

        tabs.setShouldExpand(true); //중간 사이즈

        tabs.setViewPager(pager);

        /////////////////////////////////////////////////////////////////////////////////

        return view;
}
}
