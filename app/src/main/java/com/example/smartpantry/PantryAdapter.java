package com.example.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    public interface Listener {
        void onEdit(PantryItem item);

        void onDelete(PantryItem item);
    }

    private final List<PantryItem> items = new ArrayList<>();
    private final Listener listener;

    public PantryAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setItems(List<PantryItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.name.setText(AppUtils.capitalize(item.getName()));
        holder.quantity.setText(AppUtils.formatQuantity(item.getQuantity()) + " " + item.getUnit());

        int normalColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.grey_600);
        int warningColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.red_700);
        long days = AppUtils.daysUntil(item.getExpiry());

        if (days == Long.MAX_VALUE) {
            holder.expiry.setText("No expiry date");
            holder.expiry.setTextColor(normalColor);
        } else if (days < 0) {
            holder.expiry.setText("Expired on " + item.getExpiry());
            holder.expiry.setTextColor(warningColor);
        } else if (days <= AppUtils.EXPIRY_WARNING_DAYS) {
            holder.expiry.setText("Expires in " + days + (days == 1 ? " day" : " days") + " (" + item.getExpiry() + ")");
            holder.expiry.setTextColor(warningColor);
        } else {
            holder.expiry.setText("Expires: " + item.getExpiry());
            holder.expiry.setTextColor(normalColor);
        }

        holder.itemView.setOnClickListener(v -> listener.onEdit(item));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView quantity;
        final TextView expiry;
        final ImageButton deleteButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            quantity = itemView.findViewById(R.id.item_quantity);
            expiry = itemView.findViewById(R.id.item_expiry);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
