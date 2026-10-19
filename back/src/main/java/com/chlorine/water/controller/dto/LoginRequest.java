package com.chlorine.water.controller.dto;

import javax.validation.constraints.NotBlank;   // Spring Boot 3.x 换成 jakarta.validation
import lombok.Data;
/**
 * @author chlorine
 * @version 1.0
 * @Date 2026-10-19
 * @Description
 */

@Data
public class LoginRequest {
    @NotBlank(message = "账号不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String password;
}