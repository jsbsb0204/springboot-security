package com.springboot.security.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "name")


public class Product {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long number;

@Column(nullable = false)
private String name;

@Column(nullable = false)
private Integer price;

@Column(nullable = false)
private Integer stock;

private LocalDateTime createdAt;
private LocalDateTime updatedAt;


}
