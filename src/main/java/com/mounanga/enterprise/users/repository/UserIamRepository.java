package com.mounanga.enterprise.users.repository;

import com.mounanga.enterprise.users.entity.UserIam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserIamRepository extends JpaRepository<UserIam, String> {

    Optional<UserIam> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select u from UserIam u where u.workspace.id =:workspaceId")
    Page<UserIam> findAllByWorkspaceId(@Param("workspaceId") String workspaceId, Pageable pageable);

    @Query("select u from UserIam  u where u.workspace.id =:workspaceId and (u.firstname like:keyword or u.lastname like :keyword)")
    Page<UserIam> searchAllByWorkspaceId(@Param("workspaceId") String workspaceId, @Param("keyword") String keyword, Pageable pageable);

    @Query("select u from UserIam u where u.workspace.id =:workspaceId")
    List<UserIam> findByWorkspaceId(@Param("workspaceId") String workspaceId);
}
