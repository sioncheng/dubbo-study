package com.github.sioncheng.dubbo.study.custom.spi.loader;

import com.github.sioncheng.dubbo.study.custom.spi.log.Log;
import org.junit.Assert;
import org.junit.Test;

public class SpiLoaderTest {

    @Test
    public void testLoad() throws Exception{
        SpiLoader<Log> loader = SpiLoader.getExtensionLoader(Log.class);
        Assert.assertNotNull(loader);
        Log defaultLog = loader.getDefaultExtension();
        Assert.assertNotNull(defaultLog);
        Log log2 = loader.getExtension("log2");
        Assert.assertNotNull(log2);
        defaultLog.info("a");
        log2.info("b");
        Log log22 = loader.getExtension("log2");
        Assert.assertEquals(log2 , log22);
    }
}
