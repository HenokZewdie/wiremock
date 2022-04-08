package kafka.csv.wiremock.model;

import lombok.Data;

@Data
public class EmployeePostResponse {
    private String name;
    private String salary;
    private String age;
    private String id;
}
