//package com.zyf.tenant.cache.tenant;
//
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
//import com.zyf.tenant.cache.config.TenantConfigProperties;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.NullValue;
//import net.sf.jsqlparser.expression.StringValue;
//
//import java.util.List;
//
///**
// * 租户维护处理器
// */
//@Slf4j
//@RequiredArgsConstructor
//public class MyTenantHandler implements TenantLineHandler {
//
//    private final TenantConfigProperties properties;
//
//    /**
//     * 获取租户 ID 值表达式，只支持单个 ID 值
//     * <p>
//     *
//     * @return 租户 ID 值表达式
//     */
//    @Override
//    public Expression getTenantId() {
//        String tenantId = TenantContextHolder.getTenantId();
//        log.debug("当前租户为 >> {}", tenantId);
//
//        if (StrUtil.isBlank(tenantId)) {
//            return new NullValue();
//        }
//        return new StringValue(tenantId);
//    }
//
//    /**
//     * 获取租户字段名
//     *
//     * @return 租户字段名
//     */
//    @Override
//    public String getTenantIdColumn() {
//        return properties.getColumn();
//    }
//
//    /**
//     * 根据表名判断是否忽略拼接多租户条件
//     * <p>
//     * 默认都要进行解析并拼接多租户条件
//     *
//     * @param tableName 表名
//     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
//     */
//    @Override
//    public boolean ignoreTable(String tableName) {
//        // 判断是否跳过当前查询的租户过滤
//        if (TenantContextHolder.getTenantSkip()) {
//            return Boolean.TRUE;
//        }
//
//        String tenantId = TenantContextHolder.getTenantId();
//        // 租户中ID 为空，查询全部，不进行过滤
//        if (StrUtil.isBlank(tenantId)) {
//            return Boolean.TRUE;
//        }
//
////        //  判断实体类是否标记为多租户隔离
////        TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);
////        TenantTable annotation = null;
////        if (Objects.nonNull(tableInfo)) {
////            annotation = AnnotationUtils.findAnnotation(tableInfo.getEntityType(), TenantTable.class);
////        }
//        final List<String> tables = properties.getTables();
//        return tables.contains(tableName)/* && annotation == null*/;
//    }
//
//}
