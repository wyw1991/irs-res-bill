package com.dtzhejiang.irs.res.bill.app.query.qry;

import lombok.Data;

/**
 * oss回调请求
 * @author jun
 * @company 数字浙江
 * @date 2022/5/6 23:13
 */
@Data
public class OssCallBackReq {
    /**
     * 存储空间
     */
    private String bucket;
    /**
     * 对象（文件）
     */
    private String object;
    /**
     * 文件的ETag，即返回给用户的ETag字段
     */
    private String etag;
    /**
     * Object大小，CompleteMultipartUpload时为整个Object的大小
     */
    private long size;
    /**
     * 资源类型，例如jpeg图片的资源类型为image/jpeg
     */
    private String mimeType;
}
