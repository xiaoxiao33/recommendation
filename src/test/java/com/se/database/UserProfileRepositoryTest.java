package com.se.database;

import com.alibaba.fastjson.JSON;
import com.se.repository.UserProfileRepository;
import com.se.repository.UserInfoRepository;
import com.se.Model.UserProfile;
import com.se.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserProfileRepositoryTest {    
    @Test
    public void testID() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testID@yale.edu";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        ui.saveInfo(user);
        user.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        assertEquals(up.findProfileById(user.getId()).get().getId(), user.getId());
    }

    @Test
    public void testUsername() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testUsername@yale.edu";
        String username = "zhangjingkuan";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setUsername(username);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileByName(profile.getUsername()).get().getUsername(),username);
    }

    @Test
    public void testGender() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testGender@yale.edu";
        String gender = "male";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setGender(gender);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getGender(),gender);
    }

    @Test
    public void testMajor() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testMajor@yale.edu";
        String major = "CS";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setMajor(major);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getMajor(),major);
    }

    @Test
    public void testAge() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testAge@yale.edu";
        int age = 2;
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setAge(age);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getAge(),age);
    }

    @Test
    public void testYear() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testYear@yale.edu";
        String year = "2019";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setYear(year);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getYear(),year);
    }

    @Test
    public void testEmail() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testEmail@yale.edu";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        ui.saveInfo(user);
        user.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        assertEquals(up.findProfileById(user.getId()).get().getEmail(), user.getEmail());
    }

    @Test
    public void testGPS() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testGPS@yale.edu";
        String gps = "14*23";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setShared_gps(gps);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getShared_gps(),gps);
    }

    @Test
    public void testDescription() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testDescription@yale.edu";
        String description = "hahahahaha";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setDescription(description);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getDescription(),description);
    }

    @Test
    public void testAvailability() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testAvailability@yale.edu";
        char availability = '1';
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setAvailability(availability);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getAvailability(),availability);
    }

    @Test
    public void testCollege() throws Exception{
        UserProfileRepository up = new UserProfileRepository();
        UserInfoRepository ui = new UserInfoRepository();
        String email_t = "testCollege@yale.edu";
        String college = "Morse";
        UserInfo user = UserInfo.builder().password("woshijulao").email(email_t).build();
        UserProfile profile = new UserProfile(user);
        profile.setCollege(college);
        ui.saveInfo(user);
        profile.setId(ui.findInfoByEmail(user.getEmail()).get().getId());
        up.updateProfile(profile);
        assertEquals(up.findProfileById(profile.getId()).get().getCollege(),college);
    }
}