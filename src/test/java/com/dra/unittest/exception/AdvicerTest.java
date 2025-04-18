package com.dra.unittest.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AdvicerTest {

    Advicer advicer = new Advicer();

    @Test
    public void handleNotFoundExp_shouldReturnExpectData(){

        NotFoundException notFoundException = new NotFoundException("SSS");
        ResponseEntity<?> response = advicer.handleNotFoundExp(notFoundException);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<String, String> responseData = (Map<String, String>) response.getBody();
        assertEquals("SSS", responseData.get("message"));
    }

}
