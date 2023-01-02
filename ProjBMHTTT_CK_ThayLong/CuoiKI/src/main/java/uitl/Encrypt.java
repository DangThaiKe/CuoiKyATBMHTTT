package uitl;

import java.security.MessageDigest;

import org.apache.tomcat.util.codec.binary.Base64;

public class Encrypt {
	public static String toSHA1(String txt) {
		String salt = "asdfas@f//ww1dfasdgsdagfghfdsahdgfstew";
		String result = null;
		txt = txt + salt;
		try {
			byte[] dataByte = txt.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			result = Base64.encodeBase64String(md.digest(dataByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}


}
