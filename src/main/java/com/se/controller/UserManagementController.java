package com.se.controller;

import com.se.model.UserImage;
import com.se.model.UserProfile;
import com.se.model.UserInfo;
import com.se.repository.UserImageRepository;
import com.se.repository.UserInfoRepository;
import com.se.repository.UserProfileRepository;
import com.se.service.ImageService;
import com.se.security.PasswordSecurityService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserManagementController  {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserImageRepository userImageRepository;
    @Autowired
    private ImageService imageService;

    /**
     *
     * @return all the currently available user profiles
     */
    @GetMapping("/admin/profiles")
    public List<UserProfile> getAllProfile(){
        return this.userProfileRepository.findAllProfile();
    }


    /**
     *
     * @return all the currently registered users info
     */
    @GetMapping("/admin/users")
    public List<UserInfo> getAllUsers(){
        return this.userInfoRepository.findAllInfo();
    }


    /**
     *
     * @param email user email
     * @param password password
     * @param session
     * @return login in successfully if a information is correct
     */

    @PostMapping("/login")
    public ResponseEntity<String> getUser( @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           HttpSession session){
        Optional<UserInfo> userInfo = userInfoRepository.findInfoByEmail(email);
        if(userInfo.isPresent()){
            if (PasswordSecurityService.checkPass(password, userInfo.get().getPassword())) {
                //session.setAttribute("id", userInfo.get().getId());
                return new ResponseEntity<String>(String.valueOf(userInfo.get().getId()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Wrong email or password" , HttpStatus.UNAUTHORIZED);
    }


    /**
     *
     * @param email using yale email to register
     * @param password
     * @param session
     * @return
     */
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
        //session.setAttribute("id", newUserInfo.getId());
        return new ResponseEntity<Integer>(newUserInfo.getId(), HttpStatus.CREATED);

    }

    /**
     *
     * @param id user id
     * @return profile information of the user
     */

    @GetMapping("/userProfile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable("id") int id){
        Optional<UserProfile> profile = this.userProfileRepository.findProfileById(id);
        if(profile.isPresent()){
            return new ResponseEntity<UserProfile>(profile.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param uid
     * @param genderS
     * @param major
     * @param college
     * @param ageS
     * @param year
     * @param availability if the user wants himself to be recommended to others ("T" or "F")
     * @param description
     * @param username
     * @param shareGps if the user wants to share GPS ("T" or "F")
     * @return a message indicating if the profile is updated
     */
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

    /**
     *
     * @param id
     * @param response
     * @return
     * @throws IOException
     */

    @GetMapping("/userProfile/{id}/image")
    public ResponseEntity<String> getAvatar(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        UserImage userImage = userImageRepository.findById(id).get();
        if (userImage.getImage() != null) {
            byte[] byteArray = new byte[userImage.getImage().length];
            int i = 0;
            for (Byte wrappedByte: userImage.getImage()) {
                byteArray[i++] = wrappedByte;
            }
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
        return new ResponseEntity<>("Image rendered successfully", HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @param file
     * @return
     */
    @PostMapping("/userProfile/{id}/imageUpload")
    public ResponseEntity<String> uploadAvatar(@PathVariable("id") int id, @RequestParam("imagefile")MultipartFile file) {
        boolean isSaved = imageService.saveImageFile(id, file);
        return new ResponseEntity<>("Image uploaded "+isSaved, HttpStatus.OK);
    }

}
