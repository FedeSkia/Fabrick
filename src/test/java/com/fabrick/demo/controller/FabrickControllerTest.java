package com.fabrick.demo.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FabrickControllerTest {

    @LocalServerPort
    Integer port;

    public void check_balance_is_retrieved(){

    }

}