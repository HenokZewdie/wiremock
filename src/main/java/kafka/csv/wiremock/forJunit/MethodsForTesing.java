package kafka.csv.wiremock.forJunit;

import kafka.csv.wiremock.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MethodsForTesing {

    @Autowired
    private PersonRepository personRepository;

    public MethodsForTesing(){
    }

    public MethodsForTesing(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public String getFullName() {
        Person singlePerson = personRepository.findByUsername("menaye");
        String fullname = createFullName(singlePerson.getFirstName(), singlePerson.getLastName());
        return fullname;
    }

    public String createFullName(String fName, String lName) {
        return fName + "  " + lName;
    }
}
