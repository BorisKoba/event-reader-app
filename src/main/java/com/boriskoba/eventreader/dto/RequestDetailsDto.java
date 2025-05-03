package com.boriskoba.eventreader.dto;

import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class RequestDetailsDto {

	@NotBlank(message = MISSING_ID)
	@XmlElement(name = "id")
	private String id;

	@NotNull(message = MISSING_DATE)
	@XmlElement(name = "acceptDate")
	private String acceptDate;

	@NotBlank(message = MISSING_COMPANY)
	@XmlElement(name = "sourceCompany")
	private String sourceCompany;

	public RequestDetailsDto() {
	}

	public RequestDetailsDto(String id, String acceptDate, String sourceCompany) {
		this.id = id;
		this.acceptDate = acceptDate;
		this.sourceCompany = sourceCompany;
	}
}
