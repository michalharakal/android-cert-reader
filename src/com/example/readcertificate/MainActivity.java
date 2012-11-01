package com.example.readcertificate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PackageManager pm = this.getPackageManager();
		// TODO list all packages from phone
		String packageName = this.getPackageName();
		int flags = PackageManager.GET_SIGNATURES;
		PackageInfo packageInfo = null;

		try {
			packageInfo = pm.getPackageInfo(packageName, flags);
		} catch (NameNotFoundException e) {
			// TODO some error checking
			e.printStackTrace();
		}
		Signature[] signatures = packageInfo.signatures;

		for (Signature signature : signatures) {

			// cert = DER encoded X.509 certificate:
			byte[] cert = signature.toByteArray();
			InputStream input = new ByteArrayInputStream(cert);

			CertificateFactory cf = null;
			try {
				cf = CertificateFactory.getInstance("X509");
			} catch (CertificateException e) {
				// TODO some error checking
				e.printStackTrace();
			}
			X509Certificate c = null;
			try {
				c = (X509Certificate) cf.generateCertificate(input);
			} catch (CertificateException e) {
				// TODO some error checking
				e.printStackTrace();
			}
			Log.d("Example", "Certificate for: " + c.getSubjectDN());
			Log.d("Example", "Certificate issued by: " + c.getIssuerDN());
			Log.d("Example",
					"The certificate is valid from " + c.getNotBefore()
							+ " to " + c.getNotAfter());
			Log.d("Example", "Certificate SN# " + c.getSerialNumber());
			Log.d("Example", "Generated with " + c.getSigAlgName());
			try {
				Log.d("Example", "Fingerprint " + getThumbPrint((X509Certificate) c));
			} catch (CertificateEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	 public static String hexify (byte bytes[]) {

	    	char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', 
	    			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	    	StringBuffer buf = new StringBuffer(bytes.length * 2);

	        for (int i = 0; i < bytes.length; ++i) {
	        	buf.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
	            buf.append(hexDigits[bytes[i] & 0x0f]);
	        }

	        return buf.toString();
	    }
	
	
	 public static String getThumbPrint(X509Certificate cert) 
		    	throws NoSuchAlgorithmException, CertificateEncodingException {
		    	MessageDigest md = MessageDigest.getInstance("SHA-1");
		    	byte[] der = cert.getEncoded();
		    	md.update(der);
		    	byte[] digest = md.digest();
		    	return hexify(digest);

		    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
