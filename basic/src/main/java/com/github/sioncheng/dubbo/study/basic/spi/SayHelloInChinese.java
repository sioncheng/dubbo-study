package com.github.sioncheng.dubbo.study.basic.spi;

public class SayHelloInChinese implements ISayHello {
    public void say() {
        System.out.println("你好");
    }
}
