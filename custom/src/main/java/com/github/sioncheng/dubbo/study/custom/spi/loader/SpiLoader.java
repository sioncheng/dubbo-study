package com.github.sioncheng.dubbo.study.custom.spi.loader;

import com.github.sioncheng.dubbo.study.custom.spi.annotation.MySpi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpiLoader<T> {

    public static <T> SpiLoader<T> getExtensionLoader(Class<T> clazz) {
        Object o = SPI_LOADERS.get(clazz);
        if (o == null) {
            SpiLoader<T> loader =  new SpiLoader<>(clazz);
            SPI_LOADERS.putIfAbsent(clazz, loader);
            o = loader;
        }

        return (SpiLoader<T>)o;
    }

    private SpiLoader(Class<T> tClass) {
        this.clazz = tClass;
    }

    public T getDefaultExtension()  throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException{
        ensureLoadConfig();
        if (defaultExtension == null) {
            loadDefaultExtension();
        }

        return defaultExtension;
    }

    public T getExtension(String name) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ensureLoadConfig();
        if (extensions.containsKey(name)) {
            return extensions.get(name);
        }
        return loadExtension(name);
    }

    private void ensureLoadConfig() throws IOException {
        if (config != null) {
            return;
        }

        config = new HashMap<>();
        extensions = new ConcurrentHashMap<>();

        ClassLoader classLoader = SpiLoader.class.getClassLoader();
        Enumeration<URL> resources = classLoader.getResources("custom-spi/" + clazz.getCanonicalName());
        if (resources == null) {
            return;
        }

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"))) {
                String line;
                while( (line = reader.readLine()) != null) {
                    line = line.trim();
                    String[] arr = line.split("=");
                    if (arr.length != 2) {
                        continue;
                    }
                    config.put(arr[0], arr[1]);
                }
            }
        }
    }

    private void loadDefaultExtension() throws ClassNotFoundException, IllegalAccessException, InstantiationException{
        if (!clazz.isAnnotationPresent(MySpi.class)) {
            defaultExtension = null;
        } else {
            final MySpi mySpi = clazz.getAnnotation(MySpi.class);
            final String className = mySpi.value();
            if (defaultExtension == null) {
                synchronized (this) {
                    if (defaultExtension == null) {
                        defaultExtension =
                                (T)Thread.currentThread().getContextClassLoader().loadClass(className).newInstance();
                    }

                }
            }
        }
    }

    private T loadExtension(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException{
        if (!extensions.containsKey(name)) {
            synchronized (extensions) {
                if (!extensions.containsKey(name)) {
                    extensions.putIfAbsent(name,
                            (T)Thread.currentThread().getContextClassLoader().loadClass(config.get(name)).newInstance());
                }
            }
        }

        return extensions.get(name);
    }

    private Class<T> clazz;
    private volatile Map<String, String> config;
    private volatile ConcurrentHashMap<String, T> extensions;
    private volatile T defaultExtension;

    private static ConcurrentHashMap<Class<?>, SpiLoader<?>> SPI_LOADERS = new ConcurrentHashMap<Class<?>, SpiLoader<?>>();
}
