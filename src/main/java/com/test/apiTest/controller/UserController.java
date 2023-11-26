package com.test.apiTest.controller;

import com.test.apiTest.dto.UserInfoDTO;
import com.test.apiTest.model.UserInfo;
import com.test.apiTest.repository.UserRepository;
import com.test.apiTest.request.LoginRequest;
import com.test.apiTest.response.ApiResponse;
import com.test.apiTest.service.UserService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")

@RequestMapping("/api/auth/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


//    @PostMapping("/addUser")
//    public ResponseEntity<?>  addUser(@RequestBody UserInfo user) {
//        //UserInfo existingUser = userService.findByEmail(user.getEmail()).orElse(null);
////        UserInfo existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
////
////        if (existingUser != null) {
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
////        }
////        UserInfo createdUser = userService.createUser(user);
////        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with id: " + createdUser.getId());
//
//        UserInfo existingUserByEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
//        UserInfo existingUserByUsername = userRepository.findByName(user.getUsername()).orElse(null);
//
//        if (existingUserByEmail != null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
//        }
//
//        if (existingUserByUsername != null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
//        }
//
//        UserInfo createdUser = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with id: " + createdUser.getId());
//    }


    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserInfo user) {

        String responseMessage = null;
        HttpStatus status = null;
        //UserInfo existingUser = userService.findByEmail(user.getEmail()).orElse(null);
//        UserInfo existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
//
//        if (existingUser != null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
//        }
//        UserInfo createdUser = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with id: " + createdUser.getId());
        System.out.println(user.getUsername());
        UserInfo existingUserByEmail = userRepository.findByEmail(user.getEmail()).orElse(null);
        UserInfo existingUserByUsername = userRepository.findByUsername(user.getUsername()).orElse(null);

        if (existingUserByEmail != null) {
            responseMessage = "Email already exists";
            // CustomErrorResponse errorResponse = new CustomErrorResponse("Incorrect username or password");
            status = HttpStatus.BAD_REQUEST;
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        } else if (existingUserByUsername != null) {
            responseMessage = "Username already exists";
            // CustomErrorResponse errorResponse = new CustomErrorResponse("Incorrect username or password");
            status = HttpStatus.BAD_REQUEST;
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        } else if (existingUserByUsername == null && existingUserByEmail == null) {
            UserInfo createdUser = userService.createUser(user);
            responseMessage = "User created successfully with id: " + createdUser.getId();
            status = HttpStatus.OK;
            //return ResponseEntity.ok(user);

        }


        // return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with id: " + createdUser.getId());
        ApiResponse<T> apiResponse = new ApiResponse<>(status.value(), responseMessage, null);
        return ResponseEntity.status(status).body(apiResponse);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate user based on email and password (implement this logic)
        // ...

        // Fetch user details from the database using the email

        String responseMessage;
        HttpStatus status;
//        UserInfoDTO user = userService.doLogin(loginRequest.getUsername(),loginRequest.getPassword(),loginRequest.getEntity(),loginRequest.getUserType());

        UserInfoDTO user = userService.doLogin(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            // User found, return user details
            responseMessage = "login successfully";
            status = HttpStatus.OK;
            //return ResponseEntity.ok(user);
        } else {
            // User not found, return appropriate response (you can customize this)
            //return ResponseEntity.status(401).body("Incorrect username or password");
            responseMessage = "Incorrect username or password";
            // CustomErrorResponse errorResponse = new CustomErrorResponse("Incorrect username or password");
            status = HttpStatus.BAD_REQUEST;
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        }
//        ApiResponse<T> apiResponse = new ApiResponse<>(status.value(), responseMessage, null);
//        return ResponseEntity.status(status).body(apiResponse);
        ApiResponse<UserInfoDTO> apiResponse = new ApiResponse<>(status.value(), responseMessage, user);
        return ResponseEntity.status(status).body(apiResponse);

    }


    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers() {
        String responseMessage;
        HttpStatus status;
        List<UserInfoDTO> allUsers = userService.getAllUsers();
        if (allUsers != null) {
            responseMessage = "login successfully";
            status = HttpStatus.OK;
        } else {
            responseMessage = "something went wrong";
            status = HttpStatus.OK;
        }
        ApiResponse<?> apiResponse = new ApiResponse<>(status.value(), responseMessage, allUsers);
        return ResponseEntity.status(status).body(apiResponse);
    }

//    @GetMapping("/{username}")
//    public UserInfoDTO getUserById(@PathVariable String username) {
//        return userService.getUserByUsername(username);
//    }

    @GetMapping("/{id}")
    public UserInfoDTO getUserById(@PathVariable Long id) {
        System.out.println("=======----" + id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserInfoDTO user) {
        String responseMessage = null;
        HttpStatus status = null;
        com.test.apiTest.dto.UserInfoDTO userInfoDTO = userService.updateUser(id, user);
        responseMessage = "User Updated successfully with id: " + userInfoDTO.getId();
        status = HttpStatus.OK;
        // return userService.updateUser(id, user);
        ApiResponse<T> apiResponse = new ApiResponse<>(status.value(), responseMessage, null);
        return ResponseEntity.status(status).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        String responseMessage = null;
        HttpStatus status = null;
        userService.deleteUser(id);
        status = HttpStatus.OK;
        ApiResponse<T> apiResponse = new ApiResponse<>(status.value(), responseMessage, null);
        return ResponseEntity.status(status).body(apiResponse);
    }

    @GetMapping("api/getUserDetails/{username}")
    public ApiResponse<?> getUserByUsername(@PathVariable String username) {
        // System.out.println("=======----" + id);
        String responseMessage = null;
        HttpStatus status = null;
        UserInfoDTO userInfoDTO = userService.getByUsername(username);
        // responseMessage = "User Updated successfully with id: " + userInfoDTO.getId();
        status = HttpStatus.OK;
        // return userService.updateUser(id, user);
        ApiResponse apiResponse = new ApiResponse<>(status.value(), responseMessage, userInfoDTO);
        return apiResponse;
        //return
    }
}
