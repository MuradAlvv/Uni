package com.example.unitech.persistence.repository;

import com.example.unitech.persistence.entity.TransferEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    @Query(
            "select t from TransferEntity t"
                    + " join fetch t.fromAccount fa"
                    + " join fetch t.toAccount ta"
                    + " join fetch fa.user"
                    + " join fetch ta.user"
                    + " where t.id = :id")
    TransferEntity getByIdFetchUser(Long id);
}
