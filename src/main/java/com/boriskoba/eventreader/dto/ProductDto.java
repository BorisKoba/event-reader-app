package com.boriskoba.eventreader.dto;

import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class ProductDto {

	@NotBlank(message = MISSING_PRODUCT_TYPE)
	@XmlElement(name = "type")
	private String type;

	@DecimalMin(value = "0.01", message = INCORRECT_PRICE_VALUE)
	@XmlElement(name = "price")
	private BigDecimal price;

	@NotNull(message = MISSING_DATE)
	@XmlElement(name = "startDate")
	private String startDate;

	@NotNull(message = MISSING_DATE)
	@XmlElement(name = "endDate")
	private String endDate;

	public ProductDto() {
	}

	public ProductDto(String type, BigDecimal price, String startDate, String endDate) {
		this.type = type;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
