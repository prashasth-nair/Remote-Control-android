package com.example.remotecommand;

import static com.example.remotecommand.UI.CLICKS.onClick;
import static com.example.remotecommand.Network.Connection.receive_data;
import static com.example.remotecommand.Network.Connection.send_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.remotecommand.Network.Discover;
import com.google.android.material.slider.Slider;
import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.marcinmoskala.arcseekbar.ProgressListener;

import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Objects;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity {
    public static ArcSeekBar volume;

    public static TextView volume_txt;
    public static Slider brightness;

    public static ImageView mute;
    public static String host_ip ;
    public static ImageView previous;
    public static ImageView next;
    public static ImageView play_pause;
    public static ImageView rewind;
    public static ImageView fast_forward;
    public static ImageView increase_volume;
    public static ImageView decrease_volume;
    public static ImageButton left_mouse_button;
    public static ImageButton middle_mouse_button;
    public static ImageButton right_mouse_button;

    public static ImageView shutdown;
    public static ImageView lock;
    public static ImageView restart;

    public static LinearLayout media;
    public static LinearLayout brightness_button;
    public static LinearLayout keyboard;
    public static LinearLayout lock_control;

    public static RelativeLayout media_controls;
    public static RelativeLayout brightness_controls;
    public static RelativeLayout power_controls;
    public static RelativeLayout main;
    public static RelativeLayout trackpad;

    private Toolbar mActionBarToolbar;

    public static boolean is_muted;
    public static boolean is_media = false;
    public static boolean is_brightness = false;
    public static boolean is_lock_controls = false;


    Handler handler = new Handler();
    Runnable executing;
    Runnable myRunnable;
    int delay = 2000; // 2 sec (it is in millisecond)
    public static ServerSocket serverSocket;


    MenuItem laptop_icon;

    public static NsdManager nsdManager;

    private final Handler connect_handler = new Handler(Looper.getMainLooper());
    private static final long DELAY_MS = 2000; // 2 seconds

    public static float startX, startY;
    public static float prevX, prevY;

    public static boolean isMoving = false;
    public static final int MOUSE_SENSITIVITY = 1; // Adjust this for mouse movement speed



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        nsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);
        // Start the networking thread
        new Thread(new Discover()).start();


        onClick(this);

        volume_txt.setVisibility(View.GONE);
        mute.setVisibility(View.VISIBLE);
        // using toolbar as ActionBar
        setSupportActionBar(mActionBarToolbar);

        brightness.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                send_data("brightness " + brightness.getValue());
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                send_data("brightness " + brightness.getValue());
            }
        });

        volume.setOnStartTrackingTouch(new ProgressListener() {
            @Override
            public void invoke(int i) {
                mute.setVisibility(View.GONE);
                volume_txt.setVisibility(View.VISIBLE);
                volume.setOnProgressChangedListener(new ProgressListener() {
                    @Override
                    public void invoke(int i) {
                        send_data("volume " + i);
                        volume_txt.setText(String.valueOf(i));
                    }
                });

            }
        });
        volume.setOnStopTrackingTouch(new ProgressListener() {
            @Override
            public void invoke(int i) {
                send_data("volume " + i);
                volume_txt.setVisibility(View.GONE);
                mute.setVisibility(View.VISIBLE);
            }
        });
    }
//
    public void findViews() {
        volume = (ArcSeekBar) findViewById(R.id.seekbar);
        volume_txt = (TextView) findViewById(R.id.volume_txt);
        mute = (ImageView) findViewById(R.id.mute);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        play_pause = (ImageView) findViewById(R.id.play_pause);
        rewind = (ImageView) findViewById(R.id.rewind);
        fast_forward = (ImageView) findViewById(R.id.fast_forward);
        increase_volume = (ImageView) findViewById(R.id.increase);
        decrease_volume = (ImageView) findViewById(R.id.decrease);

        left_mouse_button = (ImageButton) findViewById(R.id.left_mouse_button);
        middle_mouse_button = (ImageButton) findViewById(R.id.middle_mouse_button);
        right_mouse_button = (ImageButton) findViewById(R.id.right_mouse_button);

        shutdown = (ImageView) findViewById(R.id.shutdown);
        lock = (ImageView) findViewById(R.id.lock);
        restart = (ImageView) findViewById(R.id.restart);

        media = (LinearLayout) findViewById(R.id.media);
        brightness_button = (LinearLayout) findViewById(R.id.brightness);
        media_controls = (RelativeLayout) findViewById(R.id.media_controls);
        brightness_controls = (RelativeLayout) findViewById(R.id.brightness_controlls);
        power_controls = (RelativeLayout) findViewById(R.id.power_controls);
        main = (RelativeLayout) findViewById(R.id.main);
        trackpad = (RelativeLayout) findViewById(R.id.trackpad);
        keyboard = (LinearLayout) findViewById(R.id.keyboard_control);
        lock_control = (LinearLayout) findViewById(R.id.lock_control);
        brightness = (Slider) findViewById(R.id.brightness_slider);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);

    }

    public void connect() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    if (host_ip == null) {
                        return;
                    }
                    Socket client = new Socket(host_ip, 6000);
                    client.setSoTimeout(4*1000);
                    PrintWriter printwriter = new PrintWriter(client.getOutputStream(), true);
                    printwriter.write("HI "); // write the message to output stream
                    printwriter.flush();
                    printwriter.close();

                    // Toast in background becauase Toast cannnot be in main thread you have to create runOnuithread.
                    // this is run on ui thread where dialogs and all other GUI will run.
                    if (client.isConnected()) {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                //Do your UI operations like dialog opening or Toast here
                                laptop_icon.setIcon(+R.raw.laptop_online);
                            }
                        });
                    } else {
                        laptop_icon.setIcon(+R.raw.laptop);
                    }
                    client.close();

                } catch (UnknownHostException e2) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            //Do your UI operations like dialog opening or Toast here
                            laptop_icon.setIcon(+R.raw.laptop);
//                            Toast.makeText(getApplicationContext(), "Unknown host please make sure IP address is correct", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e1) {
                    Log.d("socket", "IOException");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            //Do your UI operations like dialog opening or Toast here
                            laptop_icon.setIcon(+R.raw.laptop);
                        }
                    });
                }


            }
        }).start();
    }
    @Override
    //creating menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        laptop_icon = menu.findItem(R.id.laptop);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        connect_handler.postDelayed(myRunnable = new Runnable() {
            @Override
            public void run() {

                connect_handler.postDelayed(myRunnable, DELAY_MS);
                connect();
                receive_data(MainActivity.this);
            }
        }, DELAY_MS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(executing);
        connect_handler.removeCallbacks(myRunnable);
        hideSoftKeyboard(this);
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            inputMethodManager.hideSoftInputFromWindow(
                    Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(),
                    0
            );

        }
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            // just puts your task (all activities) in background. Same as if user pressed Home button.
            return true;
        }
        char pressedKey = (char) event.getUnicodeChar();
        if (!event.isSystem()) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                send_data("{\"key\": \"" + "backspace" + "\"}");
            } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            } else if (keyCode == 59) {
                Log.d("Caps", "caps");
            } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
                send_data("{\"key\": \"" + "EnterKey" + "\"}");
            } else if (keyCode == 75) {
                send_data("{\"key\": \"" + "\\\"" + "\"}");
            } else {
                send_data("{\"key\": \"" + pressedKey + "\"}");
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            send_data("DECREASE");
            if (volume.getProgress() != 0) {
                volume.setProgress(volume.getProgress() - 2);
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            send_data("INCREASE");
            volume.setProgress(volume.getProgress() + 2);
        }

        /* Sample for handling the Menu button globally */
        return true;
    }

}