package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.Event.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository  extends JpaRepository<EventType, Long> {

    Optional<EventType> findByName(String name);
}
