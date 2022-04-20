package fr.nazodev.p4_mareu.user_interface;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.repository.Repository;

public class MeetingFragment extends Fragment {

    public static RecyclerView recyclerView;
    static Repository repository = DI.getRepository();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        recyclerView = (RecyclerView) view;
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //initList(); // pourquoi init list ici ? dans le P3 c'était fait ici
    }

    public static void initList(){
        recyclerView.setAdapter(new MeetingRecyclerViewAdapter(repository.getFilteredList()));
    }
}