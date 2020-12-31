package tech.soulcoder.signature.log;

import java.util.Collection;

import javax.servlet.ServletRequest;

import com.alibaba.fastjson.JSON;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yunfeng.lu
 * @create 2019-06-25.
 */
@Component
@Aspect
public class AopLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(AopLogAspect.class);

    @Around("@annotation(logAop)")
    public Object printLog(ProceedingJoinPoint pjp, AopLog logAop) throws Throwable {
        Object result;
        AopLog.ParamPrintOption printOption = logAop.outParamPrint();
        long currentTime = System.currentTimeMillis();
        StringBuilder inParam = new StringBuilder();
        if (pjp.getArgs() != null && pjp.getArgs().length > 0) {
            for (Object arg : pjp.getArgs()) {
                if (arg instanceof ServletRequest) {
                    // do nothing
                } else {
                    inParam.append(JSON.toJSONString(arg)).append("|");
                }
            }
        }
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        try {
            result = pjp.proceed();
            // 未配置情况 系统默认不打印 集合类型，其余都打印
            if (AopLog.ParamPrintOption.UNCONFIG.equals(printOption)) {
                if (result instanceof Collection) {
                    printOption = AopLog.ParamPrintOption.IGNORE;
                } else if (result != null && result.getClass().isArray()) {
                    printOption = AopLog.ParamPrintOption.IGNORE;
                } else {
                    printOption = AopLog.ParamPrintOption.PRINT;
                }
            }
            long lastTime = System.currentTimeMillis() - currentTime;
            logger.info("{}.{}: 入参: {} \t出参: {} 响应时间:{}毫秒", className,
                methodName,
                inParam.toString(),
                printOption.equals(AopLog.ParamPrintOption.PRINT) ? JSON.toJSONString(result) : "参数未配置打印", lastTime);

        } catch (Throwable throwable) {
            logger.error("{}.{}: 执行报错,入参: {}", className, methodName, inParam.toString(), throwable);
            throw throwable;
        }
        return result;
    }

}
