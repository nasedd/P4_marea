package fr.nazodev.p4_mareu.user_interface;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

import fr.nazodev.p4_mareu.R;

public class MeetingFragment extends Fragment {

    RecyclerView recyclerView;


    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        return fragment;
    }
    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        recyclerView = (RecyclerView) view;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void initList(){

        List<String> meetingList = Arrays.asList("Meeting A", "Meeting B", "Meeting C", "Meeting D");

        recyclerView.setAdapter(new RecyclerViewAdapter(meetingList));

    }
}