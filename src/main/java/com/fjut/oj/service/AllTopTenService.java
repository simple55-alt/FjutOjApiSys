package com.fjut.oj.service;

import com.fjut.oj.pojo.User;

import java.util.List;

public interface AllTopTenService {
    List<User> getRatingTOP();
    List<User> getAcbTOP();
    List<User> getAcTOP();
    List<User> getActiveTop();
}
