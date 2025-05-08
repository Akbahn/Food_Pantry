package org.main.food_pantry.Databases;


import org.main.food_pantry.Models.RequestRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentRequestGroup {
    private int studentId;
    private String studentName;
    private LocalDate requestDate;
    private String status;
    private List<RequestRow> requests = new ArrayList<>();

    public StudentRequestGroup(int studentId, String studentName, LocalDate requestDate, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.requestDate = requestDate;
        this.status = status;
    }

    public void addRequest(RequestRow row) {
        this.requests.add(row);
    }

    // Getters
    public int getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public LocalDate getRequestDate() { return requestDate; }
    public String getStatus() { return status; }
    public List<RequestRow> getRequests() { return requests; }
}
