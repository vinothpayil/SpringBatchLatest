package com.vinothtechie.springbatchlatest.config;

import com.vinothtechie.springbatchlatest.dto.EmployeeDTO;
import com.vinothtechie.springbatchlatest.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeProcessor implements ItemProcessor<EmployeeDTO, Employee> {
    @Override
    public Employee process(EmployeeDTO employeeDTO) throws Exception {
        if(employeeDTO.jobTitle().equals("Vice President")){
            return null;
        }
        return mapEmployee(employeeDTO);
    }

    private Employee mapEmployee(EmployeeDTO employeeDTO) {
        return  Employee.builder()
                .employeeId(employeeDTO.employeeId())
                .fullName(employeeDTO.fullName())
                .jobTitle(employeeDTO.jobTitle())
                .department(employeeDTO.department())
                .businessUnit(employeeDTO.businessUnit())
                .gender(employeeDTO.gender())
                .ethnicity(employeeDTO.ethnicity())
                .age(employeeDTO.age())
                .build();
    }
}
