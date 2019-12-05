package com.projectTutoriel.AppUser.Dao;

import com.projectTutoriel.AppUser.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByUsername(String userName);
}
