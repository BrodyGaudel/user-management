package com.mounanga.enterprise.users.repository;

import com.mounanga.enterprise.users.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, String> {

    @Query("select w from Workspace w where w.userRoot.username =:username")
    Optional<Workspace> findByUsername(@Param("username") String username);

}
