import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class TestMain {
	private static final String DEFAULT_SALT = "y5x6G+jW9w==";
	private static final String DEFAULT_IV = "U56ClHWVWbdw5PljZtGNpQ==";

	private static final int ITERATION_COUNT = 65536;// 50000;
	private static final int AES_KEY_LEN = 256;
	private static final String DEFAULT_KEY_PASS = "fY6edUre";
	private static Key skeySpec = null;

	private static IvParameterSpec ivpspec = null;
	private static final int DECIMAL_8 = 8;
	public static final String ENCODING_UTF8 = "UTF-8";
	public static final String ENCODER_MUTIL = "AES/CBC/PKCS5Padding";
	public static final String ENCODER_AES = "AES";

	public static void main(String[] args)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {

		String TOKEN = "fY6edUre";
		String content = "Hello";
		System.out.println(content);

//		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//		PBEKeySpec spec = new PBEKeySpec(TOKEN.toCharArray(), DEFAULT_SALT.getBytes(), 65536, 256);
//		SecretKey tmp = factory.generateSecret(spec);
//		SecretKey keySpec = new SecretKeySpec(tmp.getEncoded(), ENCODER_AES);
//		Cipher cipher = Cipher.getInstance(ENCODER_MUTIL);
//		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
//	    AlgorithmParameters ap = cipher.getParameters();
//	    byte[] ivBytes = ap.getParameterSpec(IvParameterSpec.class).getIV();
//	    byte[] result = cipher.doFinal(text.getBytes());
//	    Key key= generateKey(TOKEN.toCharArray(),  DEFAULT_SALT.getBytes());
		String resultStr = encryptByAes(content);
		System.out.println(resultStr);
		System.out.println(  decryptByAes(resultStr ));
	}
	
	 public synchronized static String decryptByAes(String content) {
	        Base64.Decoder decoder = Base64.getDecoder();

			byte[] defaultiv = decoder.decode(DEFAULT_IV);
	        return decryptByAes(content, defaultiv);
	    }

	public synchronized static String decryptByAes(String content, byte[] ivparam) {
        initParams(content, ivparam);
        return decryptByAes(content, skeySpec, ivpspec);
    }
	
	public synchronized static String decryptByAes(String content, Key key, IvParameterSpec ivp) {
		//
		Base64.Decoder decoder = Base64.getDecoder();

		String decryptResult = "";
		if (null == content) {
			return decryptResult;
		}
		try {
			// ?
			Cipher cipher = Cipher.getInstance(ENCODER_MUTIL);
			// ?
			cipher.init(Cipher.DECRYPT_MODE, key, ivp);
			byte[] result = cipher.doFinal(decoder.decode(content));
			decryptResult = new String(result, ENCODING_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptResult;
	}

	public synchronized static String encryptByAes(String content) {
		Base64.Decoder decoder = Base64.getDecoder();

		byte[] defaultiv = decoder.decode(DEFAULT_IV);// Base64.decodeBase64(DEFAULT_IV);
		return encryptByAes(content, defaultiv);
	}

	public synchronized static String encryptByAes(String content, byte[] ivparam) {
		initParams(content, ivparam);
		return encryptByAes(content, skeySpec, ivpspec);
	}

	private static void initParams(String content, byte[] ivparam) {
		checkParams(content, ivparam);

		if (null == skeySpec) {
			loadKey();
		}

		ivpspec = new IvParameterSpec(ivparam);
	}

	private static void checkParams(String content, byte[] ivparam) {
		if ((null == content) || (content.isEmpty())) {
			throw new RuntimeException("the content to encrpt is null or empty");
		}

		if ((null == ivparam) || (ivparam.length < DECIMAL_8)) {
			throw new RuntimeException("byte[] is null or less than eight bytes");
		}
	}

	private synchronized static void loadKey() {
		Base64.Decoder decoder = Base64.getDecoder();

		try {
			if (null != skeySpec) {
				return;
			}
			String keyPass = DEFAULT_KEY_PASS;
			byte[] salt = decoder.decode(DEFAULT_SALT);
			skeySpec = generateKey(keyPass.toCharArray(), salt);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static String encryptByAes(String content, Key key, IvParameterSpec ivp) {
		String encryptResult = "";
		Base64.Encoder encoder = Base64.getEncoder();
		if (null == content) {
			return encryptResult;
		}
		try {
			// ?
			Cipher cipher = Cipher.getInstance(ENCODER_MUTIL);
			byte[] byteContent = content.getBytes(ENCODING_UTF8);
			// ?
			cipher.init(Cipher.ENCRYPT_MODE, key, ivp);
			byte[] result = cipher.doFinal(byteContent);
			encryptResult = encoder.encodeToString(result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptResult;
	}

	public synchronized static Key generateKey(char[] Rabiit, byte[] salt) {
		SecretKeyFactory factory;
		SecretKey tmpkey = null;
		SecretKey secret = null;
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			// AES128??AES128
			// int aeskeylen = AppProperties.getAsInt("AES_KEY_LEN", AES_KEY_LEN);
			KeySpec keyspec = new PBEKeySpec(Rabiit, salt, ITERATION_COUNT, AES_KEY_LEN);
			tmpkey = factory.generateSecret(keyspec);

			// AES??
			secret = new SecretKeySpec(tmpkey.getEncoded(), ENCODER_AES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secret;
	}

}
