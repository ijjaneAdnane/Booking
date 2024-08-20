package com.Boujorno.adnanemanager.repository;

import com.Boujorno.adnanemanager.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName(String fileName);
}