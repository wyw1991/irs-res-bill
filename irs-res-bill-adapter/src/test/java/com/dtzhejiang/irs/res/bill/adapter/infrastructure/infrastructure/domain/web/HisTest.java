package com.dtzhejiang.irs.res.bill.adapter.infrastructure.infrastructure.domain.web;

import com.dtzhejiang.irs.res.bill.app.service.IndexConfigService;
import com.dtzhejiang.irs.res.bill.common.util.JsonUtil;
import com.dtzhejiang.irs.res.bill.common.util.ObjUtil;
import com.dtzhejiang.irs.res.bill.domain.model.AppInfo;
import com.dtzhejiang.irs.res.bill.domain.model.HisIndices;
import com.dtzhejiang.irs.res.bill.domain.model.IndexConfig;
import com.dtzhejiang.irs.res.bill.infra.repository.AppInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@SpringBootTest
@Transactional
public class HisTest {

    @Autowired
    private AppInfoRepository appInfoRepository;
    @Autowired
    private IndexConfigService indexConfigService;
    @Test
    public void objTest() {
        AppInfo info=appInfoRepository.getById(1);
        List<IndexConfig> listConfig=indexConfigService.getList();
        listConfig.forEach(f->{
            HisIndices hisIndices=new HisIndices();
            String fileName=f.getIndexCode();
            Object obj=ObjUtil.getValue(info,fileName);
            hisIndices.setOperationIndices(f.getIndexName());
            hisIndices.setNormalValue(f.getNormalValue());

        });
        System.out.println(JsonUtil.toJsonString(listConfig));
    }


    @Test
    public void elTest(){
        //测试SpringEL解析器。
        //设置文字模板，其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
        String template = "#{#user<0}";
        //创建表达式解析器。
        ExpressionParser parser = new SpelExpressionParser();

        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("user", 1);

        //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
        Expression expression = parser.parseExpression(template, new TemplateParserContext());

        //使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文，第二个参数是类型参数，表示返回值的类型。
        System.out.println(expression.getValue(context, String.class));

    }
}
