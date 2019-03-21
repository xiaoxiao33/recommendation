package com.se.controller;

import com.se.exception.ResourceNotFoundException;
import com.se.model.UserInfo;
import com.se.model.UserProfile;
import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:8100")
public class UserManagementController  {

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

    // Login using basic authentication.
    @GetMapping("/login")
    public ResponseEntity<String> getUser(HttpSession session){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserInfo> userInfo = userInfoRepository.findInfoByEmail(email);
        if(userInfo.isPresent()){
            session.setAttribute("id", userInfo.get().getId());
            return new ResponseEntity<String>(String.valueOf(userInfo.get().getId()), HttpStatus.OK);
        }

        return new ResponseEntity<>("User not found with id: " + userInfo.get().getId(), HttpStatus.NOT_FOUND);
    }


    @PostMapping("/register")
    public ResponseEntity<Integer> addUser(@RequestParam("username") String username,
                                          @RequestParam("email") String email,
                                          @RequestParam("password") String password,
                                          HttpSession session){
        // Check if email address is from yale.edu, xiaoxiao.ouyang@yale.edu
        int correctIdx = email.length() - 9;
        if (email == null || email.length() <= 8 || email.lastIndexOf("@yale.edu") != correctIdx ) {
           return new ResponseEntity("Invalid Email Address", HttpStatus.BAD_REQUEST);
        }

        // check if the user name and email address already exisit
        Optional<UserInfo> userInfo = userInfoRepository.findInfoByUsername(username);
        if (userInfoRepository.findInfoByUsername(username).isPresent() ||
                userInfoRepository.findInfoByEmail(email).isPresent()) {
            return new ResponseEntity("User already exist", HttpStatus.CONFLICT);
        }

        UserInfo newUserInfo = UserInfo.builder().username(username).password(password).email(email).build();
        // Save userInfo to userInfoRepository, userProfileRepository updated at the same time
        // ? handle failure of the API
        this.userInfoRepository.saveInfo(newUserInfo);

        // Save id to session
        session.setAttribute("id", newUserInfo.getId());
        return new ResponseEntity<Integer>(newUserInfo.getId(), HttpStatus.CREATED);

    }

    @GetMapping("/userProfile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable("id") int id){
        Optional<UserProfile> profile = this.userProfileRepository.findProfileById(id);
        if(profile.isPresent()){
            return new ResponseEntity<UserProfile>(profile.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfile userProfile, HttpSession session) {
        int id = (Integer)session.getAttribute("id");
        Optional<UserProfile> profile = this.userProfileRepository.findProfileById(id);

        // Check if the profile associate with the id exists
        if (!profile.isPresent()) {
            return new ResponseEntity<>("User not found with id: " + userProfile.getId(), HttpStatus.NOT_FOUND);
        }

        // Update the corresponding profile with new parameters
        userProfileRepository.updateProfile(userProfile);
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);

    }
}