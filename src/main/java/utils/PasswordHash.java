package main.java.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
	public static final String ATTRIBUTE_NAME = "SHA_hasher";

	/**
	 * Hashes given text with SHA encryption algorithm.
	 * 
	 * @param text
	 *            Any String.
	 * @return Returns hashed text.
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashText(String text) throws NoSuchAlgorithmException {
		if(text == null) return "";
		MessageDigest md;
		md = MessageDigest.getInstance("SHA");
		md.update(text.getBytes());
		return hexToString(md.digest());
	}

	/*
	 * Given a byte[] array, produces a hex String, such as "234a6f". with 2
	 * chars for each byte in the array. (provided code)
	 */
	private static String hexToString(byte[] bytes) {
		StringBuilder buff = new StringBuilder();
		for (byte aByte : bytes) {
			int val = aByte;
			val = val & 0xff;
			if (val < 16)
				buff.append('0');
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

}
