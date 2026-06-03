package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.EmployeeController;
import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.service.employee.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Version 1 implementation of {@link EmployeeController}.
 */
@Tag(name = "Employee", description = "Employee management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/v1/employees", version = "v1")
@RequiredArgsConstructor
public class EmployeeControllerV1 implements EmployeeController {

  private static final String ID_DESC = "Employee ID";

  private final EmployeeService employeeService;

  @Operation(
      summary = "Create a new employee",
      description = "Creates a new employee record and persists it to the database.")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public EmployeeDTO createEmployee(@Valid @RequestBody final EmployeeDTO dto) {
    log.debug("POST /employees");
    return employeeService.create(dto);
  }

  @Operation(
      summary = "Soft-delete an employee by ID",
      description = "Marks the employee as deleted without physically removing the record."
          + " The record can be restored later.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteEmployee(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("DELETE /employees/{}", id);
    employeeService.delete(id);
  }

  @Operation(
      summary = "Retrieve all employees",
      description = "Returns a list of all active (non-deleted) employee records.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public List<EmployeeDTO> getAllEmployees() {
    log.debug("GET /employees");
    return employeeService.findAll();
  }

  @Operation(
      summary = "Retrieve an employee by ID",
      description = "Returns the employee record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public EmployeeDTO getEmployeeById(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("GET /employees/{}", id);
    return employeeService.findById(id);
  }

  @Operation(
      summary = "Update an employee by ID",
      description = "Updates the mutable fields (username, password, role) of an existing employee.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public EmployeeDTO updateEmployee(
      @Parameter(description = ID_DESC) @PathVariable final Long id,
      @Valid @RequestBody final EmployeeDTO dto) {
    log.debug("PUT /employees/{}", id);
    return employeeService.update(id, dto);
  }
}
