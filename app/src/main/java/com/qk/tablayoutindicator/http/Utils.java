package com.qk.tablayoutindicator.http;

import android.text.TextUtils;

/**
 * Created by qk on 2016/11/10.
 */

public class Utils {
    public static boolean isNullOrEmpty(String s){
        if(s == null){
            return true;
        }
        if(TextUtils.isEmpty(s)){
            return true;
        }
        return false;
    }
}
