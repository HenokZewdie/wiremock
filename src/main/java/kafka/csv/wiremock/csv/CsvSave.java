package kafka.csv.wiremock.csv;

import static java.nio.file.Files.newBufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import kafka.csv.wiremock.repo.CsvRepo;

public class CsvSave {

	@Autowired
	private CsvRepo csvRepo;

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
