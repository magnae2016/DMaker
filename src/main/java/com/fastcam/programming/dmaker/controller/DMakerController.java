package com.fastcam.programming.dmaker.controller;

import com.fastcam.programming.dmaker.dto.*;
import com.fastcam.programming.dmaker.exception.DMakerException;
import com.fastcam.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(@PathVariable String memberId) {
        log.info("GET /developers/{memberId} HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developers")
    public CreateDeveloper.Response createDevelopers(@Valid @RequestBody CreateDeveloper.Request request) {
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }

    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(@PathVariable String memberId, @Valid @RequestBody EditDeveloper.Request developer) {
        return dMakerService.editDeveloper(memberId, developer);
    }

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable String memberId) {
        return dMakerService.deleteDeveloper(memberId);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(DMakerException e,
                                               HttpServletRequest request) {
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());
        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }
}
