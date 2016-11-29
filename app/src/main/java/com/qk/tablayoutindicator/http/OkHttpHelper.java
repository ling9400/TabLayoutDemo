package com.qk.tablayoutindicator.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kay on 2016/11/1.
 */
public class OkHttpHelper {
    private static OkHttpClient okHttpClient;
    private Gson gson;
    private Handler handler;

    private OkHttpHelper(){
        okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);

        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance(){
        return new OkHttpHelper();
    }

    public void post(String url, Map<String, String> params, BaseCallBack callBack){
        Request request = buildRequest(url, params, HttpMethodType.POST);
        doRequest(request, callBack);
    }

    public void get(String url, BaseCallBack callBack){
        Request request = buildRequest(url, null, HttpMethodType.GET);
        doRequest(request, callBack);
    }

    public void doRequest(final Request request, final BaseCallBack callBack){

        callBack.onRequestBefore(request);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(request,e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callBack.onResponse(response, null);
                if(response.isSuccessful()){
                    String resultStr = response.body().string();
                    if(callBack.type == String.class){
//                        callBack.onSuccess(response, resultStr);
                        callBackSuccess(callBack, response, resultStr);
                    }else{
                        try{
                            Object obj = gson.fromJson(resultStr, callBack.type);
//                            callBack.onSuccess(response, obj);
                            callBackSuccess(callBack, response, obj);
                        }catch (JsonParseException e){
//                            callBack.onError(response, response.code(), e);
                            callBackError(callBack, response);
                        }

                    }
                }else{
//                    callBack.onError(response, response.code(),null);
                    callBackError(callBack, response);
                }

            }
        });
    }

    private Request buildRequest(String url, Map<String, String> params, HttpMethodType type){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if(type == HttpMethodType.GET){
            builder.get();
        }else if(type == HttpMethodType.POST){
            RequestBody body = buildFormData(params);
            builder.post(body);
        }
        return builder.build();
    }

    private RequestBody buildFormData(Map<String, String> params){
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if(params != null){
            for(String key : params.keySet()){
                builder.add(key, params.get(key));
            }
        }
        return builder.build();
    }

    private void callBackSuccess(final BaseCallBack callBack, final Response response, final Object object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response, object);
            }
        });
    }

    private void callBackError(final BaseCallBack callBack, final Response response){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onError(response, response.code(), null);
            }
        });
    }


    enum HttpMethodType{
        GET,
        POST
    }
}
