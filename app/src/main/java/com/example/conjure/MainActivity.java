package com.example.conjure;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.codekidlabs.storagechooser.StorageChooser;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    public static final int FOLDERPICKER_PERMISSIONS = 0;
    public static final int FILEPICKER_PERMISSIONS = 1;
//    public TextView txt_Done;
//    private ProgressBar pgsBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setLogo(R.drawable.icon);
        super.onCreate(savedInstanceState);

//        txt_Done = findViewById(R.id.textDone);
//        txt_Done.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                txt_Done.setVisibility(View.INVISIBLE);
//                return true;
//            }
//        });
//        pgsBar = findViewById(R.id.progressBar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.icon);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        int REQUEST_READ_PHONE_STATE_PERMISSION = 1;



        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_PHONE_STATE_PERMISSION);
        }

        ImageButton filepickerBtn = findViewById(R.id.button_filepicker);
        filepickerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if(hasPermissions(MainActivity.this, PERMISSIONS)){
                    ShowFilepicker();
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
                }
                File folder = new File(Environment.getExternalStorageDirectory() + "/DL_ghost");
                boolean success=true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                    if (success) {
                        Toast.makeText(getApplicationContext(), "dir is created ",
                                Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "dir is NOT created ",
                                Toast.LENGTH_LONG).show();
                    }
                }else{

                }
            }
        });

        ImageButton folderpickerBtn = findViewById(R.id.button_folderpicker);
        folderpickerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if(hasPermissions(MainActivity.this, PERMISSIONS)){
                    ShowDirectoryPicker();
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, FOLDERPICKER_PERMISSIONS);
                }
                File folder = new File(Environment.getExternalStorageDirectory() + "/DL_ghost");
                boolean success=true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                    if (success) {
                        Toast.makeText(getApplicationContext(), "dir is created ",
                                Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "dir is NOT created ",
                                Toast.LENGTH_LONG).show();
                    }
                }else{

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        String displayName = null;
        String path = Environment.getExternalStorageDirectory() + "/DL_ghost/";
        Dialog settingsDialog = new Dialog(this);

        switch(requestCode){

            case 7:

                if(resultCode==RESULT_OK){
                    if(data.getClipData() != null) {

                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;

                        Uri uri = data.getClipData().getItemAt(currentItem).getUri();
                        String uri_str = uri.toString();
                        File myFile = new File(uri_str);
                        File file = new File(myFile.getAbsolutePath());
                        String dir_par = file.getParent();
                        while(currentItem < count) {


//                            settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                            settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
//                                    , null));
//                            settingsDialog.show();

                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            String src = imageUri.getPath();
                            String uriString = imageUri.toString();

                            currentItem = currentItem + 1;

                            if (uriString.startsWith("content://")) {
                                Cursor cursor = null;
                                try {
                                    cursor = this.getContentResolver().query(imageUri, null, null, null, null);
                                    if (cursor != null && cursor.moveToFirst()) {
                                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    }
                                } finally {
                                    cursor.close();
                                }
//                                Toast.makeText(MainActivity.this, displayName , Toast.LENGTH_LONG).show();

                                File new_File = new File(path + displayName);
                                if (!new_File.exists()) {
                                    try {
                                        new_File.createNewFile();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "The " + displayName + "is exist" , Toast.LENGTH_LONG).show();
                                }

                            } else if (uriString.startsWith("file://")) {
                                displayName = myFile.getName();
                                File new_File = new File(path + displayName);
                                if (!new_File.exists()) {
                                    try {
                                        new_File.createNewFile();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "The " + displayName + "is exist" , Toast.LENGTH_LONG).show();
                                }
//                                Toast.makeText(MainActivity.this, displayName , Toast.LENGTH_LONG).show();
                            }

                        }
//                        Dialog settingsDialog = new Dialog(this);
//                        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
//                                , null));
//                        settingsDialog.show();
//                        txt_Done.setVisibility(View.VISIBLE);





                    } else if(data.getData() != null) {

                        Uri uri = data.getData();
                        String imagePath = uri.toString();

                        if (imagePath.startsWith("content://")) {
                            Cursor cursor = null;
                            try {
                                cursor = this.getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            } finally {
                                cursor.close();
                            }
                            File new_File = new File(path + displayName);

                            if (!new_File.exists()) {
                                try {
                                    new_File.createNewFile();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "The " + displayName + " is exist", Toast.LENGTH_LONG).show();
                            }
                        }
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    }
                }
//            case 8:
//
//                if(resultCode==RESULT_OK){
//                    if(data.getData() != null) {
//
//                        Uri uri = data.getData();
//                        String folderPath = data.getDataString();
////                        if (imagePath.startsWith("content://")) {
////                            Cursor cursor = null;
////                            try {
////                                cursor = this.getContentResolver().query(uri, null, null, null, null);
////                                if (cursor != null && cursor.moveToFirst()) {
////                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
////                                }
////                            } finally {
////                                cursor.close();
////                            }
//                            File directory = new File(folderPath);
//                            File[] files = directory.listFiles();
//                            Log.d("Files", "Size: " + files.length);
//                            for (int i = 0; i < files.length; i++) {
//                                Log.d("Files", "FileName:" + files[i].getName());
//                            }
//                        }
//                    }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ShowDirectoryPicker(){
        // 1. Initialize dialog
        final StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(MainActivity.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(StorageChooser.DIRECTORY_CHOOSER)
                .build();

        // 2. Retrieve the selected path by the user and show in a toast !
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
//                txt_Done = findViewById(R.id.textDone);
//                Dialog settingsDialog = new Dialog(MainActivity.this);

                String path_gh = Environment.getExternalStorageDirectory() + "/DL_ghost/";
//                Toast.makeText(MainActivity.this, "The selected path is : " + path, Toast.LENGTH_SHORT).show();

                File directory = new File(path);
                File[] files = directory.listFiles();
                Log.d("Files", "Size: " + files.length);
                Dialog settingsDialog = new Dialog(MainActivity.this);
                for (int i = 0; i < files.length; i++) {
                    Toast.makeText(MainActivity.this,  i+1 + " File(s) are coppied", Toast.LENGTH_SHORT).show();

//                    settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout
//                            , null));
//                    settingsDialog.show();
                    Log.d("Files", "FileName:" + files[i].getName());
                    File new_File = new File(path_gh + files[i].getName());

                    if (!new_File.exists()) {
                        try {
                            new_File.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                Toast.makeText(
                        MainActivity.this,
                        "Done!",
                        Toast.LENGTH_SHORT
                ).show();



            }
        });

        // 3. Display File Picker !
        chooser.show();
    }

    /**
     * Helper method that verifies whether the permissions of a given array are granted or not.
     *
     * @param context
     * @param permissions
     * @return {Boolean}
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Callback that handles the status of the permissions request.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FOLDERPICKER_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            MainActivity.this,
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            MainActivity.this,
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
            case FILEPICKER_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            MainActivity.this,
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            MainActivity.this,
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }
    /**
     * Method that displays the filepicker of the StorageChooser.
     */
    public void ShowFilepicker(){
        // 1. Initialize dialog
        final String path_gh = Environment.getExternalStorageDirectory() + "/DL_goast/";

        final StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(MainActivity.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(StorageChooser.FILE_PICKER)
                .build();

        // 2. Retrieve the selected path by the user and show in a toast !
//        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
        chooser.setOnMultipleSelectListener(new StorageChooser.OnMultipleSelectListener() {

            @Override
            public void onDone(ArrayList<String> pathes) {


                int count = pathes.size();
                for (int i=0; i<count; i++){
                    File temp_File = new File (pathes.get(i));
//                    Toast.makeText(MainActivity.this, "The selected path is : " + pathes.get(i), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this,  i+1 + " File(s) are coppied", Toast.LENGTH_SHORT).show();

                    File new_File = new File(path_gh + temp_File.getName());
                    if (!new_File.exists()) {
                        try {
                            new_File.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toast.makeText(
                        MainActivity.this,
                        "Done!",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // 3. Display File Picker !
        chooser.show();
    }
}



//package com.example.conjure;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Environment;
//import android.provider.OpenableColumns;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import com.codekidlabs.storagechooser.StorageChooser;
//import java.io.File;
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//
//    Intent intent;
//    public static final int FOLDERPICKER_PERMISSIONS = 0;
//    public static final int FILEPICKER_PERMISSIONS = 1;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().setLogo(R.drawable.icon);
//        super.onCreate(savedInstanceState);
//
////        getSupportActionBar().setDisplayShowHomeEnabled(true);
////        getSupportActionBar().setHomeButtonEnabled(true);
////        getSupportActionBar().setDisplayUseLogoEnabled(true);
////        getSupportActionBar().setIcon(R.drawable.icon);
////        getSupportActionBar().setDisplayUseLogoEnabled(true);
//
//        int REQUEST_READ_PHONE_STATE_PERMISSION = 1;
//
//        setContentView(R.layout.activity_main);
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_PHONE_STATE_PERMISSION);
//        }
//
//        Button filepickerBtn = findViewById(R.id.button_filepicker);
//        filepickerBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            //On click function
//            public void onClick(View view) {
//                String[] PERMISSIONS = {
//                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                };
//
//                if(hasPermissions(MainActivity.this, PERMISSIONS)){
//                    ShowFilepicker();
//                }else{
//                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
//                }
//                File folder = new File(Environment.getExternalStorageDirectory() + "/DL_ghost");
//                boolean success=true;
//                if (!folder.exists()) {
//                    success = folder.mkdir();
//                    if (success) {
//                        Toast.makeText(getApplicationContext(), "dir is created ",
//                                Toast.LENGTH_LONG).show();
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "dir is NOT created ",
//                                Toast.LENGTH_LONG).show();
//                    }
//                }else{
//
//                }
//            }
//        });
//
//        Button folderpickerBtn = findViewById(R.id.button_folderpicker);
//        folderpickerBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            //On click function
//            public void onClick(View view) {
//                intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//
//                String[] PERMISSIONS = {
//                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//                };
//
//                if(hasPermissions(MainActivity.this, PERMISSIONS)){
//                    ShowDirectoryPicker();
//                }else{
//                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, FOLDERPICKER_PERMISSIONS);
//                }
//                File folder = new File(Environment.getExternalStorageDirectory() + "/DL_ghost");
//                boolean success=true;
//                if (!folder.exists()) {
//                    success = folder.mkdir();
//                    if (success) {
//                        Toast.makeText(getApplicationContext(), "dir is created ",
//                                Toast.LENGTH_LONG).show();
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "dir is NOT created ",
//                                Toast.LENGTH_LONG).show();
//                    }
//                }else{
//
//                }
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // TODO Auto-generated method stub
//        String displayName = null;
//        String path = Environment.getExternalStorageDirectory() + "/DL_goast/";
//
//        switch(requestCode){
//
//            case 7:
//
//                if(resultCode==RESULT_OK){
//                    if(data.getClipData() != null) {
//                        int count = data.getClipData().getItemCount();
//                        int currentItem = 0;
//
//                        Uri uri = data.getClipData().getItemAt(currentItem).getUri();
//                        String uri_str = uri.toString();
//                        File myFile = new File(uri_str);
//                        File file = new File(myFile.getAbsolutePath());
//                        String dir_par = file.getParent();
//                        while(currentItem < count) {
//                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
//                            String src = imageUri.getPath();
//                            String uriString = imageUri.toString();
//
//                            currentItem = currentItem + 1;
//
//                            if (uriString.startsWith("content://")) {
//                                Cursor cursor = null;
//                                try {
//                                    cursor = this.getContentResolver().query(imageUri, null, null, null, null);
//                                    if (cursor != null && cursor.moveToFirst()) {
//                                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                                    }
//                                } finally {
//                                    cursor.close();
//                                }
////                                Toast.makeText(MainActivity.this, displayName , Toast.LENGTH_LONG).show();
//
//                                File new_File = new File(path + displayName);
//                                if (!new_File.exists()) {
//                                    try {
//                                        new_File.createNewFile();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                                else{
//                                    Toast.makeText(MainActivity.this, "The " + displayName + "is exist" , Toast.LENGTH_LONG).show();
//                                }
//
//                            } else if (uriString.startsWith("file://")) {
//                                displayName = myFile.getName();
//                                File new_File = new File(path + displayName);
//                                if (!new_File.exists()) {
//                                    try {
//                                        new_File.createNewFile();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                                else{
//                                    Toast.makeText(MainActivity.this, "The " + displayName + "is exist" , Toast.LENGTH_LONG).show();
//                                }
////                                Toast.makeText(MainActivity.this, displayName , Toast.LENGTH_LONG).show();
//                            }
//                        }
//
//                    } else if(data.getData() != null) {
//
//                        Uri uri = data.getData();
//                        String imagePath = uri.toString();
//
//                        if (imagePath.startsWith("content://")) {
//                            Cursor cursor = null;
//                            try {
//                                cursor = this.getContentResolver().query(uri, null, null, null, null);
//                                if (cursor != null && cursor.moveToFirst()) {
//                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                                }
//                            } finally {
//                                cursor.close();
//                            }
//                            File new_File = new File(path + displayName);
//
//                            if (!new_File.exists()) {
//                                try {
//                                    new_File.createNewFile();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                Toast.makeText(MainActivity.this, "The " + displayName + " is exist", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                        //do something with the image (save it to some directory or whatever you need to do with it here)
//                    }
//                }
////            case 8:
////
////                if(resultCode==RESULT_OK){
////                    if(data.getData() != null) {
////
////                        Uri uri = data.getData();
////                        String folderPath = data.getDataString();
//////                        if (imagePath.startsWith("content://")) {
//////                            Cursor cursor = null;
//////                            try {
//////                                cursor = this.getContentResolver().query(uri, null, null, null, null);
//////                                if (cursor != null && cursor.moveToFirst()) {
//////                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//////                                }
//////                            } finally {
//////                                cursor.close();
//////                            }
////                            File directory = new File(folderPath);
////                            File[] files = directory.listFiles();
////                            Log.d("Files", "Size: " + files.length);
////                            for (int i = 0; i < files.length; i++) {
////                                Log.d("Files", "FileName:" + files[i].getName());
////                            }
////                        }
////                    }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public void ShowDirectoryPicker(){
//        // 1. Initialize dialog
//        final StorageChooser chooser = new StorageChooser.Builder()
//                .withActivity(MainActivity.this)
//                .withFragmentManager(getFragmentManager())
//                .withMemoryBar(true)
//                .allowCustomPath(true)
//                .setType(StorageChooser.DIRECTORY_CHOOSER)
//                .build();
//
//        // 2. Retrieve the selected path by the user and show in a toast !
//        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
//            @Override
//            public void onSelect(String path) {
//                String path_gh = Environment.getExternalStorageDirectory() + "/DL_goast/";
//                Toast.makeText(MainActivity.this, "The selected path is : " + path, Toast.LENGTH_SHORT).show();
//                File directory = new File(path);
//                File[] files = directory.listFiles();
//                Log.d("Files", "Size: " + files.length);
//                for (int i = 0; i < files.length; i++) {
//                    Log.d("Files", "FileName:" + files[i].getName());
//                    File new_File = new File(path_gh + files[i].getName());
//
//                    if (!new_File.exists()) {
//                        try {
//                            new_File.createNewFile();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//
//        // 3. Display File Picker !
//        chooser.show();
//    }
//
//    /**
//     * Helper method that verifies whether the permissions of a given array are granted or not.
//     *
//     * @param context
//     * @param permissions
//     * @return {Boolean}
//     */
//    public static boolean hasPermissions(Context context, String... permissions) {
//        if (context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Callback that handles the status of the permissions request.
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case FOLDERPICKER_PERMISSIONS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(
//                            MainActivity.this,
//                            "Permission granted! Please click on pick a file once again.",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                } else {
//                    Toast.makeText(
//                            MainActivity.this,
//                            "Permission denied to read your External storage :(",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                }
//
//                return;
//            }
//            case FILEPICKER_PERMISSIONS: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(
//                            MainActivity.this,
//                            "Permission granted! Please click on pick a file once again.",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                } else {
//                    Toast.makeText(
//                            MainActivity.this,
//                            "Permission denied to read your External storage :(",
//                            Toast.LENGTH_SHORT
//                    ).show();
//                }
//
//                return;
//            }
//        }
//    }
//    /**
//     * Method that displays the filepicker of the StorageChooser.
//     */
//    public void ShowFilepicker(){
//        // 1. Initialize dialog
//        final String path_gh = Environment.getExternalStorageDirectory() + "/DL_goast/";
//
//        final StorageChooser chooser = new StorageChooser.Builder()
//                .withActivity(MainActivity.this)
//                .withFragmentManager(getFragmentManager())
//                .withMemoryBar(true)
//                .allowCustomPath(true)
//                .setType(StorageChooser.FILE_PICKER)
//                .build();
//
//        // 2. Retrieve the selected path by the user and show in a toast !
////        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
//        chooser.setOnMultipleSelectListener(new StorageChooser.OnMultipleSelectListener() {
//
//            @Override
//            public void onDone(ArrayList<String> pathes) {
//
//
//                int count = pathes.size();
//                for (int i=0; i<count; i++){
//                    File temp_File = new File (pathes.get(i));
//                    Toast.makeText(MainActivity.this, "The selected path is : " + pathes.get(i), Toast.LENGTH_SHORT).show();
//                    File new_File = new File(path_gh + temp_File.getName());
//                    if (!new_File.exists()) {
//                        try {
//                            new_File.createNewFile();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//
//        // 3. Display File Picker !
//        chooser.show();
//    }
//}
//
