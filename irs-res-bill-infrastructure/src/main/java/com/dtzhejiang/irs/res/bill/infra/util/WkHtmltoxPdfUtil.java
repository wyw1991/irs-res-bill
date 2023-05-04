package com.dtzhejiang.irs.res.bill.infra.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
public class WkHtmltoxPdfUtil {
    //空格
    private static final String space = " ";

    //文件前缀
    private static final String PREFIX = "tempFile";

    //文件后缀-html
    private static final String SUFIX_HTML = ".html";
    //文件后缀pdf
    private static final String SUFIX_PDF = ".pdf";

    private static final Random RANDOM = new Random(100);

    private static final Integer PAGE_HEIGHT = 274;

    private static final Integer PAGE_WIDTH = 207;



    public static byte[] getPdfByte(MultipartFile mfile) throws IOException {
        return doHtml2pdf(readFileContent(mfile), PAGE_HEIGHT, PAGE_WIDTH).toByteArray();
    }


    private static String readFileContent(MultipartFile mfile) throws IOException {
        File file=getFile(mfile);
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
    private static File getFile(MultipartFile mfile) throws IOException {
        File file = new File(mfile.getOriginalFilename());
        InputStream inputStream=mfile.getInputStream();
        try {
            //创建一个临时文件
            File tempFile = File.createTempFile("temp",".tmp");
            //将Inputstream中的数据写入到临时文件中
            OutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer,0, length) ;
            }
            //关闭输入流和输出流
            inputStream.close();
            outputStream.close () ;
            //将临时文件转换为Eile对象
            file = new File(tempFile.getAbsolutePath());
            System.out.println("File: "+ file.getAbsolutePath());
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }


    private static ByteArrayOutputStream doHtml2pdf(String html, Integer pageHeight, Integer pageWidth) {
        String wkhtmltopdf = findExecutable();
        //将内存中的html文件存储到一个临时地方
        File htmlTempFile = createFile(PREFIX, SUFIX_HTML);
        FileUtil.writeString(html, htmlTempFile, CharsetUtil.UTF_8);

        //wk转换pdf之后的pdf存储文件地址
        File wkpdfDestTempFile = createFile(PREFIX, SUFIX_PDF);

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

        String finalCmd = buildCmdParam(srcAbsolutePath, destAbsolutePath);

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

    public static File createFile(String prefix, String sufix) {
        File file = null;
        File fileDir = new File("");
        try {
            file = File.createTempFile(prefix, sufix, fileDir);
        } catch (IOException e) {
            log.info("创建文件失败：", e.getCause());
        }
        return file;
    }

    private static String buildCmdParam(String srcAbsolutePath, String destAbsolutePath) {
        StringBuilder cmd = new StringBuilder();
        cmd.append(findExecutable()).append(space)
                .append("--margin-left").append(space)
                .append("8").append(space)
                .append("--margin-right").append(space)
                .append("8").append(space)
                .append("--margin-top").append(space)
                .append("10").append(space)
                .append("--margin-bottom").append(space)
                .append("2").append(space)
                .append("--page-height").append(space)
                .append(273.5).append(space)
                .append("--page-width").append(space)
                .append(207).append(space)

                .append(srcAbsolutePath).append(space)
                //.append("--footer-center").append(space)
                //.append("[page]").append(space)
                .append("--footer-font-size").append(space)
                .append("14").append(space)


                //.append("--disable-smart-shrinking").append(space)
                //.append("--load-media-error-handling").append(space)
                //.append("ignore").append(space)
                //.append("--load-error-handling").append(space)
                //.append("ignore").append(space)
                //.append("--footer-right").append(space)
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