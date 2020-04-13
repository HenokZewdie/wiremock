package kafka.csv.wiremock.controller;

import static java.nio.file.Files.newBufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import kafka.csv.wiremock.csv.CsvEntity;
import kafka.csv.wiremock.csv.CsvSave;
import kafka.csv.wiremock.csv.PojoForTheCsvFile;
import kafka.csv.wiremock.repo.CsvRepo;

@RestController
public class MainController {

	@Autowired
	private CsvRepo csvRepo;
	
	@RequestMapping(value = "/readcsv", method = RequestMethod.GET)
	public String saveToDb(){
		CsvSave csvSave = new CsvSave();
		String saved = "NOT SAVED";
		try {
			readCsvFile();
			saved = "SAVED";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saved;
	}

	public void readCsvFile() throws IOException {
		try (Reader reader = newBufferedReader(Paths.get(new ClassPathResource("sample.csv").getFile().toString()))){
			Iterator<PojoForTheCsvFile> iterator = getIterator(reader);
			while(iterator.hasNext()){
				PojoForTheCsvFile csvFile = iterator.next();
				buildObject(csvFile);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Iterator<PojoForTheCsvFile> getIterator(Reader reader) {
		CsvToBean<PojoForTheCsvFile> csvToBean = new CsvToBeanBuilder<PojoForTheCsvFile>(reader)
		.withType(PojoForTheCsvFile.class)
		.withIgnoreLeadingWhiteSpace(true)
		.withVerifyReader(true)
		.build();
		return csvToBean.iterator();
	}

	private void buildObject(PojoForTheCsvFile csvFile) {
		CsvEntity csvEntity = new CsvEntity();
		csvEntity.setName(csvFile.getName());
		csvEntity.setAge(csvFile.getAge());
		csvRepo.save(csvEntity);		
	}

}
