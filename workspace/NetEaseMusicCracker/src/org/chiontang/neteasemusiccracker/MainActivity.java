package org.chiontang.neteasemusiccracker;

import android.support.v7.app.ActionBarActivity;

import java.io.DataOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	String server_ip = "103.27.77.201";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button activateButton = (Button)findViewById(R.id.button_activate);
		Button restoreButton = (Button)findViewById(R.id.button_restore);

		activateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Process p;
				try {
				   // Preform su to get root privledges
				   p = Runtime.getRuntime().exec("su");

				   // Attempt to write a file to a root-only
				   DataOutputStream os = new DataOutputStream(p.getOutputStream());

				   os.writeBytes("mount -o remount,rw /system\n");
				   os.writeBytes("echo \"" + server_ip + " music.163.com\" >>/system/etc/hosts\n");
				   os.writeBytes("mount -o remount,ro /system\n");

				   // Close the terminal
				   os.writeBytes("exit\n");
				   os.flush();
				   try {
				      p.waitFor();
				           if (p.exitValue() != 255) {
				              // TODO Code to run on success
				              toastMessage("启用成功");
				           }
				           else {
				               // TODO Code to run on unsuccessful
				               toastMessage("未root！");
				           }
				   } catch (InterruptedException e) {
				      // TODO Code to run in interrupted exception
				       toastMessage("未root！");
				   }
				} catch (IOException e) {
				   // TODO Code to run in input/output exception
				    toastMessage("未root！");
				}

			}

		});

		restoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Process p;
				try {
				   // Preform su to get root privledges
				   p = Runtime.getRuntime().exec("su");

				   // Attempt to write a file to a root-only
				   DataOutputStream os = new DataOutputStream(p.getOutputStream());
				   os.writeBytes("mount -o remount,rw /system\n");
				   os.writeBytes("echo \"127.0.0.1 localhost\" >/system/etc/hosts\n");
				   os.writeBytes("mount -o remount,ro /system\n");
				   // Close the terminal
				   os.writeBytes("exit\n");
				   os.flush();
				   try {
				      p.waitFor();
				           if (p.exitValue() != 255) {
				              // TODO Code to run on success
				              toastMessage("禁用成功");
				           }
				           else {
				               // TODO Code to run on unsuccessful
				               toastMessage("未root！");
				           }
				   } catch (InterruptedException e) {
				      // TODO Code to run in interrupted exception
				       toastMessage("未root！");
				   }
				} catch (IOException e) {
				   // TODO Code to run in input/output exception
				    toastMessage("未root！");
				}

			}


		});

	}

	public void toastMessage(String messageText) {
		Toast.makeText(getApplicationContext(), messageText,
				   Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
