package com.forest.core;

import com.forest.project.model.FrameLog;
import com.forest.project.service.FrameLogService;
import com.forest.project.service.impl.FrameLogServiceImpl;
import com.forest.utils.JWTAuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;

public class InterceptorController  implements HandlerInterceptor {

//    @Autowired
//    private  FrameLogService frameLogService;
//    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);
    /**
     * 进入controller层之前拦截请求 
     * @param httpServletRequest 
     * @param httpServletResponse 
     * @param o 
     * @return 
     * @throws Exception 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {

        String userid = JWTAuthenticationUtil.getUserId(httpServletRequest);
        String ip = JWTAuthenticationUtil.getIpAddress(httpServletRequest);
        String url = httpServletRequest.getRequestURI();
//        FrameLogService frameLogService = new FrameLogServiceImpl();
//        FrameLog log = new FrameLog();
//        log.setIpaddress(ip);
//        log.setOperatetime(new Date());
//        log.setOperator(userid);
//        log.setRequesturl(url);
//        frameLogService.save(log);
        return true;
//        log.info("---------------------开始进入请求地址拦截----------------------------");
        /*HttpSession session = httpServletRequest.getSession();
        if(!StringUtils.isEmpty(session.getAttribute("userName"))){
            return true;  
        }  
        else{  
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("{code:0,message:\"session is invalid,please login again!\"}");  
            return false;  
        }  */
  
    }  
  
    @Override  
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
//        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }  
  
    @Override  
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        log.info("---------------视图渲染之后的操作-------------------------0");
    }  
} 