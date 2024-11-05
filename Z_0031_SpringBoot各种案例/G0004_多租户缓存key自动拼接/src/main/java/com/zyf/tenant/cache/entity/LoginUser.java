package com.zyf.tenant.cache.entity;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户信息
 *
 */
@Data
public class LoginUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 员工编码
     */
    private String sysStaffNo;

    /**
     * 员工姓名
     */
    private String sysStaffName;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 租户列表
     */
    private List<JSONObject> tenants;

}
