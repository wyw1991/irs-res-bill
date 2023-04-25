package com.dtzhejiang.irs.res.bill.domain.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author jun
 */
@Getter
@Setter
@TableName(value = "file_info",autoResultMap = true)
public class FileInfo {

    private Integer id;

    private String fileId;

    private String fileName;

    private String fileUrl;

    private Double fileSize;

    private Date createTime;

}