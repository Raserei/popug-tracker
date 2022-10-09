package com.raserei.popugjira.authserv.rest;

import com.raserei.popugjira.authserv.exception.UserNotFoundException;
import com.raserei.popugjira.authserv.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users",
        produces = "application/json",
        consumes = "application/json")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;


    @PostMapping(path = "/")
    public void createUser(@RequestBody UserCreationDto userCreationDto) {
        userService.createUser(userCreationDto);
    }

    @GetMapping(path = "/")
    public List<UserDto> getUsers() {
        return userService.getUserList();
    }

    @GetMapping(path = "/{userId}")
    public UserDto getUserById (@PathVariable(name = "userId") String userId) throws UserNotFoundException {
        return userService.getUser(userId);
    }

    @DeleteMapping(path = "/{userId}")
    public void deleteUser (@PathVariable(name = "userId") String userId) throws UserNotFoundException {
        userService.deleteUser(userId);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
