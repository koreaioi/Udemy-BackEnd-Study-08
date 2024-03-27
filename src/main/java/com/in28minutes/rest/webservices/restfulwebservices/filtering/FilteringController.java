package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Filter;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering(){
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        MappingJacksonValue mappingJacksonValue = getMappingJacksonValue(new MappingJacksonValue(someBean), "field1","field3");

        return mappingJacksonValue;
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList(){
        List<SomeBean> someBeans = Arrays.asList(new SomeBean("value1", "value2", "value3")
                , new SomeBean("value4", "value5", "value6"));

        MappingJacksonValue mappingJacksonValue = getMappingJacksonValue(new MappingJacksonValue(someBeans), "field2");
        return mappingJacksonValue;
    }

    private static MappingJacksonValue getMappingJacksonValue(MappingJacksonValue someBeans, String... strings) {
        MappingJacksonValue mappingJacksonValue = someBeans;
        //필터 조건 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(strings);
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }




}
