package theo.androidproject.medictime.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import theo.androidproject.medictime.Models.MedicItem;
import theo.androidproject.medictime.R;

public class MedicAdapter extends RecyclerView.Adapter<MedicAdapter.ViewHolder> {

    private final List<MedicItem> mMedicList;

    public MedicAdapter(List<MedicItem> medicList) {
        mMedicList = medicList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_medic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicItem medicItem = mMedicList.get(position);
        holder.startDateTextView.setText("Date de début: " + medicItem.getStartDate());
        holder.endDateTextView.setText("Date de fin: " + medicItem.getEndDate());
        holder.morningTextView.setText("Matin: " + medicItem.getMorning());
        holder.noonTextView.setText("Midi: " + medicItem.getNoon());
        holder.eveningTextView.setText("Soir: " + medicItem.getEvening());
    }

    @Override
    public int getItemCount() {
        return mMedicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView startDateTextView;
        TextView endDateTextView;
        TextView morningTextView;
        TextView noonTextView;
        TextView eveningTextView;

        ViewHolder(View itemView) {
            super(itemView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            endDateTextView = itemView.findViewById(R.id.endDateTextView);
            morningTextView = itemView.findViewById(R.id.morningTextView);
            noonTextView = itemView.findViewById(R.id.noonTextView);
            eveningTextView = itemView.findViewById(R.id.eveningTextView);
        }
    }
}