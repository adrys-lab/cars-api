package com.cars.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* API Controller, parent of all Controllers -> Mapped to v1, make inherit this version path
 * --> avoiding add v1 to all sub-controllers
*/
@RestController
@RequestMapping("v1")
public class ApiVersionController
{
}
