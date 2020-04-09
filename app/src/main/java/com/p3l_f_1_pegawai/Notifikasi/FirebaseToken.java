//package com.p3l_f_1_pegawai.Notifikasi;
//
//import android.app.Application;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.os.Build;
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
//
//public class FirebaseToken extends FirebaseInstanceIdService {
////    public static final  String  CHANNEL_1_ID = "channel1";
////    public static final  String  CHANNEL_2_ID = "channel2";
////
////    @Override
////    public void onCreate() {
////        super.onCreate();
////
////        createNotificationChannels();
////    }
////
////    private void createNotificationChannels() {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            NotificationChannel channel1 = new NotificationChannel(
////                    CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH
////            );
////            channel1.setDescription("This is Channel 1");
////
////            NotificationChannel channel2 = new NotificationChannel(
////                    CHANNEL_2_ID, "Channel 2", NotificationManager.IMPORTANCE_LOW
////            );
////            channel2.setDescription("This is Channel 2");
////
////            NotificationManager manager = getSystemService(NotificationManager.class);
////            manager.createNotificationChannel(channel1);
////            manager.createNotificationChannel(channel2);
////        }
////    }
//
//
//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
//    }
//}
