package theo.androidproject.medictime.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
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
        holder.dateTextView.setText(medicItem.getDateBegin());
        holder.morningMedications.setText(medicItem.getMorning());
        holder.noonMedications.setText(medicItem.getNoon());
        holder.eveningMedications.setText(medicItem.getEvening());

        holder.morningMedications.setOnClickListener(v -> openMedicFragment(v, medicItem, "morning"));
        holder.noonMedications.setOnClickListener(v -> openMedicFragment(v, medicItem, "noon"));
        holder.eveningMedications.setOnClickListener(v -> openMedicFragment(v, medicItem,"evening"));
    }

    private void openMedicFragment(View view, MedicItem medicItem, String timeOfDay) {
        FragmentActivity activity = (FragmentActivity) view.getContext();

        // Créer un MedicFragment et y ajouter les données du médicament
        MedicFragment medicFragment = new MedicFragment();
        Bundle args = new Bundle();
        args.putInt("ID", medicItem.getID());

        medicFragment.setArguments(args);

        // Remplacer le fragment actuel par MedicFragment
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, medicFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public int getItemCount() {
        return mMedicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView morningMedications;
        TextView noonMedications;
        TextView eveningMedications;

        ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            morningMedications = itemView.findViewById(R.id.morningMedications);
            noonMedications = itemView.findViewById(R.id.noonMedications);
            eveningMedications = itemView.findViewById(R.id.eveningMedications);
        }
    }
}