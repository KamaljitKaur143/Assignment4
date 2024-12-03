package com.example.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiTest {

    // Test Case 1: Verify Status Code
    @Test
    public void testStatusCode() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
    }

    // Test Case 2: Validate Response Body Contains Key
    @Test
    public void testResponseBodyContainsKey() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
        Assert.assertTrue(response.getBody().asString().contains("userId"), "Response does not contain 'userId'");
    }

    // Test Case 3: Test All Posts Endpoint
    @Test
    public void testAllPostsEndpoint() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
        Assert.assertTrue(response.getBody().jsonPath().getList("$").size() > 0, "Response list is empty");
    }

    // Test Case 4: Test Invalid Endpoint
    @Test
    public void testInvalidEndpoint() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/invalid-endpoint");
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code is 404");
    }

    // Test Case 5: POST New Data
    @Test
    public void testPostNewData() {
        Response response = RestAssured.given()
            .contentType("application/json")
            .body("{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}")
            .post("https://jsonplaceholder.typicode.com/posts");
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code is 201");
        Assert.assertTrue(response.getBody().asString().contains("\"id\":"), "Response does not contain 'id'");
    }

    // Test Case 6: PUT Request
    @Test
    public void testPutRequest() {
        Response response = RestAssured.given()
            .contentType("application/json")
            .body("{\"id\": 1, \"title\": \"updated title\", \"body\": \"updated body\", \"userId\": 1}")
            .put("https://jsonplaceholder.typicode.com/posts/1");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
        Assert.assertTrue(response.getBody().asString().contains("updated title"), "Response does not contain 'updated title'");
    }

    // Test Case 7: DELETE Request
    @Test
    public void testDeleteRequest() {
        Response response = RestAssured.delete("https://jsonplaceholder.typicode.com/posts/1");
        Assert.assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 204, "Expected status code is 200 or 204");
    }

    
    // Test Case 8: Check Response Time
    @Test
    public void testResponseTime() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
        Assert.assertTrue(response.getTime() < 2000, "Response time exceeds 2000 ms");
    }

    // Test Case 9: Verify Content-Type
    @Test
    public void testContentType() {
        Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");
        Assert.assertEquals(response.contentType(), "application/json; charset=utf-8", "Expected content-type is application/json");
    }
}
