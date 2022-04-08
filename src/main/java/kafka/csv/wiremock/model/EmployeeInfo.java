package kafka.csv.wiremock.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class EmployeeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long num;
    public String id;
    public String employee_name;
    public String employee_salary;
    public String employee_age;
    public String department;
}
