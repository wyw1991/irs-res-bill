package com.dtzhejiang.irs.res.bill.infra.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.dtzhejiang.irs.res.bill.common.dto.OssSignatureDTO;
import com.dtzhejiang.irs.res.bill.common.enums.ContentTypeEnum;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里云oss工具类
 *
 * @author jun
 */
@Component
@Slf4j
public class OssClientUtil {

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;
    @Value("${aliyun.oss.file-folder}")
    private String fileFolder;
    @Value("${aliyun.net-oss.access-key-id}")
    private String netAccessKeyId;
    @Value("${aliyun.net-oss.access-key-secret}")
    private String netAccessKeySecret;
    @Value("${aliyun.net-oss.endpoint}")
    private String netEndpoint;
    @Value("${aliyun.net-oss.pubKey}")
    private String netPubKey;
    @Value("${aliyun.net-oss.file-folder}")
    private String netFileFolder;
    @Value("${aliyun.net-oss.bucket-name}")
    private String netBucketName;
    @Value("${aliyun.net-oss.callback-url}")
    private String netCallbackUrl;
    @Value("${aliyun.net-oss.tmp-dir}")
    private String netTmpDir;

    private OSS ossNetClient;

    public OSS getClient() {
        // 创建OSSClient实例。
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    public OSS getNetClient() {
        if (ossNetClient == null) {
            // 创建公有云OSSClient实例。
            ossNetClient = new OSSClientBuilder().build(netEndpoint, netAccessKeyId, netAccessKeySecret);
        }
        return ossNetClient;
    }

    public Map<String, Boolean> putObjects(Map<String, MultipartFile> uploadFiles) {
        OSS client = getClient();
        Map<String, Boolean> result = new HashMap<>();
        uploadFiles.forEach((key, value) -> result.put(key, putObject(client, key, value)));
        if (client != null) {
            client.shutdown();
        }
        return result;
    }

    public Boolean putObject(OSS ossClient, String fileName, MultipartFile uploadFile) {
        try (InputStream data = uploadFile.getInputStream()){
            return putObject(ossClient, fileName, uploadFile.getInputStream(), uploadFile.getContentType(), FileUtil.getFileCharset(data));
        } catch (Exception e) {
            log.error("操作流失败 {}", ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    public Boolean putObject(String fileName, MultipartFile uploadFile) {
        try (InputStream data = uploadFile.getInputStream()){
            return putObject(fileName, uploadFile.getInputStream(), uploadFile.getContentType(), FileUtil.getFileCharset(data));
        } catch (Exception e) {
            log.error("操作流失败 {}", ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    public Boolean putObject(String fileName, InputStream data, String contentType, String charset) {
        OSS client = getClient();
        try {
            return putObject(client, fileName, data, contentType, charset);
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }

    public Boolean putObject(OSS ossClient, String fileName, InputStream data, String contentType, String charset) {
        // 创建上传Object的Metadata
        ObjectMetadata meta = new ObjectMetadata();
        try {
            // 设置ContentLength ContentType
            meta.setContentLength(data.available());
            String charsets = StandardCharsets.UTF_8.displayName();
            if (StringUtils.isNotBlank(charset)) {
                charsets = charset;
            }
            if (StringUtils.isNotBlank(contentType)) {
                meta.setContentType(contentType + ";charset=" + charsets);
            } else {
                String suffix = FileUtil.getFileSuffix(fileName);
                ContentTypeEnum typeEnum = ContentTypeEnum.getEnumBySuffix(suffix);
                if (typeEnum != null) {
                    meta.setContentType(typeEnum.getContentType() + ";charset=" + charsets);
                }
            }
            // 上传Object
            log.debug("开始上传文件到oos,文件名：" + fileName);
            ossClient.putObject(bucketName, fileFolder + fileName, data, meta);
        } catch (IOException e) {
            log.error("上传文件异常: {}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }

    public OSSObject getOssObjectByName(OSS ossClient, String fileName) {
        return ossClient.getObject(bucketName, fileFolder + fileName);
    }

    public void getObject(HttpServletResponse response, String fileName, String originalFilename) {
        OSS ossClient = getClient();
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            String nameEncode = URLEncoder.encode(originalFilename, "UTF-8");
            response.setHeader("Content-Disposition", String.format("inline;filename=%s;filename*=utf-8''%s", nameEncode, nameEncode));
            log.debug("开始下载文件,文件名：" + fileName);
            OSSObject object = ossClient.getObject(bucketName, fileFolder + fileName);
            String contentType = object.getObjectMetadata().getContentType();
            if (contentType.indexOf(";charset=") < 0) {
                response.setContentType(contentType + ";charset=utf-8");
            } else {
                response.setContentType(contentType);
            }
            in = object.getObjectContent();
            out = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
            object.close();
        } catch (IOException e) {
            log.error("文件下载异常", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            } catch (IOException e) {
                log.error("资源释放出现异常", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public String generatePresignedUrl(String fileName) {
        OSS ossClient = getClient();
        // 设置URL过期时间为7天。
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 7);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, fileFolder + fileName, expiration);
        if (ossClient != null) {
            ossClient.shutdown();
        }
        return url != null ? url.toString() : null;
    }

    public String generatePresignedNetUrl(String fileName, Long expireTime) {
        OSS ossClient = getNetClient();
        // 设置URL过期时间
        Date expiration = new Date(System.currentTimeMillis() + expireTime);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(netBucketName, netTmpDir + fileName, expiration);
        return url != null ? url.toString() : null;
    }

    public boolean callBack(Map<String, String> headers, String ossCallBackStr, String oldFileName, String newFileName) throws UnsupportedEncodingException {
        //公钥地址确认
        String pubKeyInput = headers.get("x-oss-pub-key-url");
        byte[] pubKeyByte = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKeyByte);
        if (!pubKeyAddr.startsWith(netEndpoint)) {
            log.error("pub key addr must be oss addrss");
            return false;
        }
        //校验认证：rsa_verify(public_key, md5(url_decode(path) + query_string + ‘\n’ + body), base64_decode(authorization))
        String authStr = netCallbackUrl;
        authStr += "\n" + ossCallBackStr;
        String authorizationHeader = headers.get("Authorization");
        byte[] authorization = BinaryUtil.fromBase64String(authorizationHeader);
        boolean ret = doCheck(authStr, authorization, netPubKey);
        if (ret) {
            //校验成功后 从临时目录转移并新命名
            ret = netMove(netTmpDir + oldFileName, netFileFolder + newFileName, true);
        }
        return ret;
    }

    private boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public OssSignatureDTO getNetSignature() throws UnsupportedEncodingException {
        OSS ossClient = getNetClient();
        //30秒过期
        long expireEndTime = System.currentTimeMillis() + 30L * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, netTmpDir);

        Map<String, String> callback = new HashMap<>();
        callback.put("callbackUrl", netCallbackUrl);
        callback.put("callbackBody",
                "{\"bucket\":${bucket},\"filename\":${object},\"size\":${size},\"mimeType\":${mimeType}}");
        callback.put("callbackBodyType", "application/json");
        String base64CallbackBody = BinaryUtil.toBase64String(JsonUtil.toJsonString(callback).getBytes());

        policyConditions.addConditionItem("callback", base64CallbackBody);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        OssSignatureDTO signatureDTO = new OssSignatureDTO();
        signatureDTO.setAccessid(netAccessKeyId);
        signatureDTO.setPolicy(encodedPolicy);
        signatureDTO.setSignature(postSignature);
        signatureDTO.setDir(netTmpDir);
        signatureDTO.setHost(netEndpoint + netBucketName + "/");
        signatureDTO.setExpire(String.valueOf(expireEndTime / 1000));
        signatureDTO.setCallback(base64CallbackBody);
        return signatureDTO;
    }

    /**
     * oss文件移动
     *
     * @param oldPathFile 源路径文件名
     * @param newPathFile 目标的文件名
     * @param deleteFlag  是否删除源文件
     * @return
     */
    private boolean netMove(String oldPathFile, String newPathFile, boolean deleteFlag) {
        OSS ossClient = getNetClient();
        if (ossClient.doesObjectExist(netBucketName, oldPathFile)) {
            CopyObjectRequest objectRequest = new CopyObjectRequest(netBucketName, oldPathFile, netBucketName, newPathFile);
            if(deleteFlag) {
                ObjectMetadata meta = ossClient.getObjectMetadata(netBucketName, oldPathFile);
                OSSObject ossObject = ossClient.getObject(netBucketName, oldPathFile);
                String charSet = FileUtil.getFileCharset(ossObject.getObjectContent());
                // 设置ContentLength ContentType
                String contentType = meta.getContentType();
                if (StringUtils.isNotBlank(contentType) && contentType.indexOf("charset=") < 0) {
                    meta.setContentType(contentType + ";charset=" + charSet);
                } else {
                    String suffix = FileUtil.getFileSuffix(oldPathFile);
                    ContentTypeEnum typeEnum = ContentTypeEnum.getEnumBySuffix(suffix);
                    if (typeEnum != null) {
                        meta.setContentType(typeEnum.getContentType() + ";charset=" + charSet);
                    }
                }
                objectRequest.setNewObjectMetadata(meta);
                try {
                    ossObject.getObjectContent().close();
                } catch (IOException e) {
                    log.error("关闭ossObject异常：{}", ExceptionUtils.getStackTrace(e));
                }
            }
            CopyObjectResult result = ossClient.copyObject(objectRequest);
            if (deleteFlag) {
                ossClient.deleteObject(netBucketName, oldPathFile);
            }
            return true;
        }
        log.info("文件不存在:" + oldPathFile);
        return false;
    }

    /**
     * oss文件移动 临时->正式
     */
    public boolean netMoveTmpToFormal(String oldFileName, String newFileName, boolean deleteFlag) {
        return netMove(netTmpDir + oldFileName, netFileFolder + newFileName, deleteFlag);
    }

    /**
     * oss文件移动 正式->临时
     */
    public boolean netMoveFormalToTmp(String oldFileName, String newFileName, boolean deleteFlag) {
        return netMove(netFileFolder + oldFileName, netTmpDir + newFileName, deleteFlag);
    }

    public boolean deleteOssObjectByName(String fileName) {
        OSS ossClient = getClient();
        ossClient.deleteObject(bucketName, fileFolder + fileName);
        return !ossClient.doesObjectExist(bucketName, fileFolder + fileName);
    }

    public boolean deleteNetOssObjectByName(String fileName) {
        OSS ossClient = getNetClient();
        ossClient.deleteObject(netBucketName, netFileFolder + fileName);
        return !ossClient.doesObjectExist(netBucketName, netFileFolder + fileName);
    }
}
