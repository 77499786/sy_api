package com.forest.core;

import com.forest.project.model.FrameLog;
import com.forest.project.service.FrameLogService;
import com.forest.utils.JWTAuthenticationUtil;
import com.forest.utils.ScsyReportUtil;
import com.google.common.base.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 */
@Component
@Aspect
public class WebControllerAop {

    @Value("${scsy.logindb}")
    private  Boolean loggerflg;

    @Resource
    FrameLogService frameLogService;

    //匹配com.forest.*.web.controller包及其子包下的所有类的所有方法
    @Pointcut("execution(* com.forest..*.web..*.*(..))")
    public void executeService() {

    }

    /**
     * 前置通知，方法调用前被调用
     *
     * @param joinPoint
     */
    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
//        System.out.println("我是前置通知!!!");
//        //获取目标方法的参数信息
//        Object[] obj = joinPoint.getArgs();
//        //AOP代理类的信息
//        joinPoint.getThis();
//        //代理的目标对象
//        joinPoint.getTarget();
//        //用的最多 通知的签名
//        Signature signature = joinPoint.getSignature();
//        //代理的是哪一个方法
//        System.out.println(((MethodInvocationProceedingJoinPoint)joinPoint));
//        //AOP代理类的名字
//        System.out.println(signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
//        signature.getDeclaringType();
        //获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        //从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        System.out.println(request.getRequestURI());
//        System.out.println(JWTAuthenticationUtil.getIpAddress(request));
//        System.out.println(JWTAuthenticationUtil.getUserId(request));
        //如果要获取Session信息的话，可以这样写：
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
/*
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> parameterMap = Maps.newHashMap();
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }
        String str = JSON.toJSONString(parameterMap);
        if (obj.length > 0) {
            System.out.println("请求的参数信息为：" + str);
        }
*/
    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     *
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(value = "execution(* com.forest..*.web..*.*(..))", returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object keys) {
//        System.out.println("第一个后置返回通知的返回值：" + keys);
    }

    @AfterReturning(value = "execution(* com.forest..*.web..*.*(..))", returning = "keys", argNames = "keys")
    public void doAfterReturningAdvice2(String keys) {
//        System.out.println("第二个后置返回通知的返回值：" + keys);
    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     * 对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     *
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "executeService()", throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        //目标方法名：
        System.out.println(joinPoint.getSignature().getName());
        if (exception instanceof NullPointerException) {
            System.out.println("发生了空指针异常!!!!!");
        }
    }

    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     *
     * @param joinPoint
     */
    @After("executeService()")
    public void doAfterAdvice(JoinPoint joinPoint) {
//        System.out.println("后置通知执行了!!!!");
    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around("execution(public * com.forest..*.web..*.*(..))")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        System.out.println("请求：" + request.getRequestURI());
        long start = System.currentTimeMillis();
        try {//obj之前可以写目标方法执行前的逻辑
            Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
            savelog(request,System.currentTimeMillis() - start,"");
//            System.out.println("请求：" +request.getRequestURI() +  "执行耗时：".concat(String.valueOf(System.currentTimeMillis() - start)) + "毫秒");
            return obj;
        } catch (Throwable throwable) {
            savelog(request,0,throwable.getMessage());
            throwable.printStackTrace();
        }
        return null;
    }

    private void savelog(HttpServletRequest request, long spantimes, String message){
        if(loggerflg) {
            String userid = JWTAuthenticationUtil.getUserId(request);
            String ip = JWTAuthenticationUtil.getIpAddress(request);
            String url = request.getRequestURI();
            FrameLog log = new FrameLog();
            log.setIpaddress(ip);
            log.setOperatetime(new Date());
            log.setOperator(userid);
            log.setRequesturl(url);
            if (Strings.isNullOrEmpty(message)) {
                log.setSpendtime(spantimes);
            } else {
                log.setRemark("执行报错：" + message);
            }
            frameLogService.save(log);
        }
    }
}
