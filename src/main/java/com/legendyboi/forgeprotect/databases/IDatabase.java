package com.legendyboi.forgeprotect.databases;

import java.sql.Connection;

public interface IDatabase {

    String name();

    boolean connect();

    void init();

    Connection getConnection();

//    boolean isPresent(UUID uuid);
//
//    boolean isPresent(String name);
//
//    boolean deleteData(UUID uuid);
//
//    boolean deleteData(String name);

}
