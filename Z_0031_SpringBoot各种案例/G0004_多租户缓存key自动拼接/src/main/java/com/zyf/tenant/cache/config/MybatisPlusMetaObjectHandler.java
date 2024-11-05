//package com.zyf.tenant.cache.config;
//
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import com.zyf.tenant.cache.tenant.TenantContextHolder;
//import io.micrometer.common.util.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.util.ClassUtils;
//
//import java.nio.charset.Charset;
//
///**
// * MybatisPlus 自动填充配置
// */
//@Slf4j
//public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
//
//	@Override
//	public void insertFill(MetaObject metaObject) {
//		final String tenantId = TenantContextHolder.getTenantId();
//		if (StrUtil.isNotBlank(tenantId)) {
//			fillValIfNullByName("tenantId", tenantId, metaObject, false);
//		}
//	}
//
//	@Override
//	public void updateFill(MetaObject metaObject) {
//	}
//
//	/**
//	 * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
//	 * @param fieldName 属性名
//	 * @param fieldVal 属性值
//	 * @param metaObject MetaObject
//	 * @param isCover 是否覆盖原有值,避免更新操作手动入参
//	 */
//	private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
//		// 1. 没有 set 方法
//		if (!metaObject.hasSetter(fieldName)) {
//			return;
//		}
//		// 2. 如果用户有手动设置的值
//		Object userSetValue = metaObject.getValue(fieldName);
//		String setValueStr = Convert.str(userSetValue, Charset.defaultCharset());
//		if (StringUtils.isNotBlank(setValueStr) && !isCover) {
//			return;
//		}
//		// 3. field 类型相同时设置
//		Class<?> getterType = metaObject.getGetterType(fieldName);
//		if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
//			metaObject.setValue(fieldName, fieldVal);
//		}
//	}
//
//}
