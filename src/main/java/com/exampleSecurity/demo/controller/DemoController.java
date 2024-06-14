package com.exampleSecurity.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {
  
  @GetMapping
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello, World!");
  }

  @GetMapping("/admin")

  public ResponseEntity<String> sayHelloAdmin() {
    return ResponseEntity.ok("Hello, Admin!");
  }

  @GetMapping("/user")

  public ResponseEntity<String> sayHelloUser() {
    return ResponseEntity.ok("Hello, User!");
  }
}
