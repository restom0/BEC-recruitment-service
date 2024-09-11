package vn.unigap.services;

import org.springframework.stereotype.Component;
import vn.unigap.entities.Employer;

import java.util.Optional;

@Component
public interface EmployerService {
    Iterable<Employer> getAllEmployers(Integer page,Integer pageSize);
    Employer getEmployer(Long id);
    Long countEmployers();
    Boolean addEmployer(String name,Integer provinceId,String email,String description);
    Boolean updateEmployer(Long id,String name,Integer provinceId,String description);
    Boolean deleteEmployer(Long id);

    Employer findEmployerByEmail(String email);
}
