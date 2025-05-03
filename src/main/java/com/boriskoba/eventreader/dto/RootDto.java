package com.boriskoba.eventreader.dto;

import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class RootDto {

	@NotNull(message = MISSING_REQUEST_DETAILS)
	@XmlElement(name = "requestDetails")
	private RequestDetailsDto requestDetails;

	@NotNull(message = MISSING_LIST_EVENTS)
	@XmlElementWrapper(name = "events")
	@XmlElement(name = "event")
	private List<@NotNull EventDto> events;

	public RootDto() {
	}

	public RootDto(RequestDetailsDto requestDetails, List<EventDto> events) {
		this.requestDetails = requestDetails;
		this.events = events;
	}
}