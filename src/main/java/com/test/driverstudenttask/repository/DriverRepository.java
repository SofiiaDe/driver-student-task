package com.test.driverstudenttask.repository;

import com.test.driverstudenttask.driving.model.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

}
