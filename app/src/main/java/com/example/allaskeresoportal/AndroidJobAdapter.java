package com.example.allaskeresoportal;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AndroidJobAdapter extends RecyclerView.Adapter<AndroidJobAdapter.AndroidJobHolder> implements Filterable {
    private ArrayList<AndroidJobEntity> jobsData;
    private ArrayList<AndroidJobEntity> allJobsData;
    private Context jobContext;
    private int lastPos = -1;


    public AndroidJobAdapter(Context context, ArrayList<AndroidJobEntity> jobsData) {
        this.jobsData = jobsData;
        this.allJobsData = jobsData;
        this.jobContext = context;
    }
    @NonNull
    @Override
    public AndroidJobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AndroidJobHolder(LayoutInflater.from(jobContext).inflate(R.layout.list_job_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AndroidJobHolder holder, int position) {
        AndroidJobEntity entity = jobsData.get(position);

        holder.bindTo(entity);

        if(holder.getAbsoluteAdapterPosition() > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(jobContext, R.anim.slide_to_top);
            holder.itemView.startAnimation(animation);
            lastPos = holder.getAbsoluteAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return jobsData.size();
    }

    private Filter androidJobFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<AndroidJobEntity> filteredJobs = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0) {
                results.count = allJobsData.size();
                results.values = allJobsData;
                return results;
            }

            String filterPattern = constraint.toString().toLowerCase().trim();

            for(AndroidJobEntity entity : allJobsData) {
                if(entity.getName().toLowerCase().contains(filterPattern)) {
                    filteredJobs.add(entity);
                }
            }
            results.count = filteredJobs.size();
            results.values = filteredJobs;
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            jobsData = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return androidJobFilter;
    }

    class AndroidJobHolder extends RecyclerView.ViewHolder {

        private TextView jobNameTextView;
        private TextView jobCreatedByTextView;
        private TextView shortDescriptionTextView;

        public AndroidJobHolder(@NonNull View itemView) {
            super(itemView);
            jobNameTextView = itemView.findViewById(R.id.jobNameTextView);
            jobCreatedByTextView = itemView.findViewById(R.id.jobCreatedByTextView);
            shortDescriptionTextView = itemView.findViewById(R.id.shortDescriptionTextView);
            itemView.findViewById(R.id.detailsButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: részletek megnyitása
                }
            });
        }

        public void bindTo(AndroidJobEntity entity) {
            jobNameTextView.setText(entity.getName());
            jobCreatedByTextView.setText(entity.getCreatedBy());
            shortDescriptionTextView.setText(entity.getShortDesc());
        }
    }
}
