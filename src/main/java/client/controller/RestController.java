package client.controller;

import client.model.User;
import client.service.SpringRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private SpringRestClient client = SpringRestClient.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @PostMapping(value = "/edit")
    public ResponseEntity<User> edit(@RequestBody User user) {

        client.updateUser(user.getId(),user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public  ResponseEntity<List<User>> getAll() {
        return client.getUsers();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return client.getUserById(id);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        logger.info(user.toString());
        client.createUser(user);
        logger.info(user.toString() + " - Created");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<User> delete(@PathVariable("id") int id) {
        client.deleteUserById(id);
        logger.info("№ " + id + " - удален");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/edit")
    public void edit(@PathVariable("id") int id, User user) {
        logger.info(user.toString() + "\n");
        client.updateUser(user.getId(),user);
        logger.info(user.toString() + " - Edited");

    }
}
