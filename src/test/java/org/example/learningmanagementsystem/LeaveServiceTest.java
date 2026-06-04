package org.example.learningmanagementsystem;

import org.example.learningmanagementsystem.model.LeaveRequest;
import org.example.learningmanagementsystem.service.LeaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LeaveServiceTest {

    private LeaveService leaveService;

    @BeforeEach
    void setUp() {
        // Initialize a fresh service before every test
        leaveService = new LeaveService();
    }

    // Task 1: Unit test for leave balance calculation (Success scenario)
    @Test
    void testSubmitLeave_ValidBalance_ReturnsPendingRequest() {
        // Arrange
        LeaveRequest request = new LeaveRequest(null, 1045L, "Annual", LocalDate.now(), LocalDate.now().plusDays(3), 3);

        // Act
        LeaveRequest result = leaveService.submitLeave(request);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Pending", result.getStatus());
        assertEquals(3, result.getDaysRequested());
    }

    // Task 1: Unit test for leave balance calculation (Failure scenario)
    @Test
    void testSubmitLeave_InsufficientBalance_ThrowsException() {
        // Arrange: Employee 1045 only has 14 days, we request 20.
        LeaveRequest request = new LeaveRequest(null, 1045L, "Annual", LocalDate.now(), LocalDate.now().plusDays(20), 20);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            leaveService.submitLeave(request);
        });

        assertEquals("Insufficient leave balance.", exception.getMessage());
    }
}