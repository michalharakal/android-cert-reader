package com.example.readcertificate.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class CertificateReader {

	public static void LogCertificate(PackageManager pm, String packageName) {

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
				Log.d("Example",
						"Fingerprint "
								+ StringUtils
										.getThumbPrint((X509Certificate) c));
			} catch (CertificateEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
