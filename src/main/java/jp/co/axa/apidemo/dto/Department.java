package jp.co.axa.apidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Column(name="DEPARTMENT")
    private String department;

    @Column(name="COUNT")
    private Integer count;
}