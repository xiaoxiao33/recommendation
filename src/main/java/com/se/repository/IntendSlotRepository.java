package com.se.repository;

import java.util.List;

public interface IntendSlotRepository {

    public List<Integer> findByMatchedSlot(int uid, String start, String end);


}
