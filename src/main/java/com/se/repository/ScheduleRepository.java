package com.se.repository;

import java.util.List;

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
     * @param uid userId
     * @param start
     * @param end
     * @return add a entry to busy slots table with the three fields.
     */
    public boolean addSlot(int uid, String start, String end);

    /**
     *
     * @param uid userId
     * @param start
     * @param end
     * @return add a entry to intend slots table with the three fields.
     */
    public boolean addIntendSlot(int uid, String start, String end);
}
