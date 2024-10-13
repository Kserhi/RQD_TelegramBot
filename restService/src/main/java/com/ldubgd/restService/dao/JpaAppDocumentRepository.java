package com.ldubgd.restService.dao;

import com.ldubgd.restService.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAppDocumentRepository extends JpaRepository<FileInfo,Long> {


}
