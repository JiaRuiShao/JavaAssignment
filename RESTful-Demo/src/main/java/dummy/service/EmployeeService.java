package dummy.service;

import dummy.dao.EmployeeDao;
import dummy.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void clear() {
        employeeDao.clear();
    }

    public Optional<Employee> findById(int id) {
        return employeeDao.get(id);
    }

    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    public void saveAll(List<Employee> employees) {employeeDao.saveAll(employees);}

    public List<Employee> getEmployeesByAge(int age) {
        return employeeDao.getAll().stream().filter(e -> e.getAge() >= age).collect(Collectors.toList());
    }

    public void addEmployee(String[] params) {
        employeeDao.save(new Employee(Integer.parseInt(params[0]), params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]), params[4]));
    }

    public void updateEmployee(String[] params) {
        findById(Integer.parseInt(params[0])).ifPresent(employee -> employeeDao.update(employee, params));
    }
}
