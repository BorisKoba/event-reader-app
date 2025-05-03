package com.boriskoba.eventreader.dto;

import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class EventDto {

	@NotBlank(message = MISSING_ID)
	@XmlElement(name = "id")
	private String id;

	@NotBlank(message = MISSING_PRODUCT_TYPE)
	@XmlElement(name = "type")
	private String type;

	@NotBlank(message = MISSING_ID)
	@XmlElement(name = "insuredId")
	private String insuredId;

	@NotNull(message = MISSING_LIST_PRODUCTS)
	@XmlElementWrapper(name = "products")
	@XmlElement(name = "product")
	private List<@NotNull ProductDto> products;

	public EventDto() {
	}

	public EventDto(String id, String type, String insuredId, List<ProductDto> products) {
		this.id = id;
		this.type = type;
		this.insuredId = insuredId;
		this.products = products;
	}
}
