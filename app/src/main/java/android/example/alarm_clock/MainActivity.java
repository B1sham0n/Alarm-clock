package android.example.alarm_clock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "1";
    Button btnPlusHour, btnMinusHour, btnPlusMinute, btnMinusMinute, btnAdd;
    TextView tvHour, tvMinute;
    int hour = 0, minute = 0;
    LinearLayout root;
    LinearLayout mainChild;
    TextView addTVHour, addTVMinute;
    Button addBtnDel, addBtnOn;
    ArrayList<View> viewList;
    Switch addSwitch;
    AlarmManager am;
    ArrayList<PendingIntent> arrPI;
    Intent intent1;
    PendingIntent pIntent1;
    ArrayList<Calendar> arrCl;
    NumberPicker numberPickerHour, numberPickerMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("i am created!!!");
        arrPI = new ArrayList<>();
        arrCl = new ArrayList<>();
        am = (AlarmManager) getSystemService(ALARM_SERVICE);

        //btnPlusHour = findViewById(R.id.buttonPlusHour);
        //btnPlusHour.setOnClickListener(btnTime);

       // btnMinusHour = findViewById(R.id.buttonMinusHour);
       // btnMinusHour.setOnClickListener(btnTime);

        //btnMinusMinute = findViewById(R.id.buttonMinusMinute);
       // btnMinusMinute.setOnClickListener(btnTime);

        //btnPlusMinute = findViewById(R.id.buttonPlusMinute);
       // btnPlusMinute.setOnClickListener(btnTime);

        //tvHour = findViewById(R.id.tvHour);

        //tvMinute = findViewById(R.id.tvMinute);

        btnAdd = findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(btnAddTime);

        root = findViewById(R.id.rootMyClock);
        mainChild = new LinearLayout(this);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mainChild.setLayoutParams(par);
        mainChild.setOrientation(LinearLayout.HORIZONTAL);
        addTVHour = new TextView(this);
        addTVHour.setText(Integer.toString(hour));
        addTVHour.setBackgroundColor(1111);
        addBtnOn = new Button(this);
        addBtnOn.setText("ON");
        addTVHour.setText(Integer.toString(hour));
        mainChild.addView(addTVHour);
        mainChild.addView(addBtnOn);
        //ненужный код
        numberPickerHour = findViewById(R.id.numberPickerHour);
        numberPickerMinute = findViewById(R.id.numberPickerMinute);
        numberPickerHour.setMaxValue(23);
        numberPickerHour.setMinValue(0);
        numberPickerMinute.setMaxValue(59);
        numberPickerMinute.setMinValue(0);



    }

    View.OnClickListener btnTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           if(view == btnPlusHour) {
               if(hour == 23)
                   hour = 0;
               else
                    hour++;

           }
           if(view == btnMinusHour){
               if(hour > 0)
                   hour--;
               else
                   hour = 23;
           }
           if(view == btnPlusMinute){
               if(minute == 59)
                   minute = 0;
               else
                   minute++;
           }
           if(view == btnMinusMinute){
               if(minute > 0)
                   minute--;
               else
                   minute = 59;
           }
            if(hour < 10) {
                tvHour.setText("0");
                tvHour.append(Integer.toString(hour));
            }
            else
                tvHour.setText(Integer.toString(hour));
            if(minute < 10) {
                tvMinute.setText("0");
                tvMinute.append(Integer.toString(minute));
            }
            else
                tvMinute.setText(Integer.toString(minute));
        }
    };
    View.OnClickListener btnAddTime = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("___iamhere");
            hour = numberPickerHour.getValue();
            minute = numberPickerMinute.getValue();


            View childView;
            Context mContext = getApplicationContext();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childView = inflater.inflate(R.layout.child, null);
            addTVHour =  childView.findViewById(R.id.newHour);
            if(hour < 10)
            {
                addTVHour.setText("0");
                addTVHour.append(Integer.toString(hour));
            }
            else
                addTVHour.setText(Integer.toString(hour));

            addTVMinute =  childView.findViewById(R.id.newMinute);
            if(minute < 10)
            {
                addTVMinute.setText("0");
                addTVMinute.append(Integer.toString(minute));
            }
            else
                addTVMinute.setText(Integer.toString(minute));

            addBtnDel = childView.findViewById(R.id.newBtnDel);
            //addBtnOn = childView.findViewById(R.id.newBtnOn);

            addBtnDel.setOnClickListener(btnRemove);
            addBtnDel.setTag("delete");

            addSwitch = childView.findViewById(R.id.newSwitch);
            addSwitch.setOnCheckedChangeListener(switchOn);
            addSwitch.setTag("switch");
            //addSwitch.setId(root.getChildCount());

            //arrPI.add(root.getChildCount(),pIntent1);


            root.addView(childView);
            //System.out.println("iamid" + addSwitch.getId());
            updateIdSwitch();
            updateIdButton(addBtnDel);

            intent1 = createIntent(Integer.toString(addSwitch.getId()), "Clock");
            pIntent1 = PendingIntent.getBroadcast(getApplicationContext(), addSwitch.getId(), intent1, 0);
            if(root.getChildCount()-1 > arrPI.size()-1)
                arrPI.add(root.getChildCount()-1, pIntent1);
            Calendar tempCal = Calendar.getInstance();
            tempCal.set(Calendar.HOUR, hour);
            tempCal.set(Calendar.MINUTE,minute);
            arrCl.add(tempCal);
            //viewList.add(childView);

            //addTVMinute = root.getChildAt(1).findViewById(R.id.newMinute);
            //int count = root.getChildCount();//getChildAt()
            //System.out.println("___" + addTVMinute.getText());
            //if (count > 3)
            //    root.removeViewAt(2);

        }
    };
    View.OnClickListener btnRemove = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            root.removeViewAt((Integer) view.getId());
            System.out.println(view.getId());
            updateIdButton((Button) view);
            updateIdSwitch();
            am.cancel(arrPI.get((Integer)view.getId()));
            arrCl = updateCalendar(arrCl,view.getId());

            System.out.println("new calendar:");
            for(int i = 0; i < arrCl.size(); i++)
                System.out.println(arrCl.get(i).get(Calendar.HOUR) + " : " + arrCl.get(i).get(Calendar.MINUTE));
        }

    };
    OnCheckedChangeListener switchOn = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(compoundButton.isChecked()){
                createAlarm(compoundButton);
                System.out.println("__=" + compoundButton.getTag());
            }
            else ;

        }
    };
    public ArrayList<Calendar> updateCalendar(ArrayList<Calendar> oldCalendar, int id){
        ArrayList<Calendar> newCalendar = new ArrayList<>();

        for(int i = 0; i < id; i++)
            newCalendar.add(i,oldCalendar.get(i));
        for(int i = id; i < oldCalendar.size()-1;i++)
            newCalendar.add(i,oldCalendar.get(i+1));
        for(int i = 0; i < newCalendar.size(); i++)
            System.out.println(newCalendar.get(i).get(Calendar.HOUR) + " : " + newCalendar.get(i).get(Calendar.MINUTE));
        return newCalendar;
    }
    public void createAlarm(CompoundButton cb){
        Calendar cl = Calendar.getInstance();

        cl.set(Calendar.HOUR, hour);
        cl.set(Calendar.MINUTE, minute);
        System.out.println(arrCl.get(cb.getId()).get(Calendar.HOUR) + "     " + arrCl.get(cb.getId()).get(Calendar.MINUTE));

        am.set(AlarmManager.RTC, arrCl.get(cb.getId()).getTimeInMillis(), arrPI.get(cb.getId()));
    }
    Intent createIntent(String action, String extra) {
        Intent intent = new Intent(this, newAlarm.class);
        intent.setAction(action);
        intent.putExtra("extra", extra);
        return intent;
    }
    public void updateIdSwitch(){
        Switch sw;
        View v = null;
        for(int i = 0; i < root.getChildCount(); i++){
            v = root.getChildAt(i);
            sw = v.findViewWithTag("switch");
            if(sw != null){
                sw.setId(i);
            System.out.println("iamid" + sw.getId());}
        }
    }
    public void updateIdButton(Button btn){
        View v = null;
        for(int i = 0; i < root.getChildCount(); i++){
            v = root.getChildAt(i);
            btn = v.findViewWithTag("delete");
            if(btn != null){
                btn.setId(i);
                System.out.println("iamid" + btn.getId());}
        }
    }
    public void createNotification() {
        Intent notificationIntent = new Intent(this, newAlarm.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);//стандартная мелодия будильника
        //---для новых версий андроида нужен канал
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            //настройки канала
            channel.setDescription("To turn off click on the notification!");
            channel.enableLights(true);
            channel.setLightColor(Color.YELLOW);
            channel.enableVibration(true);
            channel.setSound(alarmTone, null);
            //показать уведомление
            notificationManager.createNotificationChannel(channel);
        }
        //---для старых версий андроида
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm Clock")
                .setContentText("To turn off click the notification!") // Текст уведомления
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE), AudioManager.STREAM_RING)//2 параметр для бесконечного рингтона
                .setContentIntent(contentIntent)
                .setAutoCancel(true)//при нажатии закрыть уведомление + должен отключиться звук
                .setColor(Color.YELLOW);
                //.setChannelId("1");//ранее был создан канал с CHANNEL_ID=1

        //показываю уведомление
        notificationManager.notify(1, builder.build());

    }


}

