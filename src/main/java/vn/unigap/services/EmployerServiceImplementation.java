package vn.unigap.services;

import org.springframework.stereotype.Component;
import vn.unigap.entities.Employer;
import vn.unigap.exceptions.ConflictException;
import vn.unigap.exceptions.NotFoundException;
import vn.unigap.repositories.EmployerRepository;

import java.util.Date;
import java.util.Optional;
@Component
public class EmployerServiceImplementation implements EmployerService{
    private final EmployerRepository employerRepository;

    public EmployerServiceImplementation(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Override
    public Iterable<Employer> getAllEmployers(Integer page,Integer pageSize){
        return this.employerRepository.findAllOffsetLimit((page-1)*pageSize,pageSize);
    }
    @Override
    public Long countEmployers(){
        return this.employerRepository.count();
    }
    @Override
    public Employer getEmployer(Long id){
        if(this.employerRepository.count()==0){
            throw new NotFoundException("Employer doesn't exist");
        }
        Optional<Employer> searchEmployer = this.employerRepository.findById(id);
        if(searchEmployer.isEmpty()){
            throw new NotFoundException("Employer doesn't exist");
        }
        return searchEmployer.get();
    }
    @Override
    public Boolean addEmployer(String name,Integer provinceId,String email,String description){
        Optional<Employer> searchEmployer=this.employerRepository.findByEmail(email);
        if(searchEmployer.isPresent()){
            throw new ConflictException("Email has already been used");
        }
        Employer newEmployer = new Employer(email,name,provinceId,description,new Date(),new Date());
        this.employerRepository.save(newEmployer);
        return true;
    }
    @Override
    public Boolean updateEmployer(Long id,String name,Integer provinceId,String description){
        Optional<Employer> searchEmployer = this.employerRepository.findById(id);
        if(searchEmployer.isEmpty()){
            throw new NotFoundException("Employer doesn't exist");
        }
        Employer newEmployer = searchEmployer.get();
        newEmployer.setName(name);
        newEmployer.setProvince(provinceId);
        newEmployer.setDescription(description);
        newEmployer.setUpdated_at(new Date());
        this.employerRepository.save(newEmployer);
        return true;
    }
    @Override
    public Boolean deleteEmployer(Long id){
        Optional<Employer> searchEmployer = this.employerRepository.findById(id);
        if(searchEmployer.isEmpty()){
            throw new NotFoundException("Employer doesn't exist");
        }
        this.employerRepository.deleteById(id);
        return true;
    }
    @Override
    public Employer findEmployerByEmail(String email){
        Optional<Employer> searchEmployer=this.employerRepository.findByEmail(email);
        if(searchEmployer.isEmpty()){
            throw new NotFoundException("Email is not existed");
        }
        return searchEmployer.get();
    };
}
