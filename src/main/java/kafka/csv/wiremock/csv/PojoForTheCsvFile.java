package kafka.csv.wiremock.csv;

import com.opencsv.bean.CsvBindByName;

public class PojoForTheCsvFile {

	@CsvBindByName(column = "NAME", required = true)
	private String name;
	@CsvBindByName(column = "AGE", required = true)
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
