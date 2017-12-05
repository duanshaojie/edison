package cn.duanshaojie.wechat.demo;

import java.io.*;

import com.github.wxpay.sdk.WXPayConfig;

public class MyConfig implements WXPayConfig {
    public String getAppID() {
        return "";
    }

    public String getMchID() {
        return "";
    }

    public String getKey() {
        return "";
    }

    public InputStream getCertStream() {
        return null;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
