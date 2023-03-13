package com.dtzhejiang.irs.res.bill.app.command.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dtzhejiang.irs.res.bill.app.command.cmd.FinalCompleteReportCmd;
import com.dtzhejiang.irs.res.bill.common.dto.Response;
import com.dtzhejiang.irs.res.bill.domain.model.SubReport;
import com.dtzhejiang.irs.res.bill.domain.process.gateway.ProcessGateway;
import com.dtzhejiang.irs.res.bill.domain.user.gateway.UserGateway;
import com.dtzhejiang.irs.res.bill.infra.repository.ReportRepository;
import com.dtzhejiang.irs.res.bill.infra.repository.SubReportRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinalCompleteCmdHandler {

    @Autowired
    private ProcessGateway processGateway;
    @Autowired
    private UserGateway userGateway;
    @Autowired
    private ReportRepository repository;
    @Autowired
    private SubReportRepository subReportRepository;

    public Response apply(FinalCompleteReportCmd cmd) {
        LambdaQueryWrapper<SubReport> query = new LambdaQueryWrapper<>();
        query.eq(SubReport::getReportId, cmd.getReportId());
        List<SubReport> list = subReportRepository.list(query);
        Collection<Long> backOffIds = cmd.getSubReportIds();
        // 退回处理
        if(CollectionUtils.isNotEmpty(backOffIds)){
            Collection<Long> allIds = list.stream().map(SubReport::getId).collect(Collectors.toList());
            Collection<Long> agreeIds = CollectionUtils.subtract(allIds, backOffIds);
            // TODO: 2023/3/13 call workflow

        }
        // 全部通过处理
        // TODO: 2023/3/13 call workflow

        // TODO: 2023/3/13 业务处理
        return Response.buildSuccess();
    }
}
