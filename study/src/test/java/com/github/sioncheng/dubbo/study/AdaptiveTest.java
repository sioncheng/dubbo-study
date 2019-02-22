package com.github.sioncheng.dubbo.study;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import org.junit.Assert;
import org.junit.Test;


public class AdaptiveTest {

    @Test
    public void testAdaptiveExtension() {
        ExtensionLoader<EchoAdaptiveExt> extensionLoader = ExtensionLoader.getExtensionLoader(EchoAdaptiveExt.class);
        EchoAdaptiveExt adaptiveExtension = extensionLoader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?echo.adaptive.ext=dubbo");
        String echoVal = adaptiveExtension.echo("d", url);
        Assert.assertEquals("DubboEchoAdpatvieExtImpl d", echoVal);

        URL url2 = URL.valueOf("test://localhost/test2");
        String echoVal2 = adaptiveExtension.echo("d", url);
        Assert.assertEquals("DubboEchoAdpatvieExtImpl d", echoVal2);
    }
}
