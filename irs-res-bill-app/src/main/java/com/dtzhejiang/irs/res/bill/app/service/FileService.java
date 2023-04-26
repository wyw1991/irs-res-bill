package com.dtzhejiang.irs.res.bill.app.service;

import com.dtzhejiang.irs.res.bill.app.query.qry.OssCallBackReq;
import com.dtzhejiang.irs.res.bill.app.response.FileResp;
import com.dtzhejiang.irs.res.bill.common.dto.OssSignatureDTO;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.domain.exception.BusinessException;
import com.dtzhejiang.irs.res.bill.domain.model.FileInfo;
import com.dtzhejiang.irs.res.bill.infra.mapper.FileMapper;
import com.dtzhejiang.irs.res.bill.infra.util.FileUtil;
import com.dtzhejiang.irs.res.bill.infra.util.OssClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 文件服务实现类
 *
 * @author jun
 */
@Service
@Slf4j
public class FileService {

    @Value("${file.useLocalUrl}")
    private boolean useLocalUrl;
    @Value("${file.download-request-path}")
    private String downloadRequestPath;
    @Value("${file.net-download-request-path}")
    private String netDownloadRequestPath;
    @Autowired
    private OssClientUtil clientUtil;
    @Autowired
    private FileMapper fileMapper;

    public FileResp uploadFile(MultipartFile uploadFile) {
        String originalFilename = uploadFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = handleOssFileName(originalFilename, uuid);
        Boolean success = clientUtil.putObject(fileName, uploadFile);
        if (Boolean.FALSE.equals(success)) {
            return FileResp.error("500", "上传文件异常");
        }
        String url = useLocalUrl ? downloadRequestPath + uuid : clientUtil.generatePresignedUrl(fileName);
        FileInfo fileInfo = buildDBEntity(url, originalFilename, uuid, uploadFile.getSize());
        return FileResp.success(insert(fileInfo));
    }

    public FileResp uploadFile(String originalFilename,InputStream inputStream,Long fileSize) {

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = handleOssFileName(originalFilename, uuid);
        Boolean success = clientUtil.putObject(fileName, inputStream,"application/pdf","utf-8");
        if (Boolean.FALSE.equals(success)) {
            return FileResp.error("500", "上传文件异常");
        }
        String url = useLocalUrl ? downloadRequestPath + uuid : clientUtil.generatePresignedUrl(fileName);
        FileInfo fileInfo = buildDBEntity(url, originalFilename, uuid, fileSize);
        return FileResp.success(insert(fileInfo));
    }

    private String handleOssFileName(String originalName, String uuid){
        log.debug("FileServiceImpl uploadFile originalFilename: {}", originalName);
        if(StringUtils.isBlank(originalName)){
            throw new BusinessException("400","上传文件名为空");
        }
        return uuid + originalName.substring(originalName.lastIndexOf("."));
    }

    public FileResp uploadFiles(MultipartFile[] uploadFiles) {
        Map<String, MultipartFile> uploads = new HashMap<>();
        Map<String, FileInfo> temp = new HashMap<>();
        for (MultipartFile uploadFile : uploadFiles) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = handleOssFileName(uploadFile.getOriginalFilename(), uuid);
            uploads.put(fileName, uploadFile);
            temp.put(fileName, buildDBEntity(null, uploadFile.getOriginalFilename(), uuid, uploadFile.getSize()));
        }
        Map<String, Boolean> map = clientUtil.putObjects(uploads);
        List<FileResp.FileResult> list = new ArrayList<>();
        map.forEach((k,v)->{
            if(Boolean.TRUE.equals(v)){
                FileInfo fileInfo = temp.get(k);
                String url = useLocalUrl ? downloadRequestPath + fileInfo.getFileId() : clientUtil.generatePresignedUrl(k);
                fileInfo.setFileUrl(url);
                list.add(insert(fileInfo));
            }
        });
        uploads.clear();
        temp.clear();
        return FileResp.success(list);
    }
    public FileResp.FileResult saveFile(String originalFilename, String uuid, String fileName, Long fileSize) {
        String url = useLocalUrl ? downloadRequestPath + uuid : clientUtil.generatePresignedUrl(fileName);
        return insert(url, originalFilename, uuid, fileSize);
    }


    private FileInfo buildDBEntity(String url, String originalFilename, String uuid, Long fileSize){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileUrl(url);
        fileInfo.setFileId(uuid);
        fileInfo.setFileName(originalFilename);
        if (fileSize != null) {
            fileInfo.setFileSize(FileUtil.checkFileSize(fileSize, "K"));
        }
        fileInfo.setCreateTime(new Date());
        return fileInfo;
    }

    public FileResp.FileResult insert(String url, String originalFilename, String uuid, Long fileSize){
        return insert(buildDBEntity(url, originalFilename, uuid, fileSize));
    }

    private FileResp.FileResult insert(FileInfo fileInfo){
        fileMapper.insert(fileInfo);
        FileResp.FileResult result = new FileResp.FileResult();
        result.setUrl(fileInfo.getFileUrl());
        result.setFileName(fileInfo.getFileName());
        result.setFileId(fileInfo.getFileId());
        return result;
    }


    public void downloadFile(HttpServletResponse response, String fileId) throws Exception {
        String originalFilename = fileMapper.selectFileName(fileId);
        if (originalFilename != null) {
            log.debug("FileServiceImpl.downloadFile.originalFilename:{}", originalFilename);
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndexOf);
            clientUtil.getObject(response, fileId + suffix, originalFilename);
        } else {
            throw new BusinessException("1000", "该文件id不存在!!!");
        }
    }

    public OssSignatureDTO getSignature() throws UnsupportedEncodingException {
        return clientUtil.getNetSignature();
    }

    public FileResp callBack(Map<String, String> headers, String ossCallBackStr) throws UnsupportedEncodingException {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        OssCallBackReq ossCallBackReq = JsonUtil.parseObject(ossCallBackStr, OssCallBackReq.class);
        String originalFilename = ossCallBackReq.getObject();
        log.debug("FileServiceImpl.uploadFile.originalFilename:{}", originalFilename);
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(lastIndexOf);
        String fileName = uuid + suffix;
        if (clientUtil.callBack(headers, ossCallBackStr, ossCallBackReq.getObject(), fileName)) {
            return FileResp.success(saveNetFile(originalFilename, uuid, fileName, ossCallBackReq.getSize()));
        }
        return FileResp.error("500", "上传文件异常,请重新上传");
    }

    public FileResp uploadSuccess(OssCallBackReq req) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String originalFilename = req.getObject();
        log.debug("FileServiceImpl.uploadFile.originalFilename:{}", originalFilename);
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(lastIndexOf);
        String fileName = uuid + suffix;
        if (clientUtil.netMoveTmpToFormal(originalFilename, fileName, true)) {
            return FileResp.success(saveNetFile(originalFilename, uuid, fileName, req.getSize()));
        }
        return FileResp.error("500", "上传文件异常,请重新上传");
    }

    private FileResp.FileResult saveNetFile(String originalFilename, String uuid, String fileName, Long fileSize) {
        String url = useLocalUrl ? netDownloadRequestPath + uuid : clientUtil.generatePresignedNetUrl(fileName, 1000L * 60 * 10);
        return insert(url, originalFilename, uuid, fileSize);
    }

    public String netDownloadUrl(String fileId) throws Exception {
        return netDownloadUrl(fileId, 1000L * 60 * 10);
    }

    public String netDownloadUrl(String fileId, long ttl) throws Exception {
        String originalFilename = fileMapper.selectFileName(fileId);
        if (originalFilename != null) {
            log.debug("FileServiceImpl.downloadFile.originalFilename:{}", originalFilename);
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndexOf);
            String fileName = fileId + suffix;
            String newFilename = originalFilename.substring(0, lastIndexOf) + "_" + fileName;
            if (clientUtil.netMoveFormalToTmp(fileName, newFilename, false)) {
                return clientUtil.generatePresignedNetUrl(newFilename, ttl);
            }
        } else {
            throw new BusinessException("1000", "该文件id不存在!!!");
        }
        return null;
    }

    public List<Boolean> delete(List<String> fileIds) {
        List<Boolean> list = new ArrayList<>();
        fileIds.stream().forEach(fileId -> {
            String originalFilename = fileMapper.selectFileName(fileId);
            if (originalFilename != null) {
                log.debug("FileServiceImpl.downloadFile.originalFilename:{}", originalFilename);
                int lastIndexOf = originalFilename.lastIndexOf(".");
                String suffix = originalFilename.substring(lastIndexOf);
                String fileName = fileId + suffix;
                list.add(clientUtil.deleteNetOssObjectByName(fileName) && fileMapper.del(fileId));
            } else {
                list.add(true);
                log.info("该文件id:[" + fileId + "]不存在!!!");
            }
        });
        return list;
    }

}
