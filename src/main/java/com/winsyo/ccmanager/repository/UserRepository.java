package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.User;
import com.winsyo.ccmanager.domain.enumerate.UserType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

  Optional<User> findByUsername(String username);

  List<User> findUsersByParentId(String parentId);

  List<User> findUsersByParentIdAndUsernameContains(String parentId, String name);

  long countByParentId(String parentId);

  Optional<User> findByType(UserType type);

  User findUserByAgentAreaCode(String agentAreaCode);

  @Modifying
  @Query("update User set name = :name, phone = :phone, identityCard = :IDNumber where username = :username ")
  void updateUserInfo(@Param(value = "name") String name, @Param(value = "phone") String phone, @Param(value = "IDNumber") String IDNumber,
      @Param(value = "username") String username);

}
