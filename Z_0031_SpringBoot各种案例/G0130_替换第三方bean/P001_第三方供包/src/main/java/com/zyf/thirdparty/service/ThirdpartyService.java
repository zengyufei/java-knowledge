package com.zyf.thirdparty.service;


import com.zyf.thirdparty.dao.ThirdpartyDao;

public class ThirdpartyService {

    private ThirdpartyDao thirdpartyDao;

    public ThirdpartyService(ThirdpartyDao thirdpartyDao) {
        this.thirdpartyDao = thirdpartyDao;
    }

    public String getThirdparty(){
        return thirdpartyDao.getThirdparty();
    }
}
