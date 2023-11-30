package com.example.unitech.persistence.repository;

import com.example.unitech.dto.account.UserAccountResponseDto;
import com.example.unitech.persistence.entity.AccountEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query(
            "select new com.example.unitech.dto.account.UserAccountResponseDto(a.id,a.balance,"
                    + "a.currency,a.status,a.createdAt)"
                    + " from AccountEntity a"
                    + " join UserEntity u"
                    + " on a.user = u"
                    + " where u.id = :id and a.status = 'ACTIVE'")
    List<UserAccountResponseDto> getByUserId(Long id);
}
