package com.se.service;

import com.se.model.UserLocation;
import com.se.repository.LocationRepository;
import com.se.repository.ScheduleRepository;
import com.se.service.impl.RecommendationServiceImpl;
import com.se.vo.IntendVO;
import com.se.vo.RealTimeLocationVO;
import com.se.vo.UserBriefVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RecommendationServiceTest {
    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private LocationRepository locationRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetScheduledRecommendation() throws Exception {
        IntendVO vo = new IntendVO();
        vo.userId = 1;
        vo.startTime = "2019-04-09-11-30";
        vo.endTime = "2019-04-09-12-00";
        Mockito.when(scheduleRepository.addIntendSlot(vo.userId, vo.startTime, vo.endTime)).thenReturn(true);
        List<Integer> intendMatchList = new ArrayList<>();
        Mockito.when(scheduleRepository.findByMatchedSlot(vo.userId, vo.startTime, vo.endTime)).thenReturn(intendMatchList);
        List<Integer> busyMatchList = new ArrayList<>();
        Mockito.when(scheduleRepository.findByNonConlictSlot(vo.userId, vo.startTime, vo.endTime)).thenReturn(busyMatchList);

        List<UserBriefVO> list = recommendationService.getRecommendation(vo, vo.userId);
        assertEquals(list.size(), 0);
    }

    @Test
    public void testGetRealTimeRecommendation() throws Exception {
        String currentTime = "2019-04-12-12-21";
        String previousTime = "2019-04-12-12-01";
        RealTimeLocationVO vo = new RealTimeLocationVO();
        vo.uid = 1;
        vo.latitude = 30.7;
        vo.longitude = 40.8;
        Mockito.when(locationRepository.exist(vo.uid)).thenReturn(true);
//        Mockito.when(locationRepository.updateUserLocation(vo.latitude, vo.longitude, vo.uid, currentTime)).thenReturn(true);
        List<UserLocation> queryList = new ArrayList<>();
//        Mockito.when(locationRepository.getAllLocation(currentTime)).thenReturn(queryList);
        List<Integer> busyMatchList = new ArrayList<>();
//        Mockito.when(scheduleRepository.findByConflictSlot(vo.uid, currentTime, previousTime)).thenReturn(busyMatchList);

        List<UserBriefVO> list = recommendationService.getRealTimeRecommendation(vo);
        assertEquals(list.size(), 0);
    }
}
