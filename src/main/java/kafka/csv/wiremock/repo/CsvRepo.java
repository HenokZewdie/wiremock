package kafka.csv.wiremock.repo;

import org.springframework.data.repository.CrudRepository;

import kafka.csv.wiremock.csv.CsvEntity;

public interface CsvRepo extends CrudRepository<CsvEntity, String>{

}
