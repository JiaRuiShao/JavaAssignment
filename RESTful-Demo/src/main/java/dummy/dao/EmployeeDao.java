package dummy.dao;

import dummy.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class EmployeeDao implements Dao<Employee> {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeDao() {
        // employees.add(new Employee());
    }

    @Override
    public Optional<Employee> get(long id) {
        return Optional.ofNullable(employees.get((int) id));
    }

    @Override
    public List<Employee> getAll() {
        return employees;
    }

    public void saveAll(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public void save(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void update(Employee employee, String[] params) {
        employee.setId(Objects.requireNonNull(
                Integer.valueOf(params[0]), "Id cannot be null"));
        employee.setName(Objects.requireNonNull(
                params[1], "Name cannot be null"));
        employee.setSalary(Objects.requireNonNull(
                Integer.valueOf(params[2]), "Salary cannot be null"));
        employee.setAge(Integer.parseInt(params[3]));
        employee.setImg(params[4]);
        employees.add(employee);
    }

    @Override
    public void delete(Employee employee) {
        employees.remove(employee);
    }

    public void clear() {
        employees.clear();
    }
}
