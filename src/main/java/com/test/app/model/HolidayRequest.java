package com.test.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HolidayRequest {
	
	private String employee;
	private Integer nrOfHolidays;
	private String description;
}
