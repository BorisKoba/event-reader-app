package com.boriskoba.eventreader.repository;

import com.boriskoba.eventreader.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}