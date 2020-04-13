package kafka.csv.wiremock.controller;

import static java.nio.file.Files.newBufferedReader;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kafka.csv.wiremock.csv.CsvSave;
import kafka.csv.wiremock.repo.CsvRepo;

@RestController
public class MainController {

	private final CsvRepo csvRepo;

	public MainController(CsvRepo csvRepo) {
		this.csvRepo = csvRepo;
	}

	@RequestMapping(value = "/readcsv", method = RequestMethod.GET)
	public String saveToDb(){
		CsvSave csvSave = new CsvSave(csvRepo);
		String saved = "NOT SAVED";
		try {
			csvSave.readCsvFile();
			saved = "SAVED";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saved;
	}

}
