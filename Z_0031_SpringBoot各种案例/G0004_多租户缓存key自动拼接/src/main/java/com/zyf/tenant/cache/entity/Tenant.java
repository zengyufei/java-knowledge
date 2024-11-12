package com.zyf.tenant.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String tenantId;
    private String tenantName;

}
