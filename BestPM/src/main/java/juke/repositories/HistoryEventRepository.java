package juke.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.HistoryEvent;

public interface HistoryEventRepository extends JpaRepository<HistoryEvent, Integer> {

}
