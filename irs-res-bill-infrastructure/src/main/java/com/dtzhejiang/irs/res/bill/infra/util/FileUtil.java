package com.dtzhejiang.irs.res.bill.infra.util;



import info.monitorenter.cpdetector.io.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 文件工具类
 *
 * @author jun
 */
public class FileUtil {
    private static CodepageDetectorProxy codepageDetector = null;

    static {
        codepageDetector = CodepageDetectorProxy.getInstance();
        //UnicodeDetector用于Unicode家族编码的测定
        codepageDetector.add(UnicodeDetector.getInstance());
        //ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于指示是否显示探测过程的详细信息，为false不显示。
        codepageDetector.add(new ParsingDetector(false));
        //JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码 测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
        codepageDetector.add(JChardetFacade.getInstance());
        //ASCIIDetector用于ASCII编码测定
        codepageDetector.add(ASCIIDetector.getInstance());
    }

    /**
     * 判断文件大小
     *
     * @param len  文件长度
     * @param unit 限制单位（B,K,M,G）
     * @return boolean
     */
    public static double checkFileSize(Long len, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        return fileSize;
    }

    public static String getFileCharset(InputStream inputStream) {
        try{
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            Charset charset = codepageDetector.detectCodepage(bufferedInputStream, bufferedInputStream.available());
            return charset.name();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "utf-8";
    }


    public static String getFileSuffix(String fileName) {
        return cn.hutool.core.io.FileUtil.getSuffix(fileName);
    }
}
