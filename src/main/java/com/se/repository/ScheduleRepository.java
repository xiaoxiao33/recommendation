package com.se.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository {

    /**
     * Intended slots table
     * @param uid
     * @param start
     * @param end
     * @return uid
     */
    public List<Integer> findByMatchedSlot (int uid, String start, String end);

    /**
     * busy slots table
     * @param uid
     * @param start
     * @param end
     * @return
     */
    public List<Integer> findByNonConlictSlot (int uid, String start, String end);


    /**
     * delete all rows previous to given time
     * @param pivot
     * @return
     */
    public boolean deleteExpiredBusySlots (String pivot);

    /**
     *
     * @param uid
     * @param start
     * @param end
     * @return
     */
    public boolean addSlot(int uid, String start, String end);

}
