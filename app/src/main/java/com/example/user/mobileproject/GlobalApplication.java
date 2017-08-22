package com.example.user.mobileproject;

/**
 * Created by user on 2016-06-18.
 */
import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.usermgmt.response.model.UserProfile;

public class GlobalApplication extends Application {

    User user = new User();
    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }
    //[출처] [Android] AndroidStudio 카카오톡 로그인 연동하기(4) - 로그인 연동 (마지막)|작성자 Printf
}

