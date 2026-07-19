package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.controller.AbstractResponseController;
import vn.unigap.common.exception.ApiException;

import java.util.HashMap;

@RestController
@SecurityRequirement(name = "rs")
@RequestMapping(value = "/employers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployerController extends AbstractResponseController {

    private static final Logger logger = LogManager.getLogger(EmployerController.class);

    private final EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @Operation(
            summary = "Thêm mới employer",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseEmployer.class
                                    )
                            )}
                    )
            }
    )
    @PostMapping(value = "")
    public ResponseEntity<?> createEmployer(@RequestBody @Valid EmployerDtoIn employerDtoIn) {
        logger.info("Creating employer with data: " + employerDtoIn);
        employerService.createEmployer(employerDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Cập nhật user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseEmployer.class
                                    )
                            )}
                    )
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEmpployer(@PathVariable(value = "id") Long id,
                                    @RequestBody @Valid EmployerDtoIn employerDtoIn) {
        logger.info("Updating employer with id: " + id);
        employerService.updateEmployer(id, employerDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.OK);
    }

    @Operation(
            summary = "Lấy thông tin employer theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseEmployer.class
                                    )
                            )}
                    )
            }
    )
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getEmployer(@PathVariable(value = "id") Long id) {
        logger.info("Fetching employer with id: " + id);
        return responseEntity(() -> {
            return this.employerService.getEmployer(id);
        });
    }

    @Operation(
            summary = "Xóa employer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = vn.unigap.common.response.ApiResponse.class
                                    )
                            )}
                    )
            }
    )
    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> deleteEmployer(@PathVariable(value = "id") Long id) {
        logger.info("Deleting employer with id: " + id);
        return responseEntity(() -> {
            this.employerService.deleteEmployer(id);
            return new HashMap<>();
        });
    }

    @Operation(
            summary = "Lấy danh sách employers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponsePageEmployer.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllEmployers(@Valid PageDtoIn pageDtoIn) {
        logger.info("Fetching all employers");
        return responseEntity(() -> {
            return this.employerService.getAllEmployers(pageDtoIn);
        });
    }

    private static class ResponseEmployer extends vn.unigap.common.response.ApiResponse<EmployerDtoOut> {
    }

    private static class ResponsePageEmployer extends vn.unigap.common.response.ApiResponse<PageDtoOut<EmployerDtoOut>> {
    }
}