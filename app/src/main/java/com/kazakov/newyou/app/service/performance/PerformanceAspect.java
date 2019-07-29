package com.kazakov.newyou.app.service.performance;

import com.kazakov.newyou.app.service.log.LogMessageService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class PerformanceAspect {

    private final LogMessageService logMessageService = new LogMessageService();

    private static final String TIMED_METHOD_POINTCUT =
            "execution(@com.kazakov.newyou.app.service.performance.annotation.Time * *(..))";

    @Pointcut(TIMED_METHOD_POINTCUT)
    public void methodAnnotatedWithTimed() {
    }

    @Around("methodAnnotatedWithTimed()")
    public Object performancejoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        String groupId = logMessageService.logTimedStart(getMessageName(joinPoint));
        Object result = joinPoint.proceed();
        logMessageService.logTimedStop(groupId, getMessageName(joinPoint));
        return result;
    }

    private static String getMessageName(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        return className + methodName;
    }
}
