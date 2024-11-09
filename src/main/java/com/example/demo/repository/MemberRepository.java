package com.example.demo.repository;

import com.example.demo.entity.MemberEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
}
