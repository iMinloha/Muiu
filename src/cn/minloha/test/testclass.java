package cn.minloha.test;

import cn.minloha.algorithm.Compere;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.Arrays;

public class testclass {
    public static void main(String[] args) {
        System.out.println(Compere.isHanZi("!".charAt(0)));
    }
}
