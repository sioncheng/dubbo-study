package com.github.sioncheng.dubbo.study;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

@SPI("dubbo")
public interface EchoAdaptiveExt {
    @Adaptive
    String echo(String message, URL url);
}
