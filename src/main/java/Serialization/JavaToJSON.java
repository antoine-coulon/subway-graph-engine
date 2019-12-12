package Serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class JavaToJSON {

    private ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        try {
            new JavaToJSON().JSONtoJava();
        } catch(IOException e) {
           throw new Exception(e);
        }
    }


    private void JSONtoJava() throws IOException {
       Person person = objectMapper.readValue(new File("D://Users//Coulo//Documents//ISEP//A2//AAV//project//person.txt"), Person.class);
       System.out.println(person.toString());
    }


    private void javaToJSON() throws IOException {
        Person person = mockPerson();
       // objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("D://Users//Coulo//Documents//ISEP//A2//AAV//project//person.txt"), person);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);
        System.out.println(json);
    }

    private Person mockPerson() {
        Person person = new Person();
        person.setId(1563465);
        person.setName("COULON");
        person.setSalary(20000.00);
        person.setDate(new Date());
        return person;
    }

}
