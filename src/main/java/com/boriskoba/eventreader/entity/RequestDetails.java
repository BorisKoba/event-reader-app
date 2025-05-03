package com.boriskoba.eventreader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


import com.boriskoba.eventreader.dto.RequestDetailsDto;

@Entity
@Data
public class RequestDetails {
    @Id
    private String id;
    private String acceptDate;
    private String sourceCompany;

    public static RequestDetails fromDto(RequestDetailsDto dto) {
        RequestDetails rd = new RequestDetails();
        rd.setId(dto.getId());
        rd.setAcceptDate(dto.getAcceptDate());
        rd.setSourceCompany(dto.getSourceCompany());
        return rd;
    }

    public RequestDetailsDto toDto() {
        return new RequestDetailsDto(id, acceptDate, sourceCompany);
    }
}
