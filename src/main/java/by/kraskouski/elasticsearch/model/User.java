package by.kraskouski.elasticsearch.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {

    private Long id;
    private String firstname;
    private String lastname;
    private Integer age;

}
