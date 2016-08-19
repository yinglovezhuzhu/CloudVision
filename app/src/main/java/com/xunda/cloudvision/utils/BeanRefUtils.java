package com.xunda.cloudvision.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 反射工具类
 * 
 * Create by yinglovezhuzhu@gmail.com 2016/08/19
 */
public class BeanRefUtils {
	
	/**
	 * 将Bean数据类对象转换Map数据表
	 * @param bean Bean数据类对象
	 * @return Map数据表
	 */
	public static Map<String, Object> getFieldValueMap(Object bean) {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		if (bean == null) {
			return valueMap;
		}
		Class<?> cls = bean.getClass();
		Map<String, Field> fieldMap = getFields(cls);
		Iterator<Map.Entry<String, Field>> iterator = fieldMap.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			try {
				Field field = (Field) ((Map.Entry<String, Field>) iterator.next()).getValue();
				field.setAccessible(true);
				Object value = field.get(bean);
				valueMap.put(field.getName(), value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return valueMap;
	}

	/**
	 * 读取某个类的成员变量列表（含父类）
	 * @param cls 类名
	 * @return
	 */
	public static Map<String, Field> getFields(Class<?> cls) {
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		Class<?> superClass = cls.getSuperclass();
		if (superClass != null) {
			fieldMap.putAll(getFields(superClass));
		}
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if ((!fieldName.startsWith("shadow$_"))
					&& (!fieldName.startsWith("$change"))) {
				fieldMap.put(fieldName, field);
			}
		}
		return fieldMap;
	}

	/**
	 * 设置类对象成员变量的值
	 * @param bean
	 * @param valMap
	 */
	public static void setFieldValues(Object bean, Map<String, Object> valMap) {
		Class<?> cls = bean.getClass();
		Map<String, Field> fieldMap = getFields(cls);
		Iterator<Map.Entry<String, Object>> iterator = valMap.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			try {
				Field field = (Field) fieldMap
						.get(((Map.Entry<String, Object>) iterator.next()).getKey());
				if (field != null) {
					field.setAccessible(true);
					field.set(bean, valMap.get(field.getName()));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
