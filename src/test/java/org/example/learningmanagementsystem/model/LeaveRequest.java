package org.example.learningmanagementsystem.model;

import java.time.LocalDate;

public class LeaveRequest {
    private Long id;
    private Long employeeId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int daysRequested;
    private String status;

    // Constructor
    public LeaveRequest(Long id, Long employeeId, String leaveType, LocalDate startDate, LocalDate endDate, int daysRequested) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysRequested = daysRequested;
        this.status = "Pending";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Long getEmployeeId() { return employeeId; }
    public String getStatus() { return status; }
    public String getLeaveType() { return leaveType;}
    public LocalDate getStartDate() { return startDate;}
    public LocalDate getEndDate() { return endDate;}
    public void setStatus(String status) { this.status = status; }
    public int getDaysRequested() { return daysRequested; }
}