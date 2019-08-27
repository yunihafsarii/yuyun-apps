package com.example.yunihafsari.fypversion3;

import android.app.Application;

/**
 * Created by yunihafsari on 25/04/2017.
 */

public class FirebaseChatMainApp extends Application{

    public static boolean sIsChatActivityOpen = false;

    public static boolean issIsChatActivityOpen() {
        return sIsChatActivityOpen;
    }

    public static void setsIsChatActivityOpen(boolean sIsChatActivityOpen) {
        FirebaseChatMainApp.sIsChatActivityOpen = sIsChatActivityOpen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}


// 3aTcp17hgENRlnwguXYPClJYfAW2 e3oMTBPGUiQ:APA91bH-D89twVtV6r9HoXi2XqwRkn4njN6nwIRvy8smkl_Oy8UycP6LmvMyim8xQlPUG4yecD8a8AKdaT9fipG1R_4MzXbTKqcxJh7yAOH4XKGVOZD4c64p-yyU5-iucED7qTyDsNxI