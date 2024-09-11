package vn.unigap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.dto.in.AddEmployerRequest;
import vn.unigap.dto.in.UpdateEmployerRequest;
import vn.unigap.dto.out.PagingResponse;
import vn.unigap.dto.out.ResponseHandler;
import vn.unigap.entities.Employer;
import vn.unigap.exceptions.BadRequestException;
import vn.unigap.exceptions.InternalServerErrorException;
import vn.unigap.exceptions.MissingServletRequestParameterException;
import vn.unigap.exceptions.NotFoundException;
import vn.unigap.services.EmployerService;

@RestController
@RequestMapping("/employers")
public class EmployerController {

    @Autowired
    private EmployerService employerService;
    @GetMapping
    public ResponseEntity<Object> getAllEmployers(@RequestParam Integer page, @RequestParam Integer pageSize){
        if(page==null){
            throw new MissingServletRequestParameterException("");
        }
        if(pageSize==null){
            throw new MissingServletRequestParameterException("");
        }
        if(page<=0){
            throw new BadRequestException("Invalid page");
        }
        if(pageSize<=0 || pageSize>500){
            throw new BadRequestException("Invalid page's size");
        }
        Iterable<Employer>employers= employerService.getAllEmployers(page,pageSize);
        Long count =this.employerService.countEmployers();
        return ResponseHandler.responseBuilder(null,200,null, new PagingResponse(page,pageSize,count, employers), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployer(@PathVariable("id") Long id){
        if(id<=0){
            throw new BadRequestException("Invalid id");
        }
        return ResponseHandler.responseBuilder(null,200,null,employerService.getEmployer(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Object> addEmployer(@RequestBody AddEmployerRequest addEmployerRequest){
        if(addEmployerRequest.getName()==null){
            throw new BadRequestException("name is missing");
        }
        if(addEmployerRequest.getProvinceId()==null){
            throw new BadRequestException("province's id is missing");
        }
        if(addEmployerRequest.getEmail()==null){
            throw new BadRequestException("email is missing");
//            throw new MissingServletRequestParameterException("");
        }
        String name= addEmployerRequest.getName();
        Integer provinceId= addEmployerRequest.getProvinceId();
        String email= addEmployerRequest.getEmail();
        String description= addEmployerRequest.getDescription();
        if(!email.contains("@")||email.length()>255){
            throw new BadRequestException("Invalid email");
        }
        if (name.length()>255){
            throw new BadRequestException("Invalid name");
        }
        Boolean result = this.employerService.addEmployer(name,provinceId,email,description);
        if(result){
            return ResponseHandler.responseBuilder(null,201,null,null, HttpStatus.CREATED);
        }
        throw new InternalServerErrorException("Server Error");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployer(@PathVariable("id") Long id, @RequestBody UpdateEmployerRequest updateEmployerRequest){
        if(id==null){
            throw new MissingServletRequestParameterException("");
        }
        if(updateEmployerRequest.getName()==null){
            throw new BadRequestException("name is missing");
        }
        if(updateEmployerRequest.getProvinceId()==null){
            throw new BadRequestException("province's id is missing");
        }
        if (updateEmployerRequest.getName().length()>255){
            throw new BadRequestException("Invalid name");
        }
        String name= updateEmployerRequest.getName();
        Integer provinceId = updateEmployerRequest.getProvinceId();
        String description=updateEmployerRequest.getDescription();
        Boolean result = this.employerService.updateEmployer(id,name,provinceId,description);
        if(result){
            return ResponseHandler.responseBuilder(null,200,null,null, HttpStatus.OK);
        }
        throw new InternalServerErrorException("Server Error");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployer(@PathVariable("id") Long id){
        if(id==null){
            throw new MissingServletRequestParameterException("");
        }
        Boolean result = this.employerService.deleteEmployer(id);
        if(result){
            return ResponseHandler.responseBuilder(null,200,null,null, HttpStatus.OK);
        }
        throw new InternalServerErrorException("Server Error");
    }
}
