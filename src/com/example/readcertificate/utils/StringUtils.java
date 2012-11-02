package com.example.readcertificate.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public class StringUtils {
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


}
