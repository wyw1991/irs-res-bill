package com.dtzhejiang.irs.res.bill.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dtzhejiang.irs.res.bill.domain.model.FileInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMapper extends BaseMapper<FileInfo> {

    String selectFileName(String fileId);

    List<FileInfo> selectFiles(List<String> fileIds);

    int insert(FileInfo fileInfo);

    boolean del(String fileId);
}
