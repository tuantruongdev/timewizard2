package com.example.testnavbottom.ui.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testnavbottom.Classview;
import com.example.testnavbottom.DatabaseHelper;
import com.example.testnavbottom.R;
import com.example.testnavbottom.classListAdaper;
import com.example.testnavbottom.classlistAlarm_adapter;
import java.util.ArrayList;

public class DashboardFragment extends Fragment  {

    private DashboardViewModel dashboardViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);







        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final ListView listView = root.findViewById(R.id.listviewAlarm);



        DatabaseHelper mydb = new DatabaseHelper(this.getContext());
        Context context=this.getContext();
        ArrayList<Classview> classlist = new ArrayList<>();

      ArrayList<Classview> a  = new ArrayList<Classview>();

       // a.add(new Classview("0",1,"0","2020-10-10 08:55:00","0",1,"0","0","0",1));



      a= mydb.getAllProducts();
     // a=mydb.ArraylistCompare(a);
        classlistAlarm_adapter adapter = new classlistAlarm_adapter(this.getContext(),R.layout.alarm_layout,a);

        listView.setAdapter(adapter);
        ArrayList<Classview> finalA = a;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int
                    position, long id) {

                // it will get the position of selected item from the ListView

                final int selected_item = position;

                new AlertDialog.Builder(getContext()).
                        setIcon(android.R.drawable.ic_delete)
                        .setTitle("Xóa sự kiện này?")
                        .setMessage("Bạn có thực sự muốn xóa vĩnh viễn sự kiện này..?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                mydb.deleteProductByID(finalA.get(selected_item).getId());

                                finalA.remove(selected_item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Không" , null).show();

                return true;
            }
        });

        return root;
    }
}