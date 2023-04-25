package com.dtzhejiang.irs.res.bill.adapter.controller;

import com.dtzhejiang.common.entity.resp.JsonResult;
import com.dtzhejiang.irs.res.bill.app.query.qry.OssCallBackReq;
import com.dtzhejiang.irs.res.bill.app.response.FileResp;
import com.dtzhejiang.irs.res.bill.app.service.FileService;
import com.dtzhejiang.irs.res.bill.common.dto.OssSignatureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 文件上传下载服务
 *
 * @author jun
 * @company 数字浙江
 * @date 2021/10/21 14:22
 */
@RestController
@RequestMapping("/fileApi")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param: uploadFile
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public FileResp uploadFile(@RequestParam("file") MultipartFile uploadFile) {
        return fileService.uploadFile(uploadFile);
    }

    /**
     * 多文件上传
     *
     * @param uploadFiles
     */
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public FileResp uploadFiles(@RequestParam("files") MultipartFile[] uploadFiles) {
        return fileService.uploadFiles(uploadFiles);
    }

    /**
     * 文件下载
     *
     * @param: response，fileId
     * @return: void
     */
    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("fileId") String fileId) throws Exception {
        fileService.downloadFile(response, fileId);
    }

    /**
     * 获取oss签名
     */
    @RequestMapping(value = "/getSignature", method = RequestMethod.GET)
    public OssSignatureDTO getSignature() throws UnsupportedEncodingException {
        return fileService.getSignature();
    }

    /**
     * 回调校验上传成功
     */
    @RequestMapping(value = "/callBack", method = RequestMethod.POST)
    public FileResp callBack(@RequestHeader Map<String, String> headers, @RequestBody String ossCallBackStr) throws UnsupportedEncodingException {
        return fileService.callBack(headers, ossCallBackStr);
    }

    /**
     * 主动反馈上传成功
     */
    @RequestMapping(value = "/upload/success", method = RequestMethod.POST)
    public FileResp uploadSuccess(@RequestBody OssCallBackReq ossCallBackReq) throws UnsupportedEncodingException {
        return fileService.uploadSuccess(ossCallBackReq);
    }

    /**
     * 文件下载链接
     *
     * @param: response，fileId
     * @return: void
     */
    @RequestMapping(value = "/netDownloadUrl/{fileId}", method = RequestMethod.GET)
    public JsonResult<String> netDownloadUrl(@PathVariable("fileId") String fileId) throws Exception {
        return JsonResult.newSuccess(fileService.netDownloadUrl(fileId));
    }

    /**
     * 文件删除
     *
     * @return: void
     */
    @RequestMapping(value = "/deleteByFileId", method = RequestMethod.POST)
    public JsonResult delete(@RequestBody List<String> fileIds) throws Exception {
        return JsonResult.newSuccess(fileService.delete(fileIds));
    }
}
