package com.example.remotecommand.Animations;

import static com.example.remotecommand.MainActivity.brightness_controls;
import static com.example.remotecommand.MainActivity.is_brightness;
import static com.example.remotecommand.MainActivity.is_lock_controls;
import static com.example.remotecommand.MainActivity.is_media;
import static com.example.remotecommand.MainActivity.media_controls;
import static com.example.remotecommand.MainActivity.power_controls;

import android.animation.Animator;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

public class dock_animation {
    public static void media_dock_Open_animation(Activity activity) {
        int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        media_controls.setY(screenHeight); // Start the view off-screen at the bottom
        // Slide the view up
        media_controls.animate()
                .translationY(0)
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        is_media = true;

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                })
                .start();
    }
    public static void media_dock_Close_animation(Activity activity){
        int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        media_controls.animate()
                .translationY(screenHeight) // Translate it back to the bottom of the screen
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        media_controls.setVisibility(View.GONE);
                        is_media = false;
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                })
                .start();
    }
    public static void power_dock_Close_animation(Activity activity){
        int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        power_controls.animate()
                .translationY(screenHeight) // Translate it back to the bottom of the screen
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        power_controls.setVisibility(View.GONE);
                        is_lock_controls = false;

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                })
                .start();
    }
    public static void power_dock_Open_animation(Activity activity){
        int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        // Slide the view up
        power_controls.setY(screenHeight); // Start the view off-screen at the bottom

        // Slide the view up
        power_controls.animate()
                .translationY(0)
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        is_lock_controls = true;

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                })
                .start();
    }
    public static void brightness_dock_Open_animation(Activity activity){
        int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        // Slide the view up
        brightness_controls.setY(screenHeight); // Start the view off-screen at the bottom

        // Slide the view up
        brightness_controls.animate()
                .translationY(0)
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        is_brightness = true;

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                })
                .start();
    }
    public static void brightness_dock_Close_animation(Activity activity){
        int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        brightness_controls.animate()
                .translationY(screenHeight) // Translate it back to the bottom of the screen
                .setDuration(400)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        brightness_controls.setVisibility(View.GONE);
                        is_brightness = false;

                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) {

                    }
                })
                .start();
    }
}
