package MaHoa;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;

public class XacThucChuKy {

	public static void main(String[] args)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		try

		{
//					import public key
			FileInputStream keyfis = new FileInputStream("D:\\pubKey.txt");
			byte[] encKey = new byte[keyfis.available()];
			keyfis.read(encKey);
			keyfis.close();
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
//					import signature bytes
			FileInputStream sigfis = new FileInputStream("D:\\HD.pdf");
			byte[] sigToVerify = new byte[sigfis.available()];
			sigfis.read(sigToVerify);
			sigfis.close();
			Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
			sig.initVerify(pubKey);
			FileInputStream datafis = new FileInputStream("D:\\HD2.pdf");
			BufferedInputStream bufin = new BufferedInputStream(datafis);
			byte[] buffer = new byte[1024];
			int len;
			while (bufin.available() != 0) {
				len = bufin.read(buffer);
				sig.update(buffer, 0, len);
			}
			;
			bufin.close();
			boolean verifies = sig.verify(sigToVerify);
			System.out.println("Xac thuc chu ky: " + verifies);

		} catch (Exception e) {
			System.out.println("Caught exception " + e.toString());
		}
		;
	};
};