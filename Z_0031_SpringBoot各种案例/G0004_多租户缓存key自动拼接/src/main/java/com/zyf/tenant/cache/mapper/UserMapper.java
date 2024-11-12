package com.zyf.tenant.cache.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zyf.tenant.cache.entity.Tenant;
import com.zyf.tenant.cache.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * UserMapper
 */
@Repository
public class UserMapper {

    public final List<UserEntity> users = CollUtil.newArrayList();

    @PostConstruct
    public void init() {
        final ArrayList<Tenant> tenants = new ArrayList<>();
          final Tenant tenant = new Tenant();
        tenant.setTenantId("1");
        tenant.setTenantName("test");
        tenants.add(tenant);
        users.add(new UserEntity(1, "用户" + 1, tenants));
        users.add(new UserEntity(2, "用户" + 2, tenants));
        users.add(new UserEntity(3, "用户" + 3, tenants));
        users.add(new UserEntity(4, "用户" + 4, tenants));
    }

    public List<UserEntity> list() {
        return this.users;
    }

    public UserEntity getOne(Integer id) {
        return this.users.stream()
                .filter(user -> NumberUtil.equals(user.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void update(UserEntity entity) {
        this.delete(entity.getId());
        users.add(entity);
    }

    public void delete(Integer id) {
        UserEntity entity = this.getOne(id);
        if (ObjectUtil.isNotNull(entity)) {
            users.remove(entity);
        }
    }

}
