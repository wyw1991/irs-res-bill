package com.dtzhejiang.irs.res.bill.common.dto;

import lombok.Data;

/**
 * oss签名
 *
 * @author jun
 * @company 数字浙江
 * @date 2022/5/5 22:31
 */
@Data
public class OssSignatureDTO {
    /**
     *  用户请求的AccessKey  ID
     */
    private String accessid;
    /**
     *用户表单上传的策略（Policy）
     */
    private String policy;
    /**
     * 对Policy签名后的字符串
     */
    private String signature;
    /**
     * 限制上传的文件前缀
     */
    private String dir;
    /**
     *用户发送上传请求的域名
     */
    private String host;
    /**
     * 由服务器端指定的Policy过期时间
     */
    private String expire;
    /**
     * 回调内容
     */
    private String callback;
}
