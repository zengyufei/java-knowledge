package com.zyf.thirdparty.dao;


import org.springframework.stereotype.Repository;

@Repository
public class CustomDao extends ThirdpartyDao {

    @Override
    public String getThirdparty() {
        return "Hello Custom Repository";
    }
}
