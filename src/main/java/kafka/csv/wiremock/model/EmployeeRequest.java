package kafka.csv.wiremock.model;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String name;
    private String salary;
    private String age;
}
