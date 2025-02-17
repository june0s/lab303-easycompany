package com.easycompany.cmm.aop;

import com.easycompany.service.Employee;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.stereotype.Component;

@Component("aopLogging")
@Aspect
public class AopLogging {

    private final Log sysoutLogger = LogFactory.getLog(AopLogging.class);

    @Pointcut("execution(public * com.easycompany..*Impl.*(..))")
    public void targetMethod() {
        // pointcut annotation 값을 참조하기 위한 dummy method

    }

    @Before("targetMethod()")
    public void beforeMethod(JoinPoint joinPoint) {
        Class<?> aClass = joinPoint.getTarget().getClass();
        String method = joinPoint.getSignature().getName();

        StringBuffer buf = new StringBuffer();
        buf.append("+ " + method + "() before ==");

        Object[] args = joinPoint.getArgs();
        int argCount = 0;
        for (Object arg : args) {
            buf.append("\n- arg ");
            buf.append(argCount++);
            buf.append(" : ");

            if (arg != null) {
                if (arg instanceof String) {
                    buf.append((String) arg);
                }
                else if (arg instanceof Employee) {
                    buf.append(ToStringBuilder.reflectionToString(arg));
                }
            } else {
                buf.append("null");
            }
        }

        Log log = LogFactory.getLog(aClass);
        if (log.isDebugEnabled()) {
            log.debug(buf.toString());
        }
    }

    @Around("targetMethod()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        sysoutLogger.debug("2. aroundMethod");

        long time1 = System.currentTimeMillis();
        Object retVal = joinPoint.proceed();
        long time2 = System.currentTimeMillis();

        Class clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        StringBuffer buf = new StringBuffer();
        buf.append("+ " + methodName + "() end takes = " + (time2 - time1) + " ms ==");
        buf.append("\n");

        Log logger = LogFactory.getLog(clazz);
        logger.info(buf.toString());

        return retVal;
    }

    @After("targetMethod()")
    public void afterMethod(JoinPoint joinPoint) {

    }

    @AfterReturning(pointcut = "targetMethod()", returning = "retVal")
    public void afterReturningMethod(JoinPoint joinPoint, Object retVal) {
//        sysoutLogger.debug("4. afterReturningMethod");

    }

    @AfterThrowing(pointcut = "targetMethod()", throwing = "exception")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception exception) throws Exception {
//        sysoutLogger.debug("+ afterThrowingMethod");
        Class<?> aClass = joinPoint.getTarget().getClass();
        String method = joinPoint.getSignature().getName();
        StringBuffer buf = new StringBuffer();
        buf.append("+ " + method + "() Exception = " + exception.getMessage());
        Log log = LogFactory.getLog(aClass);
        if (log.isDebugEnabled()) {
            log.debug(buf.toString());
        }

        throw new EgovBizException("에러가 발생했습니다.", exception);
    }
}
