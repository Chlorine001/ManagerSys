package com.chlorine.water.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemInfoController {
    
    @Autowired
    private Environment environment;
    
    @GetMapping("/profile")
    public Map<String, String> getProfile() {
        Map<String, String> result = new HashMap<>();
        String[] profiles = environment.getActiveProfiles();
        result.put("activeProfile", profiles.length > 0 ? profiles[0] : "default");
        return result;
    }
}
