package com.github.sioncheng.dubbo.study.custom.spi.log.impl;

import com.github.sioncheng.dubbo.study.custom.spi.log.Log;

public class ConsoleLog implements Log {
    public void info(String info) {
        System.out.println("ConsoleLog-info: " + info);
    }
}
