package com.github.sioncheng.dubbo.study.basic.spi;

import java.util.ServiceLoader;

public class SayHelloMain {

    public static void main(String[] args) {
        ServiceLoader<ISayHello> iSayHellos = ServiceLoader.load(ISayHello.class);
        for (ISayHello iSayHello:
             iSayHellos) {
            iSayHello.say();
        }
    }
}
