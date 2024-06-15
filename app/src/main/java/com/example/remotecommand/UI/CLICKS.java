package com.example.remotecommand.UI;

import static com.example.remotecommand.Animations.dock_animation.brightness_dock_Close_animation;
import static com.example.remotecommand.Animations.dock_animation.brightness_dock_Open_animation;
import static com.example.remotecommand.Animations.dock_animation.media_dock_Close_animation;
import static com.example.remotecommand.Animations.dock_animation.media_dock_Open_animation;
import static com.example.remotecommand.Animations.dock_animation.power_dock_Close_animation;
import static com.example.remotecommand.Animations.dock_animation.power_dock_Open_animation;
import static com.example.remotecommand.MainActivity.MOUSE_SENSITIVITY;
import static com.example.remotecommand.MainActivity.brightness_button;
import static com.example.remotecommand.MainActivity.brightness_controls;
import static com.example.remotecommand.MainActivity.decrease_volume;
import static com.example.remotecommand.MainActivity.fast_forward;
import static com.example.remotecommand.MainActivity.increase_volume;
import static com.example.remotecommand.MainActivity.isMoving;
import static com.example.remotecommand.MainActivity.is_brightness;
import static com.example.remotecommand.MainActivity.is_lock_controls;
import static com.example.remotecommand.MainActivity.is_media;
import static com.example.remotecommand.MainActivity.is_muted;
import static com.example.remotecommand.MainActivity.keyboard;
import static com.example.remotecommand.MainActivity.left_mouse_button;
import static com.example.remotecommand.MainActivity.lock;
import static com.example.remotecommand.MainActivity.lock_control;
import static com.example.remotecommand.MainActivity.media;
import static com.example.remotecommand.MainActivity.media_controls;
import static com.example.remotecommand.MainActivity.middle_mouse_button;
import static com.example.remotecommand.MainActivity.mute;
import static com.example.remotecommand.MainActivity.next;
import static com.example.remotecommand.MainActivity.play_pause;
import static com.example.remotecommand.MainActivity.power_controls;
import static com.example.remotecommand.MainActivity.prevX;
import static com.example.remotecommand.MainActivity.prevY;
import static com.example.remotecommand.MainActivity.previous;
import static com.example.remotecommand.MainActivity.restart;
import static com.example.remotecommand.MainActivity.rewind;
import static com.example.remotecommand.MainActivity.right_mouse_button;
import static com.example.remotecommand.MainActivity.shutdown;
import static com.example.remotecommand.MainActivity.startX;
import static com.example.remotecommand.MainActivity.startY;
import static com.example.remotecommand.MainActivity.trackpad;
import static com.example.remotecommand.MainActivity.volume;
import static com.example.remotecommand.Network.Connection.send_data;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.remotecommand.MainActivity;
import com.example.remotecommand.R;

public class CLICKS {
    public static int wait = 0;

    @SuppressLint("ClickableViewAccessibility")
    public static void onClick(Activity activity) {

        mute.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (is_muted) {
                    mute.setBackground(activity.getDrawable(R.drawable.round_button));
                    mute.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_orange)));
                    is_muted = false;
                } else {
                    mute.setBackground(activity.getDrawable(R.drawable.round_button_activated));
                    mute.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.black)));
                    is_muted = true;
                }

                send_data("MUTE");
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("PREVIOUS");
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("NEXT");
            }
        });
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("PLAY");
            }
        });
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("SHUTDOWN");
            }
        });
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("LOCK");
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("RESTART");
            }
        });
        decrease_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("DECREASE");
                if (volume.getProgress() != 0) {
                    volume.setProgress(volume.getProgress() - 2);
                }
            }
        });
        increase_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("INCREASE");
                volume.setProgress(volume.getProgress() + 2);
            }
        });
        fast_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("FAST_FORWARD");
            }
        });
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("REWIND");
            }
        });

        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_brightness) {
                    brightness_controls.setVisibility(View.GONE);
                    is_brightness = false;
                }
                if (is_lock_controls) {
                    power_controls.setVisibility(View.GONE);
                    is_lock_controls = false;
                }
                if (is_media) {


                    media_dock_Close_animation(activity);

//
                } else {
                    media_controls.setVisibility(View.VISIBLE);


                    media_dock_Open_animation(activity);


                }
            }
        });
        brightness_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_media) {
                    media_controls.setVisibility(View.GONE);
                    is_media = false;
                }
                if (is_lock_controls) {
                    power_controls.setVisibility(View.GONE);
                    is_lock_controls = false;
                }
                if (is_brightness) {

                    brightness_dock_Close_animation(activity);
                } else {
                    brightness_controls.setVisibility(View.VISIBLE);
                    brightness_dock_Open_animation(activity);
                }
            }
        });
        trackpad.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float currentX = event.getX();
                float currentY = event.getY();
                float deltaX = 0;
                float deltaY = 0;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Finger touches the touchpad
                        startX = currentX;
                        startY = currentY;
                        prevX = startX;
                        prevY = startY;
                        isMoving = false;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // Finger moves on the touchpad
                        deltaX = (currentX - prevX) * 4;
                        deltaY = (currentY - prevY) * 4;
                        if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
                            isMoving = true;
                            // Send mouse position change to your server or function
//                        sendMousePositionChange(deltaX, deltaY);
                            gestureComplete(deltaX, deltaY);
                            prevX = currentX;
                            prevY = currentY;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        // Finger is lifted from the touchpad, gesture is complete
                        if (power_controls.getVisibility() == View.VISIBLE) {
                            power_controls.setVisibility(View.GONE);
                            is_lock_controls = false;
                        } else if (brightness_controls.getVisibility() == View.VISIBLE) {
                            brightness_controls.setVisibility(View.GONE);
                            is_brightness = false;
                        } else if (media_controls.getVisibility() == View.VISIBLE) {
                            media_controls.setVisibility(View.GONE);
                            is_media = false;
                        }
                        if (!isMoving) {
                            // Send a click event if there was no significant movement
                            send_data("Mouse: Left Click!");
                        }
                        break;
                }
                return true;
            }
        });

        MainActivity.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (power_controls.getVisibility() == View.VISIBLE) {
                   power_dock_Close_animation(activity);


                } else if (brightness_controls.getVisibility() == View.VISIBLE) {
                    brightness_dock_Close_animation(activity);

                } else if (media_controls.getVisibility() == View.VISIBLE) {
                    media_dock_Close_animation(activity);
                }

            }

        });
        left_mouse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("Mouse: Left Click!");
            }
        });
        right_mouse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("Mouse: Right Click!");
            }
        });
        middle_mouse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_data("Mouse: Middle Click!");
            }
        });

        lock_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_media) {
                    media_controls.setVisibility(View.GONE);
                    is_media = false;
                }
                if (is_brightness) {
                    brightness_controls.setVisibility(View.GONE);
                    is_brightness = false;
                }
                if (is_lock_controls) {

                    power_dock_Close_animation(activity);

                } else {
                    power_controls.setVisibility(View.VISIBLE);
                    power_dock_Open_animation(activity);

                }
            }
        });


        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(keyboard.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    // Function to handle gesture completion
    public static void gestureComplete(float x, float y) {
        // Add code here to handle the completion of the gesture
        // This function will be called when the user lifts their finger from the touchpad
        // You can use this to perform any additional actions or send a gesture complete signal to your server.
        // For example:
        // sendGestureCompleteSignal();

        if (wait == 5) {
            String data = "Mouse: " + x + " " + y;
            send_data(data);
            wait = 0;
        } else {
            wait++;
        }
    }

}
