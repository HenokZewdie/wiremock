package kafka.csv.wiremock.repo;

import kafka.csv.wiremock.forJunit.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

	    Person findByUsername(String username);

	}
