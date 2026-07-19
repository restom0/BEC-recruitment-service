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
import vn.unigap.api.dto.in.ResumeDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.ResumeDtoOut;
import vn.unigap.api.service.ResumeService;
import vn.unigap.common.controller.AbstractResponseController;

import java.util.HashMap;

@RestController
@SecurityRequirement(name = "rs")
@RequestMapping(value = "/resumes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ResumeController extends AbstractResponseController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Operation(
            summary = "Thêm mới resume",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = vn.unigap.common.response.ApiResponse.class
                                    )
                            )}
                    )
            }
    )
    @PostMapping(value = "")
    public ResponseEntity<?> createResume(@RequestBody @Valid ResumeDtoIn resumeDtoIn) {
        resumeService.createResume(resumeDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Cập nhật resume",
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
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateResume(@PathVariable Long id, @RequestBody @Valid ResumeDtoIn resumeDtoIn) {
        resumeService.updateResume(id, resumeDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.OK);
    }

    @Operation(
            summary = "Xóa resume",
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
    public ResponseEntity<?> deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.OK);
    }

    @Operation(
            summary = "Lấy thông tin resume theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseResume.class
                                    )
                            )}
                    )
            }
    )
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getResume(@PathVariable(value = "id") Long id) {
        return responseEntity(() -> {
            return this.resumeService.getResume(id);
        });
    }

    @Operation(
            summary = "Lấy danh sách resumes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponsePageResume.class
                                    )
                            )
                    )
            }
    )
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllResumes(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                          @RequestParam(value = "seekerId", required = false) Long seekerId) {
        return responseEntity(() -> {
            return this.resumeService.getAllResumes(page, pageSize, seekerId);
        });
    }
    private static class ResponseResume extends vn.unigap.common.response.ApiResponse<ResumeDtoOut> {
    }

    private static class ResponsePageResume extends vn.unigap.common.response.ApiResponse<PageDtoOut<ResumeDtoOut>> {
    }
}
