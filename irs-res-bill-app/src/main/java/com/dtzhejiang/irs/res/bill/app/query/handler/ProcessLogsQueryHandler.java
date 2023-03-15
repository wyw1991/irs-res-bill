package com.dtzhejiang.irs.res.bill.app.query.handler;

import com.dtzhejiang.irs.res.bill.app.dto.OperateLogDTO;
import com.dtzhejiang.irs.res.bill.common.dto.MultiResponse;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.process.valueobject.ProcessLog;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.domain.user.valueobject.User;
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
public class ProcessLogsQueryHandler implements Function<String, MultiResponse<ProcessLog>> {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private ProcessGateway processGateway;

    @Override
    public MultiResponse<ProcessLog> apply(String processId) {
        User currentUser = userGateway.getCurrentUser();
        List<ProcessLog> processLogs = processGateway.listProcessLogs(processId, currentUser.getUserName());
        return MultiResponse.of(processLogs);
    }
}
