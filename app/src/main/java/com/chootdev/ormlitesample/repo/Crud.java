package com.chootdev.ormlitesample.repo;

import java.util.List;

/**
 * Created by Choota.
 */

public interface Crud {
    int create(Object item);
    int update(Object item);
    int delete(Object item);
    Object findById(int id);
    List<?> findAll();
}
