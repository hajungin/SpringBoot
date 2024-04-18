package com.example.basiccrud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Member {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;
    private int age;
    @Column(name = "address", length = 100)
    private String addr;
//    spring.jpa.hibernate.ddl-auto=validate
//    validate 대신 create를 사용하면 이전에 데이터들이 모두 날아가고 생성이 되어서 매우 위험하다
}
