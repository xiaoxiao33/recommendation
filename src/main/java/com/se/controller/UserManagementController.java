package com.se.controller;

import com.se.exception.ResourceNotFoundException;
import com.se.model.UserInfo;
import com.se.model.UserProfile;
import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserManagementController  {

    private static int id = 1;

    private final UserProfileRepository userProfileRepository;
    private final UserInfoRepository userInfoRepository;

    public UserManagementController(UserProfileRepository userProfileRepository, UserInfoRepository userInfoRepository){
        super();
        this.userProfileRepository = userProfileRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/admin/profile")
    public List<UserProfile> getAllProfile(){
        return this.userProfileRepository.findAll();
    }

    @GetMapping("/admin/users")
    public List<UserInfo> getAllUsers(){
        return this.userInfoRepository.allUsers();
    }

    @GetMapping("/login/{username}")
    public ResponseEntity<String> getAllUsers(@PathVariable("username") String username){

        Optional<UserInfo> profile = userInfoRepository.findByUsername(username);
        if(profile.isPresent()){
            return new ResponseEntity<String>(String.valueOf(profile.get().getId()), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Profile not found with id: " + id);
    }


    // add check for existing user
    @PostMapping("/register")
    public ResponseEntity<String> addGuest(@RequestParam("username") String username,
                                                @RequestParam("password") String password){

        UserInfo userInfo = UserInfo.builder().id(id++).username(username).password(password).build();
        userInfoRepository.save(userInfo);
        if (this.userProfileRepository.addNewProfile(userInfo)) {
            return new ResponseEntity<String>(String.valueOf(userInfo.getId()), HttpStatus.CREATED);
        }

        return new ResponseEntity<String>("User already exist", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable int id){
        Optional<UserProfile> profile = this.userProfileRepository.findUserById(id);
        if(profile.isPresent()){
            return new ResponseEntity<UserProfile>(profile.get(), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Profile not found with id: " + id);
    }

}