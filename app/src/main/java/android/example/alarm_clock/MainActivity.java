package android.example.alarm_clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnPlusHour, btnMinusHour, btnPlusMinute, btnMinusMinute, btnAdd;
    TextView tvHour, tvMinute;
    int hour = 0, minute = 0;
    LinearLayout root;
    LinearLayout mainChild;
    TextView addTVHour, addTVMinute;
    Button addBtnDel, addBtnOn;
    ArrayList<View> viewList;
    Switch addSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlusHour = findViewById(R.id.buttonPlusHour);
        btnPlusHour.setOnClickListener(btnTime);

        btnMinusHour = findViewById(R.id.buttonMinusHour);
        btnMinusHour.setOnClickListener(btnTime);

        btnMinusMinute = findViewById(R.id.buttonMinusMinute);
        btnMinusMinute.setOnClickListener(btnTime);

        btnPlusMinute = findViewById(R.id.buttonPlusMinute);
        btnPlusMinute.setOnClickListener(btnTime);

        tvHour = findViewById(R.id.tvHour);

        tvMinute = findViewById(R.id.tvMinute);

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
            addBtnDel.setTag(root.getChildCount());

            addSwitch = childView.findViewById(R.id.newSwitch);
            addSwitch.setOnCheckedChangeListener(switchOn);


            root.addView(childView);
            //viewList.add(childView);

            addTVMinute = root.getChildAt(1).findViewById(R.id.newMinute);
            int count = root.getChildCount();//getChildAt()
            System.out.println("___" + addTVMinute.getText());
            //if (count > 3)
            //    root.removeViewAt(2);
        }
    };
    View.OnClickListener btnRemove = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            root.removeViewAt((Integer) view.getTag());
            System.out.println(view.getTag());
        }
    };
    OnCheckedChangeListener switchOn = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(addSwitch.isChecked())
                System.out.println("_123123");
        }
    };
}
