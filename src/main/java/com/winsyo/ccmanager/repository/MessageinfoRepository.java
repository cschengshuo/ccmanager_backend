package com.winsyo.ccmanager.repository;

import com.winsyo.ccmanager.domain.Messageinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageinfoRepository extends JpaRepository<Messageinfo, String>, JpaSpecificationExecutor<Messageinfo> {

}
