package com.forest.core;

import com.forest.project.model.FrameEmployee;
import com.forest.project.service.FrameEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
public class FrameUserDetailsService implements UserDetailsService {
    @Autowired
    FrameEmployeeService frameEmployeeService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        username.toLowerCase();
        FrameEmployee user = frameEmployeeService.findBy("userid",username.toLowerCase());
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        Collection<GrantedAuthority> grantedRoles = new ArrayList<GrantedAuthority>();
//        for(SysRole role: user.getRoles()){
//            GrantedAuthority authority = new SimpleGrantedAuthority(role.getFlag());
//            grantedRoles.add(authority);
//        }
        return new User(user.getUserid(), user.getPassword(),grantedRoles);
    }
}
