package org.example.learningmanagementsystem.controller;

import org.example.learningmanagementsystem.model.LeaveRequest;
import org.example.learningmanagementsystem.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // FEATURE 1: Submit Leave Application
    @PostMapping("/submit")
    public ResponseEntity<?> submitLeave(@RequestBody LeaveRequest request) {
        try {
            LeaveRequest savedRequest = leaveService.submitLeave(request);
            return ResponseEntity.status(201).body(Map.of(
                    "status", "success",
                    "message", "Leave application submitted successfully.",
                    "data", savedRequest
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // FEATURE 2: Process Leave (Approve/Reject)
    @PutMapping("/process/{id}")
    public ResponseEntity<?> processLeave(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        try {
            String action = payload.get("action");
            LeaveRequest processedRequest = leaveService.processLeave(id, action);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Leave " + action.toLowerCase() + "d successfully.",
                    "data", processedRequest
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // TASK 2: New Feature Endpoint
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelLeaveRequest(@PathVariable Long id, @RequestParam Long employeeId) {
        try {
            boolean isCanceled = leaveService.cancelLeave(id, employeeId);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Leave request canceled successfully."
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}