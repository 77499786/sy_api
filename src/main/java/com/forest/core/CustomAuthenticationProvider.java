package com.forest.core;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 *  自定义身份认证验证组件
 *  这个类就是提供密码验证功能，在实际使用时换成自己相应的验证逻辑，从数据库中取出、比对、赋予用户相应权限。
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

//    @Autowired
//    FrameEmployeeService frameEmployeeService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
//        String encode_pass = passwordEncoder.encode(password);

//        FrameEmployee employee  = frameEmployeeService.findBy("userid", name);
//        if(employee != null && password.equals(employee.getPassword())){
//            // 认证成功、 生成token
//            // 生成令牌
//            Authentication auth = new UsernamePasswordAuthenticationToken(name, password);
//            return auth;
//        }else {
//            throw new BadCredentialsException("账号或者密码错误！");
//        }
        // 认证逻辑
        if (name.equals("admin") && password.equals("Password@forest")) {

            // 这里设置权限和角色
            ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//            authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN") );
//            authorities.add( new GrantedAuthorityImpl("AUTH_WRITE") );
            // 生成令牌
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
            return auth;
        }else {
            throw new BadCredentialsException("密码错误~");
        }
    }

    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
