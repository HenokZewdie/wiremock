package kafka.csv.wiremock.model;

import lombok.Data;

@Data
public class PostEmployeeResponse {
    private String status;
    private EmployeePostResponse data;
}
