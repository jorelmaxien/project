package com.projectTutoriel.AppUser.service;

import com.projectTutoriel.AppUser.Dao.AppRoleRepository;
import com.projectTutoriel.AppUser.Dao.AppUserRepository;
import com.projectTutoriel.AppUser.entities.AppRole;
import com.projectTutoriel.AppUser.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
   private AppRoleRepository appRoleRepository;

    @Autowired
   private AppUserRepository appUserRepository;

    @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AppUser saveUser(String username, String pwd,String email, String confirmedPwd) {
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser != null) throw new RuntimeException("user already existe");

        if (!pwd.equals(confirmedPwd)) throw new RuntimeException("confirme your passeword");

        return appUserRepository.save(new AppUser(username, bCryptPasswordEncoder.encode(pwd), email));

    }

    @Override
    public AppRole saveRole(String rolename) {
        AppRole appRole = appRoleRepository.findByNameRole(rolename);

        if(appRole != null) throw new RuntimeException("this role already exist");
        return appRoleRepository.save(new AppRole(rolename));
    }

    @Override
    public AppUser loadUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String nameRole) {

        AppRole appRole = appRoleRepository.findByNameRole(nameRole);
        AppUser appUser = appUserRepository.findByUsername(username);
        appUser.getRoles().add(appRole);
    }
}
