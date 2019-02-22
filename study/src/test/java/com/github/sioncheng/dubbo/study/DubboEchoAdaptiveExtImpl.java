package com.github.sioncheng.dubbo.study;

import com.alibaba.dubbo.common.URL;

public class DubboEchoAdaptiveExtImpl implements EchoAdaptiveExt {
    @Override
    public String echo(String message, URL url) {
        return "DubboEchoAdpatvieExtImpl " + message;
    }
}
