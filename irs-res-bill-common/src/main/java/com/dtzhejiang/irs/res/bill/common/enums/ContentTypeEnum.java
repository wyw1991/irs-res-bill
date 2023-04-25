package com.dtzhejiang.irs.res.bill.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ContentTypeEnum {
    //jpeg、jpg、png、gif、mp4、avi、pdf、txt、word、ppt、excel、zip、rar
    JPEG("jpeg", "image/jpeg"),
    JPG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    GIF("gif", "image/gif"),
    MP4("mp4", "video/mpeg4"),
    AVI("avi", "video/avi"),
    PDF("pdf", "application/pdf"),
    TXT("txt", "text/plain"),
    DOC("doc","application/msword"),
    DOCX("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    PPT("ppt","application/vnd.ms-powerpoint"),
    PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    XLS("xls","application/vnd.ms-excel"),
    XLSX("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    ZIP("zip","application/x-zip-compressed"),
    RAR("rar","application/octet-stream");

    private String suffix;
    private String contentType;

    public static ContentTypeEnum getEnumBySuffix(String suffix){
        for (ContentTypeEnum typeEnum : ContentTypeEnum.values()){
            if(typeEnum.getSuffix().equals(suffix)){
                return typeEnum;
            }
        }
        return null;
    }
}
