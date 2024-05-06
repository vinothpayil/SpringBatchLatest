package com.vinothtechie.springbatchlatest.config;

import com.vinothtechie.springbatchlatest.entity.Employee;
import com.vinothtechie.springbatchlatest.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class EmployeeWriter implements ItemWriter<Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        employeeRepository.saveAll(chunk);
    }
}
