package com.github.sioncheng.dubbo.study.custom.spi.log;

import com.github.sioncheng.dubbo.study.custom.spi.annotation.MySpi;

@MySpi(value = "com.github.sioncheng.dubbo.study.custom.spi.log.impl.ConsoleLog")
public interface Log {
    void info(String info);
}
