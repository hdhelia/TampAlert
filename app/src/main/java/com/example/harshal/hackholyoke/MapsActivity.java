package com.example.harshal.hackholyoke;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    List<users> globalusersList = new ArrayList<users>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView getDeliveryButton = findViewById(R.id.get_delivery_button);
        final Context context = this;
        getDeliveryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("MapsActivity", "onClick:clicked ");
                Log.i("Send SMS", "");
                SmsManager smgr = SmsManager.getDefault();
                for(int i = 0; i< globalusersList.size();i++){
                    smgr.sendTextMessage(globalusersList.get(i).getPhone(),null,"TampAlert! Need a sanitary product urgently!",null,null);
                }
                Toast.makeText(MapsActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();
//                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//                public static String getDefaultSmsAppPackageName(@NonNull Context context){
//                    String defaultSmsPackageName;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context);
//                        return defaultSmsPackageName;
//                    }
//                    else {
//                        Intent intent = new Intent(Intent.ACTION_VIEW)
//                                .addCategory(Intent.CATEGORY_DEFAULT).setType("vnd.android-dir/mms-sms");
//                        final List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, 0);
//                        if (resolveInfos != null && !resolveInfos.isEmpty())
//                            return resolveInfos.get(0).activityInfo.packageName;
//
//                    }
//                    return null;
//                }
                //if (getDefaultSmsAppPackageName(this) != null) {
//                    smsIntent.setData(Uri.parse("smsto:"));
//                    smsIntent.setType("vnd.android-dir/mms-sms");
//                    smsIntent.putExtra("address", new String("7348468124"));
//                    smsIntent.putExtra("sms_body", "Test ");
//                    startActivity(smsIntent);
                //}



//                try {
//                    startActivity(smsIntent);
//                    Log.i("Finished starting ...", "");
//                    finish();
//                    Log.i("Finished sending SMS...", "");
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(MapsActivity.this,
//                            "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
//                }
                //sendSMS();
                //sendSMSMessage();

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Mount Holyoke and move the camera
        LatLng RIT = new LatLng(42.253794, -72.576605);
        mMap.addMarker(new MarkerOptions().position(RIT).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RIT, 18.2f));

        new FireBaseDatabaseHelper().readUsers(new FireBaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<users> usersList, List<String> keys) {
                globalusersList = usersList;
                for (int i=0;i<usersList.size();i++){
                    LatLng tempPos = new LatLng(usersList.get(i).getLat(),usersList.get(i).getLong());
                    if(usersList.get(i).getIsfree()) {
                        mMap.addMarker(new MarkerOptions().position(tempPos).icon(BitmapDescriptorFactory.fromResource(R.drawable.free_marker)).title("Free"));
                    }else{
                        mMap.addMarker(new MarkerOptions().position(tempPos).icon(BitmapDescriptorFactory.fromResource(R.drawable.premium_marker)).title("Paid"));
                    }
                }

            }


            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }


//    protected void sendSMSMessage() {
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.SEND_SMS)) {
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.SEND_SMS},
//                        MY_PERMISSIONS_REQUEST_SEND_SMS);
//            }
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.i("MapsActivity","smssent");
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("+17348468124", null, "message", null, null);
//                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//    }
/*
protected void sendSMS() {
    Log.i("Send SMS", "");
    Intent smsIntent = new Intent(Intent.ACTION_VIEW);

    smsIntent.setData(Uri.parse("smsto:"));
    smsIntent.setType("vnd.android-dir/mms-sms");
    smsIntent.putExtra("address"  , new String ("7348468124"));
    smsIntent.putExtra("sms_body"  , "Test ");
    //startActivity(smsIntent);
    try {
        startActivity(smsIntent);
        Log.i("Finished starting ...", "");
        finish();
        Log.i("Finished sending SMS...", "");
    } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(MapsActivity.this,
                "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
    }
}
*/

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

}
