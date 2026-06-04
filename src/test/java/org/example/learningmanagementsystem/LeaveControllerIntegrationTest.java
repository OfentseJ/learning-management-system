package org.example.learningmanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
class LeaveControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Task 2: Integration testing (API + Service integration)
    @Test
    void testSubmitLeaveEndpoint_Integration() throws Exception {
        // JSON payload simulating a frontend request
        String requestJson = """
                {
                    "employeeId": 1045,
                    "leaveType": "Annual",
                    "startDate": "2026-07-15",
                    "endDate": "2026-07-20",
                    "daysRequested": 4
                }
                """;

        // Perform the POST request and check the results
        mockMvc.perform(post("/api/leave/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated()) // Expect HTTP 201
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Leave application submitted successfully."))
                .andExpect(jsonPath("$.data.status").value("Pending"));
    }
}