package com.lc.Util;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author lc
 * 字节数组工具类
 */
public class ByteUtils {

	/**
	 * 将多个字节数组合并为一个字节数组
	 * @param bytes
	 * @return
	 */
	public static byte[] merge(byte[]... bytes) {
		Stream<Byte> stream = Stream.of();
		for(byte[] b : bytes) {
			stream = Stream.concat(stream, Arrays.stream(ArrayUtils.toObject(b))); //拼接两个 Stream
		}
		return ArrayUtils.toPrimitive(stream.toArray(Byte[]::new)); //将包装数据类型转为基础数据类型
	}
	
	/**
	 * long 转为 byte[]
	 * @param val
	 * @return
	 */
	public static byte[] toBytes(long val) {
		return ByteBuffer.allocate(Long.BYTES).putLong(val).array();
	}
}
