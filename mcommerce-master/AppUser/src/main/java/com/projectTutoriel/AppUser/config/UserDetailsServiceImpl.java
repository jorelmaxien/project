package com.projectTutoriel.AppUser.config;

import com.projectTutoriel.AppUser.entities.AppUser;
import com.projectTutoriel.AppUser.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = accountService.loadUserByUserName(username);

        if (appUser == null) throw new UsernameNotFoundException("invalid user");

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        appUser.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getNameRole()));
        });

        return new User(appUser.getUsername(),appUser.getPwd(),authorities);
    }
}
