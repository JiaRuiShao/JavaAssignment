package dummy.controller;

import dummy.entity.Employee;
import dummy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = EmployeeController.BASE_URL)
public class EmployeeController {
    static final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private EmployeeService employeeService;
    private RestTemplate restTemplate;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        restTemplate = new RestTemplate();
    }

    @GetMapping(value = "/employee", consumes = "application/json")
    public void saveAll() {
        List<Employee> employees = Objects.requireNonNull(restTemplate.getForObject(BASE_URL + "/employee", EmployeeService.class)).getAll();
        employeeService.saveAll(employees);
    }

    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id, HttpServletResponse response) {
        return new ResponseEntity<Employee>(employeeService.findById(id).orElse(null), HttpStatus.OK);
    }

    @GetMapping(value = "/employee", consumes = "application/json")
    public ResponseEntity<List<Employee>> findByAge() {
        List<Employee> employees = Objects.requireNonNull(restTemplate.getForObject(BASE_URL + "/employee", EmployeeService.class)).getAll();
        return new ResponseEntity<List<Employee>>(employees.stream().filter(e -> e.getAge() >= 60).collect(Collectors.toList()), HttpStatus.OK);
    }

}
