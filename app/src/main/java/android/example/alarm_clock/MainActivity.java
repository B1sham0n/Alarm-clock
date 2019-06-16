package android.example.alarm_clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnPlusHour, btnMinusHour, btnPlusMinute, btnMinusMinute, btnAdd;
    TextView tvHour, tvMinute;
    int hour = 0, minute = 0;
    LinearLayout root;
    LinearLayout mainChild;
    TextView addTVHour, addTVMinute;
    Button addBtnDel, addBtnOn;
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
               tvHour.setText(Integer.toString(hour));
           }
           if(view == btnMinusHour){
               if(hour > 0)
                   hour--;
               else
                   hour = 23;
                tvHour.setText(Integer.toString(hour));
           }
           if(view == btnPlusMinute){
               if(minute == 59)
                   minute = 0;
               else
                   minute++;
                tvMinute.setText(Integer.toString(minute));
           }
           if(view == btnMinusMinute){
               if(minute > 0)
                   minute--;
               else
                   minute = 59;
                tvMinute.setText(Integer.toString(minute));
           }
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
            addTVHour.setText(Integer.toString(hour));
            addTVMinute =  childView.findViewById(R.id.newMinute);
            addTVMinute.setText(Integer.toString(minute));

            root.addView(childView);
            int count = root.getChildCount();//getChildAt()
            System.out.println(count);
        }
    };
}
