package com.ws.ps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleController
 *
 * @author Eric at 2025-02-03_20:39
 */
@RestController
public class SampleController {

    @GetMapping("/test")
    public String test01() {
        System.out.println("xxxxxxxxxxxxxxxxxxx");
        return "OK.......";
    }

    @GetMapping("/test02")
    public String test02(@RequestParam int id) {
        System.out.println("id=" + id);
        return "OKxxxxxxxx";
    }
}
