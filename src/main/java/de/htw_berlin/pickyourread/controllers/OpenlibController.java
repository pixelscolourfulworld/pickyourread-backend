package de.htw_berlin.pickyourread.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController 
@RequestMapping ("/api/openlib")
public class OpenlibController {


    @RequestMapping("/test")
    public String requestMethodName() {
        return new String("reched de.htw_berlin.pickyourread.api.OpenlibController at endpoint /api/openlib/test");
    }
    
}
