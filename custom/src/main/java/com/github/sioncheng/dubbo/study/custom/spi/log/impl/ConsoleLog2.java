package com.github.sioncheng.dubbo.study.custom.spi.log.impl;

import com.github.sioncheng.dubbo.study.custom.spi.log.Log;

public class ConsoleLog2 implements Log {
    public void info(String info) {
        System.out.println("ConsoleLog2-info: " + info);
    }
}
