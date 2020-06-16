package kafka.csv.wiremock.controller;

import org.slf4j.Logger;
import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kafka.csv.wiremock.csv.CsvSave;
import kafka.csv.wiremock.repo.CsvRepo;

@RestController
public class CSVController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVController.class);
    private final CsvRepo csvRepo;

    public CSVController(CsvRepo csvRepo) {
        this.csvRepo = csvRepo;
    }

    @RequestMapping(value = "/readcsv", method = RequestMethod.GET)
    public String saveToDb() {
        LOGGER.info("Entered into the csv Controller");
        CsvSave csvSave = new CsvSave(csvRepo);
        String saved = "NOT SAVED";
        try {
            csvSave.readCsvFile();
            LOGGER.info("SAVED SUCCESSFULLY");
            saved = "SAVED";
        } catch (IOException e) {
            LOGGER.info("Exception has thrown");
            e.printStackTrace();
        }
        return saved;
    }

}
