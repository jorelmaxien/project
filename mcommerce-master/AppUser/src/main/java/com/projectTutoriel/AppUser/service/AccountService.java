package com.projectTutoriel.AppUser.service;

import com.projectTutoriel.AppUser.entities.AppRole;
import com.projectTutoriel.AppUser.entities.AppUser;

public interface AccountService {

    public AppUser saveUser(String username, String pwd, String email, String confirmedPwd);
    public AppRole saveRole(String rolename);
    public AppUser loadUserByUserName(String username);
    public void addRoleToUser(String username, String nameRole);
}
