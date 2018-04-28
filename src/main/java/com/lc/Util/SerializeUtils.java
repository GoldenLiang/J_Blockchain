package com.lc.Util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class SerializeUtils {

	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Input input = new Input(bytes);
		Object obj = new Kryo().readClassAndObject(input);
		input.close();
		return obj;
	}
	
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		Output output = new Output(4096, -1);
		new Kryo().writeClassAndObject(output, object);
		byte[] bytes = output.toBytes();
		output.close();
		return bytes;
	}
}
