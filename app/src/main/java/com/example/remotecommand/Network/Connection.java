package com.example.remotecommand.Network;

import static com.example.remotecommand.MainActivity.brightness;
import static com.example.remotecommand.MainActivity.host_ip;
import static com.example.remotecommand.MainActivity.is_muted;
import static com.example.remotecommand.MainActivity.mute;
import static com.example.remotecommand.MainActivity.serverSocket;
import static com.example.remotecommand.MainActivity.volume;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


import com.example.remotecommand.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Connection {
    public static void pc_actions(Activity activity, String info) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (info.contains("'is_mute'")) {
                    JSONObject jObject = null; // json
                    try {
                        jObject = new JSONObject(info);
                        String Current_volume = jObject.getString("Current volume"); // get the name from data.
                        String Current_brightness = jObject.getString("Current brightness").replace("[","").replace("]",""); // get the name from data.
                        brightness.setValue(Float.parseFloat(Current_brightness));
                        volume.setProgress(Integer.parseInt(Current_volume));
                        int muted = jObject.getInt("is_mute");
                        if (muted == 0) {
                            mute.setBackground(activity.getDrawable(R.drawable.round_button));
                            mute.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.yellow_orange)));
                            is_muted = false;
                        } else {
                            mute.setBackground(activity.getDrawable(R.drawable.round_button_activated));
                            mute.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.black)));
                            is_muted = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    public static void ShowToast(Activity activity, String info, int color) {
        Toast toast = Toast.makeText(activity, info, Toast.LENGTH_LONG);
        View view = toast.getView();

        //To change the Background of Toast
        view.setBackgroundColor(color);
        TextView text = (TextView) view.findViewById(android.R.id.message);

        //Shadow of the Of the Text Color
        text.setShadowLayer(2, 0, 0, Color.GRAY);
        text.setTextColor(Color.WHITE);
        text.setWidth(300);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.show();
    }
    public static void send_data(String data) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    Socket client = new Socket(host_ip, 5000);
                    PrintWriter printwriter = new PrintWriter(client.getOutputStream(), true);
                    printwriter.write(data); // write the message to output stream
                    printwriter.flush();
                    printwriter.close();
                    Log.d("Message: ",data);
                    client.close();
                } catch (UnknownHostException e2) {

                    Log.d("unknow_host", "UnknownHostException");
                } catch (IOException e1) {
                    Log.d("socket", e1.toString());
                }

            }
        }).start();
    }
    public static void receive_data(Activity activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (serverSocket==null||serverSocket.isClosed()){
                        serverSocket = new ServerSocket(5012);
                        serverSocket.setSoTimeout(5000);
                    }


                    Socket soc = serverSocket.accept();
                    System.out.println("Receive new connection: " + soc.getInetAddress());
                    DataOutputStream dout = new DataOutputStream(soc.getOutputStream());
                    DataInputStream in = new DataInputStream(soc.getInputStream());
                    dout.writeUTF("Thank You For Connecting.");
                    String data = (String) in.readUTF();
                    pc_actions(activity, data);
                    Log.d("Client ", data);
                    dout.flush();
                    dout.close();
                    soc.close();
                    serverSocket.close();

                }catch (SocketTimeoutException s){
                    Log.d("SocketTimeoutException","Socket Timeout Exception");
                }
                catch (Exception e ) {
                    e.printStackTrace();


                }

            }
        }).start();
    }

}
