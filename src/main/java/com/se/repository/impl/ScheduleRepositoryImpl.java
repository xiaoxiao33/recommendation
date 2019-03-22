package com.se.repository.impl;

import com.se.repository.ScheduleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {
    /**
     * Intended slots table
     * @param uid
     * @param start
     * @param end
     * @return uid
     */
    public List<Integer> findByMatchedSlot (int uid, String start, String end) {
        return null;
    }

    /**
     * busy slots table
     * @param uid
     * @param start
     * @param end
     * @return
     */
    public List<Integer> findByNonConlictSlot (int uid, String start, String end) {
        return null;
    }


    /**
     * delete all rows previous to given time
     * @param pivot
     * @return
     */
    public boolean deleteExpiredBusySlots (String pivot) {
        return true;
    }

    /**
     *
     * @param uid userId
     * @param start
     * @param end
     * @return add a entry to busy slots table with the three fields.
     */
    public boolean addSlot(int uid, String start, String end) {
        return true;
    }
}
