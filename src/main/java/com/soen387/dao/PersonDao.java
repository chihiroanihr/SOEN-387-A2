package com.soen387.dao;

import com.soen387.beans.Person;

public interface PersonDao {
    Person authenticateUser(long userId, String password);
    boolean checkUserRegistered(long userId);
    Person findUserInfo(long userId);
    boolean insert(Person person);
}
