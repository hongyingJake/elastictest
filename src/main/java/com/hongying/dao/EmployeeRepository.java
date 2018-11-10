package com.hongying.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.hongying.beans.Employee;

@Component
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {
}
