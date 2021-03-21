package com.example.testnavbottom.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testnavbottom.R;
import com.example.testnavbottom.internetClass;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        TextView textView=root.findViewById(R.id.tvrefreshtaskFromSvo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                internetClass a= new internetClass();
                a.checkSmartName("DTC1854801030076","Leminh77",inflater,container);


            }
        });


        return root;
    }
}