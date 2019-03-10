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
@CrossOrigin("http://localhost:8100")
public class UserManagementController  {

    private static int id = 1;

    private final UserProfileRepository userProfileRepository;
    private final UserInfoRepository userInfoRepository;

    public UserManagementController(UserProfileRepository userProfileRepository, UserInfoRepository userInfoRepository){
        super();
        this.userProfileRepository = userProfileRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/admin/profiles")
    public List<UserProfile> getAllProfile(){
        return this.userProfileRepository.findAllProfile();
    }

    @GetMapping("/admin/users")
    public List<UserInfo> getAllUsers(){
        return this.userInfoRepository.findAllInfo();
    }

    @GetMapping("/login/{username}")
    public ResponseEntity<String> getUser(@PathVariable("username") String username){

        Optional<UserInfo> profile = userInfoRepository.findInfoByUsername(username);
        if(profile.isPresent()){
            return new ResponseEntity<String>(String.valueOf(profile.get().getId()), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Profile not found with id: " + id);
    }


    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestParam("username") String username,
                                                @RequestParam("password") String password){
        // check for existing user
        Optional<UserInfo> profile = userInfoRepository.findInfoByUsername(username);
        if (profile.isPresent()) {
            return new ResponseEntity("User already exist", HttpStatus.CONFLICT);
        }
        UserInfo userInfo = UserInfo.builder().username(username).password(password).build();
        this.userInfoRepository.saveInfo(userInfo);
        return new ResponseEntity<String>(String.valueOf(userInfo.getId()), HttpStatus.CREATED);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable int id){
        Optional<UserProfile> profile = this.userProfileRepository.findProfileById(id);
        if(profile.isPresent()){
            return new ResponseEntity<UserProfile>(profile.get(), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("Profile not found with id: " + id);
    }

    @PostMapping("updateProfile/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@RequestBody UserProfile userProfile) {
        Optional<UserProfile> profile = this.userProfileRepository.findProfileById(userProfile.getId());
        if (profile.isPresent()) {
            userProfileRepository.updateProfile(userProfile);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Profile not found with id: " + id);
        }
    }

}