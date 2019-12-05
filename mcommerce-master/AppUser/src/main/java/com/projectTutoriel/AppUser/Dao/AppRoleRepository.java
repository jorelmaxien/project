package com.projectTutoriel.AppUser.Dao;

import com.projectTutoriel.AppUser.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    public AppRole findByNameRole(String rolename);
}
