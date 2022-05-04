package fr.nazodev.p4_mareu.user_interface;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.database.AppDatabase;
import fr.nazodev.p4_mareu.di.DI;
import fr.nazodev.p4_mareu.model.Meeting;
import fr.nazodev.p4_mareu.repository.Repository;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.MyViewHolder> {

    private Repository repository = DI.getRepository();

    private final List<Meeting> items;
    public MeetingRecyclerViewAdapter(List<Meeting> items){ this.items = items ;  }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView emails;
        public TextView subject;
        public TextView location;
        public TextView date;
        public TextView time;
        public ImageButton buttonDelete;
        public MyViewHolder(View itemView) {
            super(itemView);
            emails = itemView.findViewById(R.id.textView_email);
            subject = itemView.findViewById(R.id.textView_subject);
            location = itemView.findViewById(R.id.textView_location);
            date = itemView.findViewById(R.id.textView_date);
            time = itemView.findViewById(R.id.textView_time);
            buttonDelete = itemView.findViewById(R.id.delete_meeting_button);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Meeting meeting = items.get(position);
        holder.emails.setText(TextUtils.join(", ",meeting.participants)); //TexteUtils.join à la place de String.join ->API26 minimum
        holder.subject.setText(meeting.subject);
        holder.location.setText(meeting.location);
        holder.date.setText(meeting.date);
        holder.time.setText(meeting.time);
        holder.buttonDelete.setOnClickListener(v -> {
            repository.deleteMeeting(meeting);
            repository.setFilteredList(repository.getMeetingList().getValue());
            //repository.deleteFilteredList(meeting);
            items.remove(position); //remove item in local data
            //notify work with local data
            notifyItemRemoved(position);

           // MeetingFragment.recyclerView.setAdapter(new MeetingRecyclerViewAdapter(repository.getFilteredList()));
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
