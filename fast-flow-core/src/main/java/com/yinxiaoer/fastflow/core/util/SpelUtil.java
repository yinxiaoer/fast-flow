package com.yinxiaoer.fastflow.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * spel工具类
 *
 * @author yinxiuhe
 * @date 2021/11/19 16:29
 */
@Slf4j
public class SpelUtil {

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    /**
     * 解析表达式是否成立
     *
     * @param condition 条件表达式
     * @param vars      变量
     * @return 条件是否成立
     */
    public static Boolean parse(String condition, Map<String, Object> vars) {
        try {
            StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
            standardEvaluationContext.setVariables(vars);
            Expression expression = PARSER.parseExpression(condition);
            return expression.getValue(standardEvaluationContext, Boolean.class);
        } catch (ParseException e) {
            log.info("解析{}表达式错误：{}", condition, e.getMessage(), e);
            return false;
        } catch (EvaluationException e) {
            log.info("获取{}表达式结果错误：{}", condition, e.getMessage(), e);
            return false;
        }
    }

}
