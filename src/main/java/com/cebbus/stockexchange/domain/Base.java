package com.cebbus.stockexchange.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class Base {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}