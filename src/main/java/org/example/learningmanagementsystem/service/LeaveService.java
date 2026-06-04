package org.example.learningmanagementsystem.service;

import org.example.learningmanagementsystem.model.LeaveRequest;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LeaveService {

    // MOCK DATABASE: Simulating LEAVE_BALANCE and LEAVE_REQUEST tables
    private final Map<Long, Integer> leaveBalances = new HashMap<>();
    private final List<org.example.learningmanagementsystem.model.LeaveRequest> leaveRequests = new ArrayList<>();
    private long nextRequestId = 9001;

    public LeaveService() {
        // Initialize mock data: Employee 1045 has 14 days of Annual leave
        leaveBalances.put(1045L, 14);
    }

    public LeaveRequest submitLeave(LeaveRequest request) throws IllegalArgumentException {
        // --- BUG FIX: Prevent zero or negative days ---
        if (request.getDaysRequested() <= 0) {
            throw new IllegalArgumentException("Days requested must be greater than zero.");
        }
        // ----------------------------------------------
        // Secure Practice: Validation & Business Logic
        int currentBalance = leaveBalances.getOrDefault(request.getEmployeeId(), 0);

        if (currentBalance < request.getDaysRequested()) {
            throw new IllegalArgumentException("Insufficient leave balance.");
        }

        LeaveRequest newRequest = new LeaveRequest(
                nextRequestId++,
                request.getEmployeeId(),
                request.getLeaveType(),
                request.getStartDate(),
                request.getEndDate(),
                request.getDaysRequested()
        );

        leaveRequests.add(newRequest);
        return newRequest;
    }

    public LeaveRequest processLeave(Long requestId, String action) {
        Optional<LeaveRequest> optionalReq = leaveRequests.stream()
                .filter(r -> r.getId().equals(requestId))
                .findFirst();

        if (optionalReq.isEmpty()) {
            throw new NoSuchElementException("Leave request not found.");
        }

        LeaveRequest request = optionalReq.get();

        if (!request.getStatus().equals("Pending")) {
            throw new IllegalStateException("Request has already been processed.");
        }

        if (action.equalsIgnoreCase("Approve")) {
            request.setStatus("Approved");
            // Deduct balance
            int newBalance = leaveBalances.get(request.getEmployeeId()) - request.getDaysRequested();
            leaveBalances.put(request.getEmployeeId(), newBalance);
        } else if (action.equalsIgnoreCase("Reject")) {
            request.setStatus("Rejected");
        } else {
            throw new IllegalArgumentException("Invalid action. Use 'Approve' or 'Reject'.");
        }

        return request;
    }
}