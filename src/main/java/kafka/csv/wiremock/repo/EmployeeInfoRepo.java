package kafka.csv.wiremock.repo;

import kafka.csv.wiremock.model.EmployeeInfo;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeInfoRepo extends CrudRepository<EmployeeInfo, Long> {
}
