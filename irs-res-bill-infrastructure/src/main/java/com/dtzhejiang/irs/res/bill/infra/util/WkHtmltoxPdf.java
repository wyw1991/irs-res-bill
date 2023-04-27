package com.dtzhejiang.irs.res.bill.infra.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
public class WkHtmltoxPdf {
    //空格
    private static final String space = " ";

    //文件前缀
    private static final String PREFIX = "tempFile";

    //文件后缀-html
    private static final String SUFIX_HTML = ".html";
    //文件后缀pdf
    private static final String SUFIX_PDF = ".pdf";

    private static final Random RANDOM = new Random(100);

    private static String FILEDIR_PATH = "C:\\Users\\Dell\\Desktop\\pdf\\";

    private static final Integer PAGE_HEIGHT = 500;

    private static final Integer PAGE_WIDTH = 150;

    public static void main(String[] args) throws IOException {
        toPdf(getHtml(), PAGE_HEIGHT, PAGE_WIDTH);
    }

    public static void toPdf(String html, Integer pageHeight, Integer pageWidth) {
        byte[] bytes = html2pdf(html, pageHeight, pageWidth).toByteArray();
        storagePdf(bytes, FILEDIR_PATH, RANDOM.nextInt() + SUFIX_PDF);
    }

    private static String getHtml() throws IOException {
        return readFileContent(FILEDIR_PATH+"test2.html");

    }
    private static String readFileContent(String fileName) throws IOException {

        File file = new File(fileName);

        BufferedReader bf = new BufferedReader(new FileReader(file));

        String content = "";
        StringBuilder sb = new StringBuilder();

        while(content != null){
            content = bf.readLine();

            if(content == null){
                break;
            }

            sb.append(content.trim());
        }

        bf.close();
        return sb.toString();
    }


    /**
     * 存储pdf文件
     *
     * @param bfile    pdf字节流
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    public static void storagePdf(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if ((!dir.exists()) && (dir.isDirectory())) {
                boolean mkdirs = dir.mkdirs();
            }
            file = new File(filePath + "/" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 将传入的页面转换成pdf，返回pdf字节数组
     * 默认自动随机生成文件名称
     *
     * @param html html页面信息
     * @return byte[] pdf字节流
     */
    public static ByteArrayOutputStream html2pdf(String html, Integer pageHeight, Integer pageWidth) {
        String fileName = System.currentTimeMillis() + RANDOM.nextInt() + "";
        String dest = FILEDIR_PATH;
        return doHtml2pdf(html, dest, pageHeight, pageWidth);
    }

    private static ByteArrayOutputStream doHtml2pdf(String html, String dest, Integer pageHeight, Integer pageWidth) {
        String wkhtmltopdf = findExecutable();
        //将内存中的html文件存储到一个临时地方
        File htmlTempFile = createFile(PREFIX, SUFIX_HTML, dest);
        FileUtil.writeString(html, htmlTempFile, CharsetUtil.UTF_8);

        //wk转换pdf之后的pdf存储文件地址
        File wkpdfDestTempFile = createFile(PREFIX, SUFIX_PDF, dest);

        if (StrUtil.isBlank(wkhtmltopdf)) {
            log.info("no wkhtmltopdf found!");
            throw new RuntimeException("html转换pdf出错了,未找到wkHtml工具");
        }

        String srcAbsolutePath = htmlTempFile.getAbsolutePath();
        String destAbsolutePath = wkpdfDestTempFile.getAbsolutePath();

        File parent = wkpdfDestTempFile.getParentFile();
        if (!parent.exists()) {
            boolean dirsCreation = parent.mkdirs();
            log.info("create dir for new file,{}", dirsCreation);
        }

        String finalCmd = buildCmdParam(srcAbsolutePath, destAbsolutePath, pageHeight, pageWidth);

        return doProcess(finalCmd, htmlTempFile, wkpdfDestTempFile);

    }

    /**
     * 执行wkHtmltox命令，读取生成的的pdf文件，输出执行结果，最后删除由于执行wk命令生成的零时的pdf文件和html文件
     *
     * @param finalCmd          cmd命令
     * @param htmlTempFile      html零时文件
     * @param wkpdfDestTempFile 生成的pdf文件
     * @return byte[] pdf字节流
     */
    private static ByteArrayOutputStream doProcess(String finalCmd, File htmlTempFile, File wkpdfDestTempFile) {
        InputStream is = null;
        try {
            Process proc = Runtime.getRuntime().exec(finalCmd);
            new Thread(new ProcessStreamHandler(proc.getInputStream())).start();
            new Thread(new ProcessStreamHandler(proc.getErrorStream())).start();

            proc.waitFor();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = new FileInputStream(wkpdfDestTempFile);
            byte[] buf = new byte[1024];

            while (is.read(buf, 0, buf.length) != -1) {
                baos.write(buf, 0, buf.length);
            }

            return baos;
        } catch (IOException | InterruptedException e) {
            log.error("html转换pdf出错", e);
            throw new RuntimeException("html转换pdf出错了");
        } finally {
            if (htmlTempFile != null) {
                boolean delete = htmlTempFile.delete();
            }
            if (wkpdfDestTempFile != null) {
                boolean delete = wkpdfDestTempFile.delete();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File createFile(String prefix, String sufix, String fileDirPath) {
        File file = null;
        File fileDir = new File(fileDirPath);
        try {
            file = File.createTempFile(prefix, sufix, fileDir);
        } catch (IOException e) {
            log.info("创建文件失败：", e.getCause());
        }
        return file;
    }

    private static String buildCmdParam(String srcAbsolutePath, String destAbsolutePath, Integer pageHeight, Integer pageWidth) {
        StringBuilder cmd = new StringBuilder();
        cmd.append(findExecutable()).append(space)
                .append("--margin-left").append(space)
                .append("0").append(space)
                .append("--margin-right").append(space)
                .append("0").append(space)
                .append("--margin-top").append(space)
                .append("0").append(space)
                .append("--margin-bottom").append(space)
                .append("0").append(space)
                .append("--page-height").append(space)
                .append(pageHeight).append(space)
                .append("--page-width").append(space)
                .append(pageWidth).append(space)

                .append(srcAbsolutePath).append(space)
//                .append("--footer-center").append(space)
//                .append("[page]").append(space)
//                .append("--footer-font-size").append(space)
//                .append("14").append(space)
//
//                .append("--disable-smart-shrinking").append(space)
//                .append("--load-media-error-handling").append(space)
//                .append("ignore").append(space)
//                .append("--load-error-handling").append(space)
//                .append("ignore").append(space)
//                .append("--footer-right").append(space)
//                .append("WanG提供技术支持").append(space)
                .append(destAbsolutePath);
        return cmd.toString();
    }

    /**
     * 获取当前系统的可执行命令
     *
     * @return
     */
    public static String findExecutable() {
        Process p;
        try {
            String osname = System.getProperty("os.name").toLowerCase();
            String cmd = osname.contains("windows") ? "where wkhtmltopdf" : "which wkhtmltopdf";
            p = Runtime.getRuntime().exec(cmd);
            new Thread(new ProcessStreamHandler(p.getErrorStream())).start();
            p.waitFor();
            return IOUtils.toString(p.getInputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            log.info("根据当前系统返回 wkhtmltopdf 执行命令失败，IO异常：", e);
        } catch (InterruptedException e) {
            log.info("根据当前系统返回 wkhtmltopdf 执行命令失败，中断异常：", e);
        }
        return "";
    }

    private static class ProcessStreamHandler implements Runnable {
        private InputStream is;

        public ProcessStreamHandler(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            try {
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                reader = new BufferedReader(isr);
                String line;
                while ((line = reader.readLine()) != null) {
                    log.debug("---++++++++++--->" + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}