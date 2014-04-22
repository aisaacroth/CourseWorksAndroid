package com.aisaacroth.courseworks;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

/*******************************************************************************
 * Writes Authentication information for Oauth to a shared preference file. This
 * preference file will contain the password and user information for the
 * application. Encrypts the information using AES (a symmetric cypher) and
 * saves it to the file that way. TODO: Document everything.
 * 
 * @author: Alexander Roth
 * @Date: 2014-04-04
 * @version: 0.7
 * @updated: 2014-04-18
 ******************************************************************************/
public class AuthPreferences {

	/* Custom exception class - Acts as a catch-all for testing purposes */
	@SuppressWarnings("serial")
	public static class AuthPreferencesException extends RuntimeException {
		public AuthPreferencesException(Throwable e) {
			super(e);
		}
	}

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
	 * Instantiates an AuthPreferences object. This object controls the ciphers
	 * that will read and write to the file. It will also hold onto the type of
	 * encryption.
	 * 
	 * @param context
	 *            The given context
	 * @param preferenceName
	 *            The file where the user information is being stored.
	 * @param secureKey
	 *            The keys that will be used by the ciphers.
	 * @param encryptKeys
	 *            Declares whether encryption is on.
	 * @throws AuthPreferencesException
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

	/***************************************************************************
	 * Initializes the ciphers for encryption and decryption.
	 * 
	 * @param secureKey
	 *            The key used for encoding and decoding.
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 **************************************************************************/
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

	/***************************************************************************
	 * Creates an initialization vector for the cipher to work off of.
	 * 
	 * @return a specification to an initialization vector
	 **************************************************************************/
	protected IvParameterSpec getIv() {
		byte[] iv = new byte[writer.getBlockSize()];
		System.arraycopy("fldsjfodasjifudslfjdsaofshaufihadsf".getBytes(), 0,
				iv, 0, writer.getBlockSize());
		return new IvParameterSpec(iv);
	}

	/***************************************************************************
	 * Constructs a secret key from a given byte array.
	 * 
	 * @param key
	 *            The encryption key that will be used to get the byte array.
	 * @return Secret key from the given byte array.
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 **************************************************************************/
	protected SecretKeySpec getSecretKey(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] keyBytes = createKeyBytes(key);
		return new SecretKeySpec(keyBytes, TRANSFORMATION);
	}

	/***************************************************************************
	 * Hashes the given encryption key by using SHA-256 as the hashing
	 * algorithm.
	 * 
	 * @param key
	 *            The encryption key that will be hashed
	 * @return the array of bytes of the hashed encryption key.
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 **************************************************************************/
	protected byte[] createKeyBytes(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest
				.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
		md.reset();
		byte[] keyBytes = md.digest(key.getBytes(CHARSET));
		return keyBytes;
	}

	/***************************************************************************
	 * Adds key-value pair to the SharedPreference file. If the value already
	 * exists, it is overwritten.
	 * 
	 * @param key
	 *            The key in the key-value pair.
	 * @param value
	 *            The value in the key-value pair.
	 **************************************************************************/
	public void put(String key, String value) {
		if (value == null) {
			preferences.edit().remove(toKey(key)).commit();
		} else {
			putValue(toKey(key), value);
		}
	}

	/***************************************************************************
	 * Checks if the given key is within the SharedPreference file.
	 * 
	 * @param key
	 *            The key value being checked for
	 * @return true if present, false if not
	 **************************************************************************/
	public boolean containsKey(String key) {
		return preferences.contains(toKey(key));
	}

	/***************************************************************************
	 * Removes the specified value from the SharedPreference file.
	 * 
	 * @param key
	 *            The key value that will be removed.
	 **************************************************************************/
	public void removeValue(String key) {
		preferences.edit().remove(toKey(key)).commit();
	}

	/***************************************************************************
	 * Checks if the SharedPreference file contains the given key value; if
	 * present, retrieve the encoded value and decode it.
	 * 
	 * @param key
	 *            The key value being searched for
	 * @return The decrypted string value
	 **************************************************************************/
	public String getString(String key) {
		if (preferences.contains(toKey(key))) {
			String securedEncodedValue = preferences.getString(toKey(key), "");
			return decrypt(securedEncodedValue);
		}
		return null;
	}

	/***************************************************************************
	 * Clears the SharedPreference file.
	 **************************************************************************/
	public void clear() {
		preferences.edit().clear().commit();
	}

	/***************************************************************************
	 * Encrypts a given key value.
	 * 
	 * @param key
	 *            The key value of the SharedPreference file.
	 * @return either the encrypted key or the key if encryption is turned off
	 **************************************************************************/
	private String toKey(String key) {
		if (encryptKeys)
			return encrypt(key, keyWriter);
		else
			return key;
	}

	/***************************************************************************
	 * Encodes a value and stores it in the SharedPreference file.
	 * 
	 * @param key
	 *            The encrypted key
	 * @param value
	 *            The string that will be encrypted and stored
	 **************************************************************************/
	private void putValue(String key, String value) {
		String secureValueEncoded = encrypt(value, writer);
		preferences.edit().putString(key, secureValueEncoded).commit();
	}

	/***************************************************************************
	 * Encrypts a plain text string into an encoded string.
	 * 
	 * @param value
	 *            The string that will be encoded.
	 * @param writer
	 *            The cipher that will being the encoding.
	 * @return the encoded string value.
	 **************************************************************************/
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

	/***************************************************************************
	 * Decrypts an encoded string into a plain text string.
	 * 
	 * @param securedEncodedValue
	 *            The encoded string
	 * @return The decoded string
	 **************************************************************************/
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

	/***************************************************************************
	 * Encrypts or decrypts data in a single-part operation, or finished a
	 * multiple-part operation. The data is encrypted or decrypted, depending on
	 * how this cipher was initialized.
	 * 
	 * @param cipher
	 *            The initialized cipher
	 * @param bs
	 *            The byte array input
	 * @return the encrypted or decrypted data
	 **************************************************************************/
	private static byte[] convert(Cipher cipher, byte[] bs) {
		try {
			return cipher.doFinal(bs);
		} catch (Exception e) {
			throw new AuthPreferencesException(e);
		}
	}
}