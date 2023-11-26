package com.test.apiTest.service;

import com.test.apiTest.dto.UserInfoDTO;
import com.test.apiTest.exception.ResourceNotFoundException;
import com.test.apiTest.model.UserInfo;
import com.test.apiTest.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserInfo createUser(UserInfo user) {
        // You can add validation logic here (e.g., check if the email is unique)
        return userRepository.save(user);
    }

    //    public UserInfoDTO doLogin(String username, String password, String entity, String role) {
    public UserInfoDTO doLogin(String username, String password) {

//    UserInfo user = userRepository.loginCheck(username, password, entity, role);
        UserInfo user = userRepository.loginCheck(username, password);

        if (user != null) {
            // User found, return user details
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId((long) user.getId());
            userInfoDTO.setName(user.getName());
            userInfoDTO.setUsername(user.getUsername());
            userInfoDTO.setEmail(user.getEmail());
            userInfoDTO.setRole(user.getRole());
            userInfoDTO.setEntity(user.getEntity());
            return userInfoDTO;
        } else {

            return null;

        }
    }

    public List<UserInfoDTO> getAllUsers() {
        List<UserInfo> users = userRepository.findAllUser();

        List<UserInfoDTO> userInfoDTOS = new ArrayList<>();
        for (UserInfo user : users) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId((long) user.getId());
            userInfoDTO.setName(user.getName());
            userInfoDTO.setUsername(user.getUsername());
            userInfoDTO.setEmail(user.getEmail());
            userInfoDTO.setRole(user.getRole());
            userInfoDTO.setEntity(user.getEntity());
            userInfoDTOS.add(userInfoDTO);
        }
        return userInfoDTOS;
    }

//    public UserInfoDTO getUserById(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
//    }
//
//    public UserInfoDTO updateUser(Long id, UserInfoDTO userDetails) {
//        UserInfoDTO user = userRepository.findById(id);
//        return userOptional.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
//
//        user.setName(userDetails.getName());
//        user.setUsername(userDetails.getUsername());
//        user.setEmail(userDetails.getEmail());
//        // Set other properties as needed
//
//        return userRepository.save(user);
//    }
//
//    public void deleteUser(Long id) {
//        UserInfoDTO user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
//
//        userRepository.delete(user);
//    }


    public UserInfoDTO updateUser(Long id, UserInfoDTO user) {
        Optional<UserInfo> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserInfo existingUser = userOptional.get();
            existingUser.setName(user.getName());
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setRole(user.getRole());
            existingUser.setEntity(user.getEntity());
//            existingUser.setPassword(user.getPassword());

            UserInfo updatedUser = userRepository.save(existingUser);

            return convertToDto(updatedUser);
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    private UserInfoDTO convertToDto(UserInfo user) {
        return new UserInfoDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(),
                user.getRole(), user.getEntity());
    }

    public UserInfoDTO getUserById(Long id) {
        Optional<UserInfo> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            UserInfo existingUser = user.get();
            userInfoDTO.setId(existingUser.getId());
            userInfoDTO.setName(existingUser.getName());
            userInfoDTO.setUsername(existingUser.getUsername());
            userInfoDTO.setEmail(existingUser.getEmail());
            userInfoDTO.setRole(existingUser.getRole());
            userInfoDTO.setEntity(existingUser.getEntity());
            return userInfoDTO;
        } else {
            throw new ResourceNotFoundException("User", "id", id);
        }
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public void deleteUser(Long id) {
        // Check if user exists
        UserInfo user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }

    public UserInfoDTO getByUsername(String username) {
        Optional<UserInfo> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            UserInfo existingUser = user.get();
            userInfoDTO.setId(existingUser.getId());
            userInfoDTO.setName(existingUser.getName());
            userInfoDTO.setUsername(existingUser.getUsername());
            userInfoDTO.setEmail(existingUser.getEmail());
            userInfoDTO.setRole(existingUser.getRole());
            userInfoDTO.setEntity(existingUser.getEntity());
            return userInfoDTO;
        } else {
            throw new ResourceNotFoundException("User", "Username", username);
        }

    }
}
