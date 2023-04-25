package com.example.springlv2.repository;

import com.example.springlv2.entity.Crud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
//spring data JPA
public interface CrudRepository extends JpaRepository<Crud, Long> {
    Optional<Crud> findByTitle(String title);

//    Optional<Crud> findByIdAndUsername(Long id, String username);
    //내림차순 정렬
    Collection<Crud> findAllByOrderByModifiedAtDesc();
    Collection<Crud> findAllByOrderByCreatedAtDesc();
}
