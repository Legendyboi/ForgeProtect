package com.legendyboi.forgeprotect.databases;

import java.sql.Connection;

public class NoDb implements IDatabase{
    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean connect() {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }

//    @Override
//    public boolean isPresent(UUID uuid) {
//        return false;
//    }
//
//    @Override
//    public boolean isPresent(String name) {
//        return false;
//    }
//
//
//    @Override
//    public boolean deleteData(UUID uuid) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteData(String name) {
//        return false;
//    }
}
