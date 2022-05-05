package fr.nazodev.p4_mareu.user_interface;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.database.AppDatabase;
import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.repository.Repository;

public class MeetingFragment extends Fragment {

    public static RecyclerView recyclerView;
    Repository repository = DI.getRepository();
    MeetingViewModel meetingViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        meetingViewModel = new ViewModelProvider(getActivity()).get(MeetingViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        recyclerView = (RecyclerView) view;
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        meetingViewModel.filteredList.observe(getViewLifecycleOwner(),list -> recyclerView.setAdapter(new MeetingRecyclerViewAdapter( list )));

        return view;
    }
}