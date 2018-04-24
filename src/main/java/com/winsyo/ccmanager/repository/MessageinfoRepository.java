package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.MessageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageinfoRepository extends JpaRepository<MessageInfo, String>, JpaSpecificationExecutor<MessageInfo> {

  Page<MessageInfo> findByTypeOrderByCreatetimeDesc(String type, Pageable pageable);

}
