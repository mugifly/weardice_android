package info.ohgita.android.weardice;

import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.preview.support.v4.app.NotificationManagerCompat;

public class MainActivity extends Activity {
	final int NOTIFICATION_ID = 001;
	final int EVENT_ID_RETHROW = 002;
	protected NotificationManagerCompat notificationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Initialize the NotificationManager
		notificationManager = NotificationManagerCompat.from(this);
		
		// Fetch the Intent
		Intent intent = getIntent();
		if (intent != null && intent.getExtras() != null) {
			Bundle extra = intent.getExtras();
			if (extra.getInt("EVENT_ID") == EVENT_ID_RETHROW) {
				//throwDice();
			}
			notificationManager.cancel(NOTIFICATION_ID);
			throwDice();
			finish();
			return;
		}
		
		// Throw a dice
		throwDice();
		
		finish();
	}
	
	protected void throwDice() {
		// Generate the Intent and It's Pending Intent,
		// for occur when the user has been selected an action
		Intent viewIntent = new Intent(this, MainActivity.class);
		viewIntent.putExtra("EVENT_ID", EVENT_ID_RETHROW);
		PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);
		
		// Generate a random number
		Random rand = new Random();
		int dice_result = rand.nextInt(6) + 1;
		int dice_result_icon = -1;
		switch (dice_result) {
			case 1:
				dice_result_icon = R.drawable.dice_1;
				break;
			case 2:
				dice_result_icon = R.drawable.dice_2;
				break;
			case 3:
				dice_result_icon = R.drawable.dice_3;
				break;
			case 4:
				dice_result_icon = R.drawable.dice_4;
				break;
			case 5:
				dice_result_icon = R.drawable.dice_5;
				break;
			case 6:
				dice_result_icon = R.drawable.dice_6;
				break;		
		};
		
		// Generate a notification
		NotificationCompat.Builder notif_builder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.dice)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), dice_result_icon))
				.setContentTitle(getResources().getString(R.string.general_title_throwed_dice, dice_result))
				.setContentText(getResources().getString(R.string.general_message_throwed_dice))
				// Add an action
				.addAction(R.drawable.dice, getResources().getString(R.string.action_rethrow_dice), viewPendingIntent);
		Notification notif = notif_builder.build();
		
		// Fire a notification
		notificationManager.notify(NOTIFICATION_ID, notif);
	}

}
