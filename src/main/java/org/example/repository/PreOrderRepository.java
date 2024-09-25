package org.example.repository;


import org.example.models.PreOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreOrderRepository extends JpaRepository<PreOrder, Long> {
}
