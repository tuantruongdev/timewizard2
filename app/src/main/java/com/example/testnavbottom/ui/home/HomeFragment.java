package com.example.testnavbottom.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testnavbottom.Classview;
import com.example.testnavbottom.R;
import com.example.testnavbottom.classListAdaper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final ListView listView = root.findViewById(R.id.lv1);
        Switch swEnable =root.findViewById(R.id.swEnableEvent);

        Calendar calendar = Calendar.getInstance();

        ArrayList<Classview> classlist = new ArrayList<>();
        for (int i=0;i<30;i++) {

            int day = calendar.get(Calendar.DAY_OF_WEEK);
            Date date = calendar.getTime();
            int cday = calendar.get(Calendar.DATE);

            String currentdayweek = "T2";

            switch (day) {
                case Calendar.SUNDAY:
                    currentdayweek = "CN";
                    break;
                case Calendar.MONDAY:
                    currentdayweek = "T2";
                    break;
                case Calendar.TUESDAY:
                    currentdayweek = "T3";
                    break;
                case Calendar.WEDNESDAY:
                    currentdayweek = "T4";
                    break;
                case Calendar.THURSDAY:
                    currentdayweek = "T5";
                    break;
                case Calendar.FRIDAY:
                    currentdayweek = "T6";
                    break;
                case Calendar.SATURDAY:
                    currentdayweek = "T7";
                    break;
                default:
                    currentdayweek = "T2";
                    break;
            }


            Classview class1 = new Classview("--:--", "Bạn chưa có lịch hôm nay, vui lòng thêm sự kiện nhé!~", currentdayweek, String.valueOf(cday));


       /*
        Classview class2 = new Classview("10:50 - 11:30","mon 1\nTiet: 5,6\nC5.104");
        Classview class3 = new Classview("10:50 - 11:30","mon 2\nTiet: 5,6\nC5.104");
        Classview class4= new Classview("10:50 - 11:30","mon 3\nTiet: 5,6\nC5.104");
        Classview class5 = new Classview("10:50 - 11:30","mon 4\nTiet: 5,6\nC5.104");
        Classview class6 = new Classview("10:50 - 11:30","mon 5\nTiet: 5,6\nC5.104");
*/


            classlist.add(class1);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
       /* classlist.add(class2);
        classlist.add(class3);
        classlist.add(class4);
        classlist.add(class5);
        classlist.add(class1);*/

        classListAdaper adaper = new classListAdaper(this.getContext(),R.layout.adaper_view_layout,classlist);
        listView.setAdapter(adaper);






        return root;
    }
}