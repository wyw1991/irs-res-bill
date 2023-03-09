package com.dtzhejiang.irs.res.bill.app.query.handler;

import com.dtzhejiang.irs.res.bill.app.dto.OperateLogDTO;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessLog;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 操作日志查询执行器
 */
@Component
public class ProcessLogsQueryHandler implements Function<String, MultiResponse<OperateLogDTO>> {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private ProcessGateway processGateway;

    @Override
    public MultiResponse<OperateLogDTO> apply(String processId) {
        UserInfo currentUser = userGateway.getCurrentUser();
        List<ProcessLog> processLogs = processGateway.listProcessLogs(processId, currentUser.getUserName());
        List<OperateLogDTO> logs = Optional.ofNullable(processLogs).orElse(new ArrayList<>()).stream().map(log -> OperateLogDTO.builder()
                .nickname(log.getUserName())
                .deptName(log.getDeptName())
                .opName(log.getOpName())
                .logVars(log.getBizInfo())
                .operateTime(log.getCreateTime())
                .build()).collect(Collectors.toList());
        return MultiResponse.of(logs);
    }
}
