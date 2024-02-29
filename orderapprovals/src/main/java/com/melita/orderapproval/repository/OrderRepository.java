package com.melita.orderapproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melita.orderapproval.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>{

}
