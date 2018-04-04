package com.yuanshanbao.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

/**
 * 序列化实现工具类，提供两种类型的序列化和反序列化实现java自身及hessian
 * 
 * @author Singal
 *
 */
public class SerializeUtil {

	/**
	 * 基于Java实现序列化和反序列化
	 * 
	 * @param objContent
	 * @return
	 * @throws IOException
	 */
	public static byte[] javaSerialize(final Object objContent)
			throws IOException {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream output = null;
		try {
			baos = new ByteArrayOutputStream(1024);
			output = new ObjectOutputStream(baos);
			output.writeObject(objContent);
		} catch (final IOException ex) {
			LoggerUtil.error("IOException while JavaSerialize:", ex);
			throw ex;
		} finally {
			if (output != null) {
				try {
					output.close();
					if (baos != null) {
						baos.close();
					}
				} catch (final IOException ex) {
					LoggerUtil.error("IOException while JavaSerialize final:",
							ex);
				}
			}
		}
		return baos != null ? baos.toByteArray() : null;
	}

	public static Object javaDeserialize(final byte[] objContent)
			throws IOException {
		Object obj = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(objContent);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
		} catch (final IOException ex) {
			LoggerUtil.error("IOException while JavaDeserialize:", ex);
			throw ex;
		} catch (final ClassNotFoundException ex) {
			LoggerUtil.error("ClassNotFoundException while JavaDeserialize:",
					ex);
		} finally {
			if (ois != null) {
				try {
					ois.close();
					bais.close();
				} catch (final IOException ex) {
					LoggerUtil.error(
							"IOException while JavaDeserialize final:", ex);
				}
			}
		}
		return obj;
	}

	/**
	 * hessian2 serialize
	 * 
	 * @param source
	 * @return
	 * @throws IOException
	 */
	public static byte[] hessian2Serialize(Object source) throws IOException {
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			Hessian2Output out = new Hessian2Output(bos);
			// out.startMessage();
			out.writeObject(source);
			// out.completeMessage();
			out.flush();
			out.close();
			return bos.toByteArray();
		} catch (IOException e) {
			LoggerUtil.error("IOException while hessianWriteObject:", e);
			throw e;
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				LoggerUtil.error("IOException while hessianWriteObject final:",
						e);
			}
		}
	}

	/**
	 * hessian2 deserialize
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static Object hessian2Deserialize(byte[] bytes) throws IOException {
		if (bytes == null) {
			return null;
		}
		ByteArrayInputStream bin = null;
		try {
			bin = new ByteArrayInputStream(bytes);
			Hessian2Input in = new Hessian2Input(bin);
			// in.startMessage();
			Object obj = in.readObject();
			// in.completeMessage();
			in.close();
			return obj;
		} catch (IOException e) {
			LoggerUtil.error("IOException while hessianReadObject:", e);
			throw e;
		} finally {
			try {
				bin.close();
			} catch (IOException e) {
				LoggerUtil.error("IOException while hessianReadObject final:",
						e);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println(hessian2Deserialize(hessian2Serialize(1L)));
	}
}
