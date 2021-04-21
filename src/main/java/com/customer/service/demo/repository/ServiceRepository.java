package com.customer.service.demo.repository;

import com.customer.service.demo.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {

    @Query(value = "SELECT * FROM service WHERE customer_foreign = :customerId", nativeQuery = true)
    List<ServiceEntity> findAllServicesByCustomerId(int customerId);
}
