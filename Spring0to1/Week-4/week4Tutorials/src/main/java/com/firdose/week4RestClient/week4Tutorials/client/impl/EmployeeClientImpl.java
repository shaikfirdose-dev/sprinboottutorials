package com.firdose.week4RestClient.week4Tutorials.client.impl;

import com.firdose.week4RestClient.week4Tutorials.advices.APIResponse;
import com.firdose.week4RestClient.week4Tutorials.client.EmployeeClient;
import com.firdose.week4RestClient.week4Tutorials.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmployeeClientImpl implements EmployeeClient {


//    @Qualifier("employeeRestClient")
    private final RestClient restClient;

    public EmployeeClientImpl(@Qualifier("restClient") RestClient restClient) {
        this.restClient = restClient;
    }

    private final Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDto> getAllEmployees() {
        try {

            APIResponse<List<EmployeeDto>> response = restClient.get()
                    .uri("employees/")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return response.getData();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        try{
            APIResponse<EmployeeDto> employeeDto = restClient.get()
                    .uri("employees/{id}", id)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employeeDto.getData();
        } catch (Exception e){
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        try{
            ResponseEntity<APIResponse<EmployeeDto>> createEmployee = restClient.post()
                                                            .uri("employees/")
                                                            .body(employeeDto)
                                                            .retrieve()
                                                            .onStatus(HttpStatusCode::is4xxClientError , (req, res) ->{
                                                                System.out.println(new String(res.getBody().readAllBytes()));
                                                                throw new IllegalArgumentException("Resource could not created");
                                                            })
                                                            .toEntity(new ParameterizedTypeReference<>() {
                                                            });
            return createEmployee.getBody().getData();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
