package com.se.controller;

import com.se.exception.ResourceNotFoundException;
import com.se.model.UserInfo;
import com.se.Model.UserProfile;
import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.PasswordSecurityService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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


    @PostMapping("/login")
    public ResponseEntity<String> getUser( @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           HttpSession session){
        Optional<UserInfo> userInfo = userInfoRepository.findInfoByEmail(email);
        if(userInfo.isPresent()){
            if (PasswordSecurityService.checkPass(password, userInfo.get().getPassword())) {
                session.setAttribute("id", userInfo.get().getId());
                return new ResponseEntity<String>(String.valueOf(userInfo.get().getId()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Wrong email or password" , HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/register")
    public ResponseEntity<Integer> addUser(@RequestParam("email") String email,
                                          @RequestParam("password") String password,
                                          HttpSession session){
        // Check if email address is from yale.edu, xiaoxiao.ouyang@yale.edu
        int correctIdx = email.length() - 9;
        if (email == null || email.length() <= 8 || email.lastIndexOf("@yale.edu") != correctIdx ) {
           return new ResponseEntity("Invalid Email Address", HttpStatus.BAD_REQUEST);
        }

        // check if the user email address already exisit
        Optional<UserInfo> userInfo = userInfoRepository.findInfoByEmail(email);
        if (userInfoRepository.findInfoByEmail(email).isPresent()) {
            return new ResponseEntity("User already exist", HttpStatus.CONFLICT);
        }

        UserInfo newUserInfo = UserInfo.builder().password(password).email(email).build();
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
    public ResponseEntity<String> updateUserProfile(@RequestParam("uid") String uid,
                                                    @RequestParam("gender") String genderS,
                                                    @RequestParam("major") String major,
                                                    @RequestParam("college") String college,
                                                    @RequestParam("age") String ageS,
                                                    @RequestParam("year") String year,
                                                    @RequestParam("availability") String availability,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("username") String username,
                                                    @RequestParam("share_gps") String shareGps){
        int id = Integer.parseInt(uid);
        int age = Integer.parseInt(ageS);
        String gender = genderS;
        Optional<UserProfile> profile = this.userProfileRepository.findProfileById(id);

        // Check if the profile associate with the id exists
        if (!profile.isPresent()) {
            return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        // Update the corresponding profile with new parameters
        UserProfile userProfile = UserProfile.builder()
                .id(id)
                .gender(gender)
                .major(major)
                .college(college)
                .age(age)
                .year(year)
                .availability(availability.charAt(0))
                .username(username)
                .shared_gps(shareGps)
                .description(description)
                .build();
        userProfileRepository.updateProfile(userProfile);
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);

    }

}
