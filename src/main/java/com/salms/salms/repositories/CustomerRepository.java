package com.salms.salms.repositories;

import com.salms.salms.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customers, Long> {

   Optional<Customers> findByPhoneNumber(String phoneNumber);

   Customers findByPhoneNumber2 (String phoneNumber);

}
