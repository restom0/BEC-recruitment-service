package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.in.PageDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.service.JobService;
import vn.unigap.common.controller.AbstractResponseController;

import java.util.HashMap;


@RestController
@SecurityRequirement(name = "rs")
@RequestMapping(value = "/jobs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class JobController  extends AbstractResponseController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(
            summary = "Thêm mới job",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseJob.class
                                    )
                            )}
                    )
            }
    )
    @PostMapping(value = "")
    public ResponseEntity<?> createJob(@RequestBody @Valid JobDtoIn jobDtoIn) {
        jobService.createJob(jobDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Cập nhật job",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseJob.class
                                    )
                            )}
                    )
            }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody @Valid JobDtoIn jobDtoIn) {
        jobService.updateJob(id, jobDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.OK);
    }

    @Operation(
            summary = "Lấy thông tin job theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseJob.class
                                    )
                            )}
                    )
            }
    )
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getJob(@PathVariable(value = "id") Long id) {
        return responseEntity(() -> {
            return this.jobService.getJob(id);
        });
    }

    @Operation(
            summary = "Xóa job",
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
    public ResponseEntity<?> deleteJob(@PathVariable(value = "id") Long id) {
        return responseEntity(() -> {
            this.jobService.deleteJob(id);
            return new HashMap<>();
        });
    }

    @Operation(
            summary = "Lấy danh sách jobs",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponsePageJob.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllJobs(@Valid PageDtoIn pageDtoIn, @RequestParam(required = false, defaultValue = "-1") Long employerId) {
        return responseEntity(() -> {
            return this.jobService.getAllJobs(pageDtoIn.getPage(), pageDtoIn.getPageSize(), employerId);
        });
    }

    private static class ResponseJob extends vn.unigap.common.response.ApiResponse<JobDtoOut> {
    }

    private static class ResponsePageJob extends vn.unigap.common.response.ApiResponse<PageDtoOut<JobDtoIn>> {
    }
}