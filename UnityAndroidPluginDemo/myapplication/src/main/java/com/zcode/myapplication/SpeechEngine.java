package com.zcode.myapplication;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;


import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;


public class SpeechEngine {
    Context context;
    public SpeechEngine(Context context){
        this.context = context;
    }

    private SpeechRecognizer mIat;
    private String mRecognizerResult = "请先调用StartSpeech()方法";


    public void InitConnection() {
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5a0a46aa");
    }

    //Unity调用的方法
    public void StartSpeech() {
        Toast.makeText(context, "开始听写，请说话!", Toast.LENGTH_SHORT).show();
        setParams();
        mIat.startListening(mRecognizerListener);
    }

    public void ShowWord() {
        Toast.makeText(context,mRecognizerResult,Toast.LENGTH_SHORT).show();
        //return mRecognizerResult;
    }

    private void setParams() {
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(context, null);
        //2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
    }

    //听写监听器
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
        }

        @Override
        public void onBeginOfSpeech() {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            mRecognizerResult = analyzeRecognizerResult(recognizerResult);
            //Toast.makeText(getApplicationContext(), mRecognizerResult, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SpeechError speechError) {
            speechError.getPlainDescription(true); //获取错误码描述}
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResultsHashMap = new LinkedHashMap<String, String>();

    //解析结果提取文字的方法
    private String analyzeRecognizerResult(RecognizerResult recognizerResult) {
        String text = JsonParser.parseIatResult(recognizerResult.getResultString());
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResultsHashMap.put(sn, text);

        StringBuffer mResultBuffer = new StringBuffer();
        for (String key : mIatResultsHashMap.keySet()) {
            mResultBuffer.append(mIatResultsHashMap.get(key));
        }
        //resultBuffer.append(results.getResultString());
        return mResultBuffer.toString();
    }

}

