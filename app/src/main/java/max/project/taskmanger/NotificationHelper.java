package max.project.taskmanger;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {

    public static void displayNotification(Context context , String title , String body){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, ChatActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(Notification.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMsg = NotificationManagerCompat.from(context);
        mNotificationMsg.notify(1 , mBuilder.build());


    }


}
