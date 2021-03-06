package cn.dream.anno.handler.excelfield;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@SuppressWarnings("unchecked")
public class DefaultFormatValueAnnoHandler {

	private static final Set<Class<?>> ALLOW_TYPE_SET = new LinkedHashSet<>();
	
	static {
		
		ALLOW_TYPE_SET.add(String.class);
		ALLOW_TYPE_SET.add(Byte.class);
		ALLOW_TYPE_SET.add(Short.class);
		ALLOW_TYPE_SET.add(Integer.class);
		ALLOW_TYPE_SET.add(Long.class);
		ALLOW_TYPE_SET.add(Float.class);
		ALLOW_TYPE_SET.add(Double.class);
		ALLOW_TYPE_SET.add(Boolean.class);
		ALLOW_TYPE_SET.add(Date.class);
		
	}
	
	public Set<Class<?>> getAllowTypeSet(){
		return ALLOW_TYPE_SET;
	}

	/**
	 * 检查值是否符合基本类型，并且不为null
	 * @param o
	 * @return
	 */
	public final boolean check(Object o) {
		if(ObjectUtils.isEmpty(o)){
			return false;
		}

		Set<Class<?>> a = getAllowTypeSet();
		if(a != null) {
			Class<?> oClass = o.getClass();
			return a.contains(oClass);
		}
		//noinspection ConstantConditions
		log.warn("仅支持 {} 类型",getAllowTypeSet().stream().map(Class::getSimpleName).collect(Collectors.joining(",")));
		return false;
	}
	
	/**
	 * 格式化值，值类型必须为基本类型的值
	 * @param value 值
	 * @param valueType 值的预期类型
	 * @return
	 */
	public final Object formatValue(Object value,Class<?> valueType) {
		if(check(value)) {
			String formatToString = formatToString(value);
			if(String.class.isAssignableFrom(valueType)) {
				return formatToString;
			} else if(Byte.class.isAssignableFrom(valueType)) {
				return formatStringToByte(formatToString);
			} else if(Short.class.isAssignableFrom(valueType)) {
				return formatStringToShort(formatToString);
			} else if(Integer.class.isAssignableFrom(valueType)) {
				return formatStringToInteger(formatToString);
			} else if(Long.class.isAssignableFrom(valueType)) {
				return formatStringToLong(formatToString);
			} else if(Boolean.class.isAssignableFrom(valueType)) {
				return formatStringToBoolean(formatToString);
			} else if(Float.class.isAssignableFrom(valueType)) {
				return formatStringToFloat(formatToString);
			} else if(Double.class.isAssignableFrom(valueType)) {
				return formatStringToDouble(formatToString);
			} else if(Date.class.isAssignableFrom(valueType)) {
				return formatDateToDateString(value);
			} else {
				return formatTo(value);
			}
		}
		return null;
	}

	public String formatToString(Object value) {
		return String.valueOf(value);
	}
	public Integer formatStringToInteger(String value) {
		return Integer.valueOf(formatToString(value));
	}
	public Long formatStringToLong(String value) {
		return Long.valueOf(formatToString(value));
	}
	public Float formatStringToFloat(String value) {
		return Float.valueOf(formatToString(value));
	}
	public Double formatStringToDouble(String value) {
		return Double.valueOf(formatToString(value));
	}
	public Byte formatStringToByte(String value) {
		return Byte.valueOf(formatToString(value));
	}
	public Boolean formatStringToBoolean(String value) {
		return Boolean.valueOf(formatToString(value));
	}
	public Short formatStringToShort(String value) {
		return Short.valueOf(formatToString(value));
	}
	public String formatDateToDateString(Object value) {
		if(value instanceof Date){
			Date d = (Date) value;
			return getDateFormat().format(d);
		}
		throw new IllegalArgumentException("非法参数异常");
	}
	
	public SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public <T> T formatTo(Object value) {
		return null;
	}
	
	public static void main(String[] args) {
		DefaultFormatValueAnnoHandler defaultFormatValueAnnoHandler = new DefaultFormatValueAnnoHandler();
		
		String formatFormString = defaultFormatValueAnnoHandler.formatToString("1");
		
		String formatFromDate = defaultFormatValueAnnoHandler.formatDateToDateString(new Date());
		System.out.println(formatFromDate);
		
		
	}
	
}
