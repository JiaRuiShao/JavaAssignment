package dummy.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Employee {
    @JsonProperty("id")
    private int id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_salary")
    private long salary;
    @JsonProperty("employee_age")
    private int age;
    @JsonProperty("profile_image")
    private String img;
}
