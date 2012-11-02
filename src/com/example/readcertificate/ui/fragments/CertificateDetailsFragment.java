package com.example.readcertificate.ui.fragments;

import com.example.readcertificate.utils.CertificateReader;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CertificateDetailsFragment extends Fragment {

	private static final String PACKAGE_NAME = "packageName";

	public static CertificateDetailsFragment newInstance(String packageName) {
		CertificateDetailsFragment f = new CertificateDetailsFragment();

		Bundle args = new Bundle();
		args.putString(PACKAGE_NAME, packageName);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt(PACKAGE_NAME, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		CertificateReader.LogCertificate(getActivity().getPackageManager(), getActivity().getPackageName());
		return null;
	}
}
