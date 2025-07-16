package com.travelplanner.service;

import com.travelplanner.model.TriPlan;
import com.travelplanner.model.User;
import com.travelplanner.repository.TriPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing Trip Plans.
 */
@Service
public class TriPlanService {

    @Autowired
    private TriPlanRepository triPlanRepository;

    /**
     * Retrieves all published trip plans.
     *
     * @return List of TriPlan objects that are published
     */
    public List<TriPlan> getAllPublishedTriPlans() {
        return triPlanRepository.findByPublishedTrue();
    }

    /**
     * Saves or updates a trip plan.
     *
     * @param triPlan The TriPlan object to save or update
     * @return The saved TriPlan object
     */
    public TriPlan save(TriPlan triPlan) {
        return triPlanRepository.save(triPlan);
    }

    /**
     * Retrieves a trip plan by its ID.
     *
     * @param id The ID of the trip plan to retrieve
     * @return The TriPlan object if found, null otherwise
     */
    public TriPlan getTriPlanById(Long id) {
        return triPlanRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a trip plan by its ID.
     *
     * @param id The ID of the trip plan to delete
     */
    public void deleteTriPlan(Long id) {
        triPlanRepository.deleteById(id);
    }

    /**
     * Finds trip plans created by a specific user.
     *
     * @param username The username of the user
     * @return List of TriPlan objects created by the user
     */
    public List<TriPlan> findTripsByUser(String username) {
        return triPlanRepository.findByUsername(username);
    }

    /**
     * Saves a trip plan based on an original plan and associates it with a user.
     *
     * @param originalTrip The original TriPlan object to save
     * @param user         The User object associated with the new TriPlan
     */
    public void saveTrip(TriPlan originalTrip, User user) {
        TriPlan newTrip = new TriPlan();
        newTrip.setName(originalTrip.getName());
        newTrip.setHotels(originalTrip.getHotels());
        newTrip.setEndDate(originalTrip.getEndDate());
        newTrip.setStartDate(originalTrip.getStartDate());
        newTrip.setActivities(originalTrip.getActivities());
        newTrip.setUsername(user.getUsername());
        newTrip.setUser(user);
        triPlanRepository.save(newTrip);
    }

    /**
     * Deletes a trip plan by its ID.
     *
     * @param tripId The ID of the trip plan to delete
     */
    public void deleteTripById(Long tripId) {
        triPlanRepository.deleteById(tripId);
    }
}
