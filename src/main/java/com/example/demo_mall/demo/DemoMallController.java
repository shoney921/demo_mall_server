package com.example.demo_mall.demo;

import org.springframework.web.bind.annotation.*;

@ResponseBody
@RequestMapping("/demo")
@RestController
public class DemoMallController {

    @GetMapping("/test")
    public String getAllUser(@RequestParam String id) {
        System.out.println("id = " + id);
        return id;
    }

}
