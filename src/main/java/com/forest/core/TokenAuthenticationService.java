package com.forest.core;

import com.forest.utils.JWTAuthenticationUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 432_000_000;     // 5天
    static final String SECRET = "P@ssw02d";            // JWT密码
    static final String TOKEN_PREFIX = "forest";        // Token前缀
    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
    static final String GRANTEDAUTHORITY_KEY ="authorities";
    /**
     * JWT生成方法
     * @param  request
     * @param response
     * @param username
     */
    static void addAuthentication(HttpServletRequest request, HttpServletResponse response, String username) {

//        JWTAuthenticationUtil.getIpAddress(request);
        // 生成JWT
        String JWT = Jwts.builder()
                // 保存权限（角色）
                .claim(GRANTEDAUTHORITY_KEY, "") // "ROLE_ADMIN,AUTH_WRITE")
                // 用户名写入标题
                .setSubject(username)
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .signWith(SignatureAlgorithm.HS512,JWTAuthenticationUtil.getIpAddress(request))
                .compact();

        // 将 JWT 写入 body
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("utf-8");
//            response.getOutputStream().println(JSONResult.fillResultString(0, "", JWT));
            response.getWriter().print(ResultGenerator.genSuccessResult(JWT).toString());
//            response.getOutputStream().println(ResultGenerator.genSuccessResult(JWT).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * JWT验证方法
     * @param request
     * @return
     */
    static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // 解析 Token
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET).setSigningKey(JWTAuthenticationUtil.getIpAddress(request))
                    // 去掉前缀
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            // 拿用户名
            String user = claims.getSubject();
            Date expiration =  claims.getExpiration();
//            System.out.println(expiration.getTime());
//            System.out.println(System.currentTimeMillis() < expiration.getTime());
            // 得到 权限（角色）
            List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(GRANTEDAUTHORITY_KEY));

            // 返回验证令牌
            return (user != null && System.currentTimeMillis() < expiration.getTime()) ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }
}
