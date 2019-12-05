package client.service;

import client.model.User;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class SpringRestClient {
    private static final String LOGIN_POST = "http://localhost:8080/login";
    private static final String LOCALHOST_8080 = "http://localhost:8080/";
    private static final String ADMIN = "http://localhost:8080/admin/";
    private static final String CREATE_USER_URL = "http://localhost:8080/add";
    private static final String EDIT = "/edit";
    private static final String DELETE = "/delete";
    private static final String URL_USERS = "http://localhost:8080/users";

    private static SpringRestClient client;

    RestTemplate restTemplate = new RestTemplate();

    private SpringRestClient(){
    }

    public static SpringRestClient getInstance(){
        if (client == null){
            client = new SpringRestClient();
        }
        return client;
    }

    public ResponseEntity<List<User>> getUsers() {

        return new ResponseEntity<>(Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(URL_USERS, User[].class))),HttpStatus.OK);
    }

    public String setLoginPost(String login,String password){
        System.out.println(login + " " + password);
        // Request Header
        HttpHeaders headers = new HttpHeaders();

        // Request Body
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<>();
        parametersMap.add("login", login);
        parametersMap.add("password", password);

        // Request Entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parametersMap, headers);

        // POST Login
        ResponseEntity<String> response = restTemplate.exchange(LOGIN_POST, //
                HttpMethod.POST, requestEntity, String.class);

        HttpHeaders responseHeaders = response.getHeaders();

        List<String> list = responseHeaders.get("Authorization");
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    public ResponseEntity<User> getUserById(int id) {
        User result = restTemplate.getForObject(LOCALHOST_8080 + id, User.class);
        System.out.println(result);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    public User getUserByLogin(String login) {
        System.out.println(restTemplate.getForObject(ADMIN + login, User.class));
        return restTemplate.getForObject(ADMIN + login, User.class);
    }

    public ResponseEntity<User> createUser(User newUser) {

        HttpEntity<User> entity = new HttpEntity<>(newUser);

        ResponseEntity<User> result
                = restTemplate.postForEntity(CREATE_USER_URL, entity, User.class);

        System.out.println("Status code:" + result.getStatusCode());
        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }

    public ResponseEntity<User> updateUser(int id,User updateUser) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> entity = new HttpEntity<>(updateUser,headers);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        restTemplate.put(LOCALHOST_8080 + id + EDIT, entity, id);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);

    }

    public void deleteUserById(int id) {
        restTemplate.delete(LOCALHOST_8080 + id + DELETE);
    }
}
