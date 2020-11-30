package com.test.app.model;

import lombok.Data;

@Data
public class HolidayRequest {
	
	private String employee;
	private Integer nrOfHolidays;
	private String description;
}
