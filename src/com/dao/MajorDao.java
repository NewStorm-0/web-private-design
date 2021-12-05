package com.dao;

import com.entity.Major;

import java.util.List;

/**
 * @author Administrator
 */
public interface MajorDao {
    List<Integer> getStudentsId(int majorId);
    List<Major> getMajorsList();
}
