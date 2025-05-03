package com.boriskoba.eventreader.entity;
import java.util.ArrayList;
import java.util.List;
import com.boriskoba.eventreader.dto.EventDto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Event {
    @Id
    private String id;
    private String type;
    private String insuredId;
    @ManyToOne
    private RequestDetails requestDetails;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public static Event fromDto(EventDto dto, RequestDetails requestDetails) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setType(dto.getType());
        event.setInsuredId(dto.getInsuredId());
        event.setRequestDetails(requestDetails);
        event.setProducts(dto.getProducts().stream()
                           .map(p -> Product.fromDto(p, event))
                           .toList());
        return event;
    }

    public EventDto toDto() {
        return new EventDto(id, type, insuredId, 
               products.stream().map(Product::toDto).toList());
    }
}
