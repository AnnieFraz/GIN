package com.anniefraz.dissertation.test.runner.runner;

import org.apache.commons.lang3.ArrayUtils;
import org.mdkt.compiler.CompiledCode;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Intercept classloading of IsolatedTestRunner, and provide access to class from the target system
// Also allow overlaying of the modified classes.
public class CacheClassLoader extends URLClassLoader {

    protected Map<String, CompiledCode> customCompiledCode = new HashMap<>();

    public CacheClassLoader(URL[] classPaths) {
        super(addSystemClassPath(classPaths), null);
    }

    public CacheClassLoader(String classpath) {
        this(classPathToURLs(classpath));
    }


    // If the class can't be found using parents (I don't have any) then drops back here.
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        // I have to intervene here, to ensure IsolatedTestRunner uses me in the future.
        if (name.equals("com.anniefraz.dissertation.test.runner.runner.IsolatedTestRunner")) {
            return super.findClass(name);
        }

        // Modified class? Return the modified code.
        if (customCompiledCode.containsKey(name)) {
            CompiledCode cc = customCompiledCode.get(name);
            byte[] byteCode = cc.getByteCode();
            return defineClass(name, byteCode, 0, byteCode.length);
        }

        // Otherwise, try the system class loader. If not there, must be part of the project, so load ourselves.
        try {
            ClassLoader system = ClassLoader.getSystemClassLoader();
            Class fromSystemCall = system.loadClass(name);
            return fromSystemCall;
        } catch (ClassNotFoundException e) {
            return super.findClass(name);
        }

    }

    // Store the com.anniefraz.dissertation.experiments.results of compilation from the InMemoryCompiler
    public void setCustomCompiledCode(String className, CompiledCode compiledCode) {
        this.customCompiledCode.put(className, compiledCode);
    }

    // Utility method to convert a : separated classpath into an array of URLs
    private static final URL[] classPathToURLs(String classPath) {

        if (classPath == null) {
            return new URL[0];
        }

        String[] dirs = classPath.split(":");
        List<URL> urls = new ArrayList<>();

        for (String dir: dirs) {
            try {
                URL url = new File(dir).toURI().toURL();
                urls.add(url);
            } catch (MalformedURLException e) {
                Logger.error("Error converted classpath to URL, malformed: " + dir);
                Logger.error(e);
                System.exit(-1);
            }
        }

        URL[] urlArray = new URL[urls.size()];
        return urls.toArray(urlArray);

    }

    public static final URL[] addSystemClassPath(URL[] projectClasspath) {

        String classPath = System.getProperty("java.class.path");

        String[] paths = classPath.split(File.pathSeparator);

        URL[] urls = new URL[paths.length];
        int counter = 0;

        for (String path : paths){
            try {
                urls[counter] = new File(path).toURI().toURL();
                counter++;
            } catch (MalformedURLException e) {
                Logger.error("Malformed URL retrieved from system classpath.");
                Logger.error("Cannot initialise CacheClassLoader.");
                Logger.error("URL was: " + path);
                e.printStackTrace();
                System.exit(-1);
            }
        }

        return ArrayUtils.addAll(projectClasspath, urls);

    }

}
