package com.travelplanner.repository;

import com.travelplanner.model.TriPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing TriPlan data.
 */
public interface TriPlanRepository extends JpaRepository<TriPlan, Long> {

    /**
     * Finds all TriPlans that are published.
     *
     * @return a list of published TriPlans.
     */
    List<TriPlan> findByPublishedTrue();

    /**
     * Finds all TriPlans associated with a specific username.
     *
     * @param userName the username to search for.
     * @return a list of TriPlans associated with the given username.
     */
    List<TriPlan> findByUsername(String userName);
}
