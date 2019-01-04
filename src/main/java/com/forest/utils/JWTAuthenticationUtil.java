package com.forest.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

public class JWTAuthenticationUtil {
    private static final String SECRET = "P@ssw02d";            // JWT密码
    private  static final String TOKEN_PREFIX = "forest";        // Token前缀
    private  static final String HEADER_STRING = "Authorization";// 存放Token的Header Key

    // JWT验证方法
    public static String getUserId(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            try {
                // 解析 Token
                Claims claims = Jwts.parser()
                        // 验签
                        .setSigningKey(SECRET).setSigningKey(getIpAddress(request))
                        // 去掉 Bearer
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                // 拿用户名
                return claims.getSubject();
            } catch (Exception e){
                return null;
            }
        }
        return null;
    }

    /**
     * @param request
     * @return IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

}
