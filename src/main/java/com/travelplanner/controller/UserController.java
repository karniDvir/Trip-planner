package com.travelplanner.controller;

import com.travelplanner.model.TriPlan;
import com.travelplanner.model.User;
import com.travelplanner.service.TriPlanService;
import com.travelplanner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for handling user-related requests such as trip creation, modification, and deletion.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TriPlanService triPlanService;

    @Autowired
    private UserService userService;

    /**
     * Displays the form for creating a new trip.
     *
     * @param model the model to pass data to the view.
     * @return the name of the view to create a trip (createTrip.html).
     */
    @GetMapping("/createTrip")
    public String showCreateForm(Model model) {
        model.addAttribute("tripPlan", new TriPlan());
        return "createTrip";
    }

    /**
     * Handles the submission of the trip creation form.
     *
     * @param triPlan the trip plan to create.
     * @param bindingResult the result of validation.
     * @param authentication the authentication object containing the user's details.
     * @return a redirect to the trip list on success or the create trip view on failure.
     */
    @PostMapping("/createTrip")
    public String createTripPlan(@Valid @ModelAttribute("tripPlan") TriPlan triPlan, BindingResult bindingResult,
                                 Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "createTrip";
        }

        String username = authentication.getName(); // Get username of logged-in user
        User user = userService.findByUsername(username);
        if (user != null) {
            triPlan.setUser(user);
            triPlanService.save(triPlan);
            return "redirect:/trip/list";
        } else {
            bindingResult.reject("user", "User not found.");
            return "createTrip";
        }
    }

    /**
     * Displays the trips created by the logged-in user.
     *
     * @param model the model to pass data to the view.
     * @param authentication the authentication object containing the user's details.
     * @return the name of the view to display the user's trips (myTrips.html).
     */
    @GetMapping("/myTrips")
    public String showMyTrips(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<TriPlan> trips = triPlanService.findTripsByUser(username);
        model.addAttribute("trips", trips);
        return "myTrips";
    }

    /**
     * Deletes a trip created by the logged-in user.
     *
     * @param tripId the ID of the trip to delete.
     * @param authentication the authentication object containing the user's details.
     * @return a redirect to the user's trips.
     */
    @GetMapping("/deleteTrip/{tripId}")
    public String deleteTrip(@PathVariable Long tripId, Authentication authentication) {
        String username = authentication.getName();
        TriPlan tripPlan = triPlanService.getTriPlanById(tripId);

        if (tripPlan != null && tripPlan.getUser().getUsername().equals(username)) {
            triPlanService.deleteTripById(tripId);
        }

        return "redirect:/user/myTrips";
    }

    /**
     * Displays the form to add details to a specific trip.
     *
     * @param tripId the ID of the trip to add details to.
     * @param model the model to pass data to the view.
     * @return the name of the view to add details to the trip (addDetails.html).
     */
    @GetMapping("/addDetails/{tripId}")
    public String showAddDetailsForm(@PathVariable Long tripId, Model model) {
        TriPlan tripPlan = triPlanService.getTriPlanById(tripId);
        model.addAttribute("tripPlan", tripPlan);
        return "addDetails";
    }

    /**
     * Handles the date selection in the add details form.
     *
     * @param tripId the ID of the trip to add details to.
     * @param selectedDate the selected date to add details for.
     * @return a redirect to the detailed form for the selected date.
     */
    @PostMapping("/addDetails/{tripId}")
    public String handleDateSelection(@PathVariable Long tripId,
                                      @RequestParam("selectedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        return "redirect:/user/addDetails/" + tripId + "/" + selectedDate;
    }

    /**
     * Displays the detailed form to add details for a specific date in a trip.
     *
     * @param tripId the ID of the trip to add details to.
     * @param date the selected date to add details for.
     * @param model the model to pass data to the view.
     * @return the name of the view to add details for the selected date (addDetailsDate.html).
     */
    @GetMapping("/addDetails/{tripId}/{date}")
    public String showDetailedForm(@PathVariable Long tripId,
                                   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   Model model) {
        TriPlan tripPlan = triPlanService.getTriPlanById(tripId);

        // Validate selected date is within trip's date range
        LocalDate startDate = tripPlan.getStartDate();
        LocalDate endDate = tripPlan.getEndDate();

        if (date.isBefore(startDate) || date.isAfter(endDate)) {
            return "redirect:/user/myTrips";
        }

        model.addAttribute("tripPlan", tripPlan);
        model.addAttribute("selectedDate", date); // Pass selected date to the view

        return "addDetailsDate";
    }

    /**
     * Publishes a trip to make it publicly available.
     *
     * @param tripId the ID of the trip to publish.
     * @return a redirect to the user's trips.
     */
    @GetMapping("/publishTrip/{tripId}")
    public String publishTrip(@PathVariable Long tripId) {
        TriPlan tripPlan = triPlanService.getTriPlanById(tripId);
        if (tripPlan != null) {
            tripPlan.setPublished(true);
            triPlanService.save(tripPlan);
        }
        return "redirect:/user/myTrips";
    }

    /**
     * Unpublishes a trip to make it private.
     *
     * @param tripId the ID of the trip to unpublish.
     * @return a redirect to the user's trips.
     */
    @GetMapping("/unpublishTrip/{tripId}")
    public String unpublishTrip(@PathVariable Long tripId) {
        TriPlan tripPlan = triPlanService.getTriPlanById(tripId);
        if (tripPlan != null) {
            tripPlan.setPublished(false);
            triPlanService.save(tripPlan);
        }
        return "redirect:/user/myTrips";
    }

    /**
     * Saves the details for a specific date in a trip.
     *
     * @param tripId the ID of the trip to add details to.
     * @param date the selected date to add details for.
     * @param hotel the hotel to save for the selected date.
     * @param activity the activity to save for the selected date.
     * @return a redirect to the user's trips.
     */
    @PostMapping("/addDetails/{tripId}/{date}")
    public String saveDetailsForDate(@PathVariable Long tripId,
                                     @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     @RequestParam String hotel,
                                     @RequestParam String activity) {
        TriPlan existingTrip = triPlanService.getTriPlanById(tripId);

        // Update hotels and activities for the selected date
        existingTrip.getHotels().put(date, hotel);
        existingTrip.getActivities().put(date, activity);

        // Save the updated trip plan
        triPlanService.save(existingTrip);

        return "redirect:/user/myTrips";
    }

    /**
     * Displays the details of a specific trip.
     *
     * @param tripId the ID of the trip to display details for.
     * @param model the model to pass data to the view.
     * @return the name of the view to display the trip details (presentDetails.html).
     */
    @GetMapping("/presentDetails/{tripId}")
    public String showTripDetails(@PathVariable Long tripId, Model model) {
        TriPlan tripPlan = triPlanService.getTriPlanById(tripId);
        List<LocalDate> dateRange = tripPlan.getDateRange();
        long tripLength = tripPlan.getTripLengthInDays();

        model.addAttribute("tripPlan", tripPlan);
        model.addAttribute("dateRange", dateRange);
        model.addAttribute("tripLength", tripLength);

        return "presentDetails";
    }

    /**
     * Returns the error.html page in case of an exception.
     *
     * @return the name of the view to display the error page.
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
