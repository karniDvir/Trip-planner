package com.travelplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelplanner.DateUtils;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity class representing a travel plan.
 */
@Entity
public class TriPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String username;
    private boolean published = false;

    @ManyToOne
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<LocalDate, String> hotels = new HashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<LocalDate, String> activities = new HashMap<>();

    /**
     * Gets the published status of the trip plan.
     * @return true if the trip plan is published, false otherwise.
     */
    public boolean isPublished() {
        return published;
    }

    /**
     * Sets the published status of the trip plan.
     * @param published the new published status.
     */
    public void setPublished(boolean published) {
        this.published = published;
    }

    /**
     * Gets the length of the trip in days.
     * @return the length of the trip in days.
     */
    public Long getTripLength() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate) + 1;
        } else {
            return null;
        }
    }

    /**
     * Gets the map of hotels.
     * @return a map of dates to hotel names.
     */
    public Map<LocalDate, String> getHotels() {
        return hotels;
    }

    /**
     * Sets the map of hotels.
     * @param hotels a map of dates to hotel names.
     */
    public void setHotels(Map<LocalDate, String> hotels) {
        this.hotels = hotels;
    }

    /**
     * Gets the map of activities.
     * @return a map of dates to activity names.
     */
    public Map<LocalDate, String> getActivities() {
        return activities;
    }

    /**
     * Sets the map of activities.
     * @param activities a map of dates to activity names.
     */
    public void setActivities(Map<LocalDate, String> activities) {
        this.activities = activities;
    }

    /**
     * Gets the hotel for a specific date.
     * @param hotelDate the date for which to get the hotel.
     * @return the hotel name for the specified date.
     */
    public String getHotel(LocalDate hotelDate) {
        return hotels.get(hotelDate);
    }

    /**
     * Sets the hotel for a specific date.
     * @param hotelDate the date for which to set the hotel.
     * @param hotelName the name of the hotel.
     */
    public void setHotel(LocalDate hotelDate, String hotelName) {
        hotels.put(hotelDate, hotelName);
    }

    /**
     * Gets the activity for a specific date.
     * @param activityDate the date for which to get the activity.
     * @return the activity name for the specified date.
     */
    public String getActivity(LocalDate activityDate) {
        return activities.get(activityDate);
    }

    /**
     * Sets the activity for a specific date.
     * @param activityDate the date for which to set the activity.
     * @param activity the name of the activity.
     */
    public void setActivity(LocalDate activityDate, String activity) {
        activities.put(activityDate, activity);
    }

    /**
     * Gets the ID of the trip plan.
     * @return the ID of the trip plan.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the trip plan.
     * @param id the ID of the trip plan.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the trip plan.
     * @return the name of the trip plan.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the trip plan.
     * @param name the name of the trip plan.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the username of the trip plan owner.
     * @return the username of the trip plan owner.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the trip plan owner.
     * @param username the username of the trip plan owner.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the start date of the trip.
     * @return the start date of the trip.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the trip.
     * @param startDate the start date of the trip.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the trip.
     * @return the end date of the trip.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the trip.
     * @param endDate the end date of the trip.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the user associated with the trip plan.
     * @return the user associated with the trip plan.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the trip plan.
     * @param user the user associated with the trip plan.
     */
    public void setUser(User user) {
        setUsername(user.getUsername());
        this.user = user;
    }

    /**
     * Gets the length of the trip in days.
     * @return the length of the trip in days.
     */
    public long getTripLengthInDays() {
        return this.getStartDate().until(this.getEndDate()).getDays() + 1;
    }

    /**
     * Gets the date range of the trip.
     * @return a list of dates representing the range of the trip.
     */
    public List<LocalDate> getDateRange(){
        return DateUtils.getDateRange(this.getStartDate(), this.getEndDate());
    }
}
