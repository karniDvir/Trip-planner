package com.travelplanner.controller;

import com.travelplanner.model.TriPlan;
import com.travelplanner.model.User;
import com.travelplanner.service.TriPlanService;
import com.travelplanner.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

/**
 * Controller for handling trip-related requests.
 */
@Controller
@RequestMapping("/trip")
public class TriPlanController {

    @Autowired
    private TriPlanService triPlanService;

    @Autowired
    private UserService userService;

    /**
     * Displays a list of all published trip plans.
     *
     * @param model the model to pass data to the view.
     * @param session the HTTP session to check for any errors.
     * @return the name of the view to display the list of trips (listTrips.html).
     */
    @GetMapping("/list")
    public String listTripPlans(Model model, HttpSession session) {
        model.addAttribute("tripPlans", triPlanService.getAllPublishedTriPlans());
        if (session.getAttribute("error") != null) {
            model.addAttribute("error", session.getAttribute("error"));
            session.removeAttribute("error");
        }
        return "listTrips";
    }

    /**
     * Saves a trip plan to the user's saved trips.
     *
     * @param tripId the ID of the trip to save.
     * @param authentication the authentication object containing the user's details.
     * @param session the HTTP session to store any errors.
     * @return a redirect to the list of trips on failure or to the user's saved trips on success.
     */
    @PostMapping("/save")
    public String saveTrip(@RequestParam Long tripId, Authentication authentication, HttpSession session) {
        TriPlan trip = triPlanService.getTriPlanById(tripId);
        if (trip.getUsername().equals(authentication.getName())) {
            session.setAttribute("error", "You cannot save your own trip.");
            return "redirect:/trip/list";
        } else {
            User user = userService.findByUsername(authentication.getName());
            triPlanService.saveTrip(trip, user);
            return "redirect:/user/myTrips";
        }
    }

    /**
     * Saves a trip plan to the session for saving after login.
     *
     * @param tripId the ID of the trip to save.
     * @param session the HTTP session to store the trip.
     * @return a redirect to the login page.
     */
    @PostMapping("/saveToSession")
    public String saveTripToSession(@RequestParam Long tripId, HttpSession session) {
        TriPlan trip = triPlanService.getTriPlanById(tripId);
        session.setAttribute("tripToSave", trip);
        return "redirect:/login";
    }

    /**
     * Saves a trip plan after the user logs in.
     *
     * @param session the HTTP session containing the trip to save.
     * @param authentication the authentication object containing the user's details.
     * @return a redirect to the list of trips on failure or to the user's saved trips on success.
     */
    @GetMapping("/afterLogin")
    public String saveTripAfterLogin(HttpSession session, Authentication authentication) {
        if (session.getAttribute("tripToSave") != null) {
            TriPlan trip = (TriPlan) session.getAttribute("tripToSave");
            if (trip.getUsername().equals(authentication.getName())) {
                session.setAttribute("error", "You cannot save your own trip.");
                return "redirect:/trip/list";
            } else {
                User user = userService.findByUsername(authentication.getName());
                triPlanService.saveTrip(trip, user);
            }
            session.removeAttribute("tripToSave");
        }
        return "redirect:/user/myTrips";
    }
}
