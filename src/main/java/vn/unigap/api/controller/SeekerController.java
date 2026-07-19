package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.in.SeekerDtoIn;
import vn.unigap.api.dto.out.PageDtoOut;
import vn.unigap.api.dto.out.SeekerDtoOut;
import vn.unigap.api.service.SeekerService;
import vn.unigap.common.controller.AbstractResponseController;

import java.util.HashMap;

@RestController
@SecurityRequirement(name = "rs")
@RequestMapping("/seekers")
public class SeekerController extends AbstractResponseController {

    private final SeekerService seekerService;

    @Autowired
    public SeekerController(SeekerService seekerService) {
        this.seekerService = seekerService;
    }

    @Operation(
            summary = "Thêm mới seeker",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseSeeker.class
                                    )
                            )}
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> createSeeker(@RequestBody SeekerDtoIn seekerDtoIn) {
        seekerService.createSeeker(seekerDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Cập nhật seeker",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseSeeker.class
                                    )
                            )}
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeeker(@PathVariable Long id, @RequestBody SeekerDtoIn seekerDtoIn) {
        seekerService.updateSeeker(id, seekerDtoIn);
        return responseEntity(() -> {
            return new HashMap();
        }, HttpStatus.OK);
    }

    @Operation(
            summary = "Lấy thông tin seeker theo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseSeeker.class
                                    )
                            )}
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getSeeker(@PathVariable Long id) {
        return responseEntity(() -> {
            return this.seekerService.getSeeker(id);
        });
    }

    @Operation(
            summary = "Xóa seeker",
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeeker(@PathVariable Long id) {
        return responseEntity(() -> {
            this.seekerService.deleteSeeker(id);
            return new HashMap<>();
        });
    }

    @Operation(
            summary = "Lấy danh sách seekers",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResponsePageSeeker.class
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<?> getAllSeekers(@RequestParam int page, @RequestParam int pageSize, @RequestParam Long provinceId) {
        return responseEntity(() -> {
            return this.seekerService.getAllSeekers(page, pageSize, provinceId);
        });
    }

    private static class ResponseSeeker extends vn.unigap.common.response.ApiResponse<SeekerDtoOut> {
    }

    private static class ResponsePageSeeker extends vn.unigap.common.response.ApiResponse<PageDtoOut<SeekerDtoOut>> {
    }
}