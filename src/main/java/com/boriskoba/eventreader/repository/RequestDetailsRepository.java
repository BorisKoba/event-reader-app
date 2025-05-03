package com.boriskoba.eventreader.repository;


import com.boriskoba.eventreader.entity.RequestDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestDetailsRepository extends JpaRepository<RequestDetails, String> {
}