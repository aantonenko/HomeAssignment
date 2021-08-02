package com.rta.db;

import com.rta.model.DrugRecordApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRecordApplicationRepository extends JpaRepository<DrugRecordApplication, String> {
}
