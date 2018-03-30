package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {


}
