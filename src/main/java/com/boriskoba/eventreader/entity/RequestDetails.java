package com.boriskoba.eventreader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RequestDetails {

    @Id
    private String id; 
    private LocalDateTime acceptDate;
    private String sourceCompany;
}
