package com.uniovi.repositories;

import com.uniovi.entities.Offer;
import com.uniovi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {

    Page<Offer> findAll(Pageable pageable);

    @Query("SELECT r FROM Offer r WHERE r.user = ?1 ORDER BY r.id ASC ")
    Page<Offer> findAllByUser(Pageable pageable, User user);

    @Query("SELECT r FROM Offer r WHERE r.buyer = ?1 ORDER BY r.id ASC")
    Page<Offer> findAllBuysByUser(Pageable pageable, User user);

    @Query("SELECT r FROM Offer r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1))")
    Page<Offer> searchByDescriptionAndName(Pageable pageable, String seachtext);

    @Query("SELECT r FROM Offer r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1)) AND r.user = ?2 ")
    Page<Offer> searchByDescriptionNameAndUser(Pageable pageable, String seachtext, User user);

    @Query("SELECT r FROM Offer r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1)) AND r.buyer = ?2")
    Page<Offer> searchBuysByDescriptionNameAndUser(Pageable pageable, String searchText, User user);

    @Query("SELECT r FROM Offer r WHERE (LOWER(r.description) LIKE LOWER(?1) OR LOWER(r.name) LIKE LOWER(?1)) AND r.user = ?2 ")
    List<Offer> searchByDescriptionNameAndUser(String seachtext, User user);

    @Query("SELECT r FROM Offer r WHERE r.highlighted = true")
    List<Offer> getHighlightedOffers();

    @Modifying
    @Transactional
    @Query("DELETE FROM Offer WHERE id = ?1")
    int deleteManually(Long id);

}