package com.example.readcertificate;

import com.example.readcertificate.ui.fragments.CertificateDetailsFragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

		CertificateDetailsFragment newFragment = CertificateDetailsFragment.newInstance(getPackageName());

		ft.replace(R.id.fragment_host, newFragment, "detailFragment");

		// Start the animated transition.
		ft.commit();
	}

}
