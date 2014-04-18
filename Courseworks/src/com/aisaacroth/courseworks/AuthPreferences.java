package com.aisaacroth.courseworks;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

/*******************************************************************************
 * Writes Authentication information for Oauth to a shared preference file. This
 * preference file will contain the password and user information for the
 * application.
 * 
 * @author: Alexander Roth
 * @Date: 2014-04-04
 * @version: 0.5
 * @updated: 2014-04-17
 ******************************************************************************/
public class AuthPreferences {

	@SuppressWarnings("serial")
	public static class AuthPreferencesException extends RuntimeException {
		public AuthPreferencesException(Throwable e) {
			super(e);
		}
	}

//	private static final String KEY_USER = "uni";
//	private static final String KEY_PASSWORD = "password";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String KEY_TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
	private static final String CHARSET = "UTF-8";
	
	private final boolean encryptKeys;
	private final Cipher writer;
	private final Cipher reader;
	private final Cipher keyWriter;
	private SharedPreferences preferences;

	/***************************************************************************
	 * Creates a new file named "auth", which will house all the information
	 * related to the Oauth authentication process.
	 * 
	 * @param context
	 *            the current context
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 **************************************************************************/
	public AuthPreferences(Context context, String preferenceName,
			String secureKey, boolean encryptKeys)
			throws AuthPreferencesException {
		try {
			this.writer = Cipher.getInstance(TRANSFORMATION);
			this.reader = Cipher.getInstance(TRANSFORMATION);
			this.keyWriter = Cipher.getInstance(KEY_TRANSFORMATION);
			initCiphers(secureKey);
			this.preferences = context.getSharedPreferences(preferenceName,
					Context.MODE_PRIVATE);
			this.encryptKeys = encryptKeys;
		} catch (GeneralSecurityException e) {
			throw new AuthPreferencesException(e);
		} catch (UnsupportedEncodingException e) {
			throw new AuthPreferencesException(e);
		}
	}

	@SuppressLint("TrulyRandom")
	protected void initCiphers(String secureKey) throws InvalidKeyException,
			InvalidAlgorithmParameterException, UnsupportedEncodingException,
			NoSuchAlgorithmException {
		IvParameterSpec ivSpec = getIv();
		SecretKeySpec secretKey = getSecretKey(secureKey);

		writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
		reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
		keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
	}

	protected IvParameterSpec getIv() {
		byte[] iv = new byte[writer.getBlockSize()];
		System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0,
				iv, 0, writer.getBlockSize());
		return new IvParameterSpec(iv);
	}

	protected SecretKeySpec getSecretKey(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] keyBytles = createKeyBytes(key);
		return new SecretKeySpec(keyBytles, TRANSFORMATION);
	}

	protected byte[] createKeyBytes(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest
				.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
		md.reset();
		byte[] keyBytes = md.digest(key.getBytes(CHARSET));
		return keyBytes;
	}

	public void put(String key, String value) {
		if (value == null) {
			preferences.edit().remove(toKey(key)).commit();
		} else {
			putValue(toKey(key), value);
		}
	}

	public boolean containsKey(String key) {
		return preferences.contains(toKey(key));
	}

	public void removeValue(String key) {
		preferences.edit().remove(toKey(key)).commit();
	}

	public String getString(String key) {
		if (preferences.contains(toKey(key))) {
			String securedEncodedValue = preferences.getString(toKey(key), "");
			return decrypt(securedEncodedValue);
		}
		return null;
	}

	public void clear() {
		preferences.edit().clear().commit();
	}

	private String toKey(String key) {
		if (encryptKeys)
			return encrypt(key, keyWriter);
		else
			return key;
	}

	private void putValue(String key, String value) {
		String secureValueEncoded = encrypt(value, writer);
		preferences.edit().putString(key, secureValueEncoded).commit();
	}

	protected String encrypt(String value, Cipher writer) {
		byte[] secureValue;
		try {
			secureValue = convert(writer, value.getBytes(CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new AuthPreferencesException(e);
		}
		String secureValueEncoded = Base64.encodeToString(secureValue,
				Base64.NO_WRAP);
		return secureValueEncoded;
	}

	protected String decrypt(String securedEncodedValue) {
		byte[] securedValue = Base64
				.decode(securedEncodedValue, Base64.NO_WRAP);
		byte[] value = convert(reader, securedValue);
		try {
			return new String(value, CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new AuthPreferencesException(e);
		}
	}

	private static byte[] convert(Cipher cipher, byte[] bs) {
		try {
			return cipher.doFinal(bs);
		} catch (Exception e) {
			throw new AuthPreferencesException(e);
		}
	}
	// /***************************************************************************
	// * Sets the user of the current session for Oauth authentication.
	// *
	// * @param user
	// * the user's username.
	// **************************************************************************/
	// public void setUser(String user) {
	// Editor editor = preferences.edit();
	// editor.putString(KEY_USER, user);
	// editor.commit();
	// }
	//
	// /***************************************************************************
	// * Sets the password credentials for the Oauth authentication process.
	// *
	// * @param password
	// * the user's password
	// **************************************************************************/
	// public void setPassword(String password) {
	// Editor editor = preferences.edit();
	// editor.putString(KEY_PASSWORD, password);
	// editor.commit();
	// }
	//
	// /***************************************************************************
	// * Clears the information in the "auth" file in case the user does not
	// want
	// * to save his or her credential information.
	// **************************************************************************/
	// // public void clear() {
	// // Editor editor = preferences.edit();
	// // editor.clear();
	// // editor.commit();
	// // }
	//
	// /***************************************************************************
	// * Gets the user information
	// *
	// * @return the username
	// **************************************************************************/
	// public String getUser() {
	// return preferences.getString(KEY_USER, null);
	// }
	//
	// /***************************************************************************
	// * Gets the password information.
	// *
	// * @return the user's password
	// **************************************************************************/
	// public String getPassword() {
	// return preferences.getString(KEY_PASSWORD, null);
	// }
}