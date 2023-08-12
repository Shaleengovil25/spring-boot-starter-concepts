package com.shikhakunj.udemymicroservicestutorial.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	// static filtering -> add @JsonIgnore on bean's field or 
	// @JsonIgnoreProperties on bean's class
	@GetMapping("/static-filtering")
	public Somebean StaticFilter() {
		return new Somebean("value1","value2","value3");
	}
	
	@GetMapping("/static-filtering-list")
	public List<Somebean> filterList() {
		return Arrays.asList(new Somebean("value1","value2","value3"), new Somebean("value4","value5","value6"));
	}
	
	// dynamic filtering -> mappingJacksonValue
	@GetMapping("/dynamic-filtering")
	public MappingJacksonValue dynamicFilter() {
		Somebean somebean = new Somebean("value1","value2","value3");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(somebean);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter );
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	@GetMapping("/dynamic-filtering-list")
	public MappingJacksonValue dynamicFilterList() {
		List<Somebean> list= Arrays.asList(new Somebean("value1","value2","value3"), new Somebean("value4","value5","value6"));
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter );
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

}
