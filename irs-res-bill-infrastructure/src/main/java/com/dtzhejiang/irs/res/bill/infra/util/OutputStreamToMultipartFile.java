package com.dtzhejiang.irs.res.bill.infra.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StreamUtils;
import java.io.*;
import java.nio.file.Files;

public class OutputStreamToMultipartFile implements MultipartFile {

    private final String name;
    private final String originalFileName;
    private final String contentType;
    private final byte[] content;

    public OutputStreamToMultipartFile(String name, String originalFileName, String contentType, OutputStream outputStream) throws IOException {
        this.name = name;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.content = ((ByteArrayOutputStream) outputStream).toByteArray();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        Files.write(file.toPath(), content);
    }
}