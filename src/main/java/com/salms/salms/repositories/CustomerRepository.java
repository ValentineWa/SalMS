package com.salms.salms.repositories;

import com.salms.salms.models.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface CustomerRepository extends JpaRepository<Customers, UUID> {

//   Optional<Customers> findByPhoneNumbers(String phoneNumber);

   Customers findByPhoneNumber (String phoneNumber);

   @Override
   Optional<Customers> findById(UUID id);


}
