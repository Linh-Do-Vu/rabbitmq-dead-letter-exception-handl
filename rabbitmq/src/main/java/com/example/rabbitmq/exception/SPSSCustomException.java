package com.example.rabbitmq.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SPSSCustomException extends Exception {
    private String error ;
    private static final long serialVersionUID = -3154618962130084535L;
}
