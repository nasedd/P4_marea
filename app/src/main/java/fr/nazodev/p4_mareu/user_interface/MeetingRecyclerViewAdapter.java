package fr.nazodev.p4_mareu.user_interface;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.nazodev.p4_mareu.R;
import fr.nazodev.p4_mareu.model.Meeting;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.MyViewHolder> {

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
        holder.emails.setText(TextUtils.join(", ",items.get(position).participants)); //TexteUtils.join à la place de String.join ->API26 minimum
        holder.subject.setText(items.get(position).subject);
        holder.location.setText(items.get(position).location);
        holder.date.setText(items.get(position).date);
        holder.time.setText(items.get(position).time);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
