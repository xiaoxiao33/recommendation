package com.se.database;

import com.se.repository.UserInfoRepository;
import com.se.repository.impl.ScheduleRepositoryImpl;
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
public class ScheduleRepositoryTest {

    ScheduleRepositoryImpl srImpl = new ScheduleRepositoryImpl();

    @Test
    public void testAddIntendSlot() {
        UserInfoRepository u = new UserInfoRepository();

        String email_t = "ha@yale.edu";
        UserInfo user = UserInfo.builder().password("woshidalao").email(email_t).build();
        u.saveInfo(user);
        email_t = "hi@yale.edu";
        user = UserInfo.builder().password("woshidalao").email(email_t).build();
        u.saveInfo(user);

        srImpl.addIntendSlot(2,"20190304-10", "20190304-12");

        assertEquals(srImpl.findByMatchedSlot (1, "20190304-10", "20190304-11").get(0), Integer.valueOf(2));
        assertEquals(srImpl.findByMatchedSlot (1, "20190304-09", "20190304-13").get(0), Integer.valueOf(2));
        assertEquals(srImpl.findByMatchedSlot (1, "20190304-01", "20190304-03").size(), 0);
        assertEquals(srImpl.findByMatchedSlot (1, "20190304-15", "20190304-18").size(), 0);
        assertEquals(srImpl.findByMatchedSlot (1, "20190304-09", "20190304-11").get(0), Integer.valueOf(2));
        assertEquals(srImpl.findByMatchedSlot (1, "20190304-11", "20190304-14").get(0), Integer.valueOf(2));

    }

    @Test
    public void testAddSlot(){
        srImpl.addSlot(2,"20190304-10", "20190304-12");
        assertEquals(srImpl.findByNonConlictSlot(1, "20190304-13", "20190304-15").get(0), Integer.valueOf(2));
        assertEquals(srImpl.findByNonConlictSlot(1, "20190304-07", "20190304-09").get(0), Integer.valueOf(2));
        assertEquals(srImpl.findByNonConlictSlot(1, "20190304-11", "20190304-12").size(), 0);
        assertEquals(srImpl.findByConflictSlot(1, "20190304-11", "20190304-13").get(0), Integer.valueOf(2));
    }

    @Test
    public void testDeleteExpiredBusySlots(){
        srImpl.addSlot(2,"20190303-10", "20190303-12");
        srImpl.deleteExpiredBusySlots("20190304-00");

        assertEquals(srImpl.findByConflictSlot(1, "20190303-10", "20190303-12").size(), 0);

    }
}