package fr.nazodev.p4_mareu.user_interface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.repository.Repository;

public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewAdapter.MyViewHolder>{

    private final List<String> emailList;

    public ParticipantRecyclerViewAdapter (List<String> emailList){
        this.emailList = emailList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView email;
        ImageButton delete_button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            delete_button = itemView.findViewById(R.id.delete_participant_button);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String item = emailList.get(position);
        holder.email.setText(emailList.get(position));
        holder.delete_button.setOnClickListener(v -> {
            Repository repository = DI.getRepository();
            repository.deleteEmail(item);
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }


}
