package com.example.duan1_nhom6.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.Model.FeedBack;
import com.example.duan1_nhom6.R;

import java.util.ArrayList;

public class ManagerFeedbackAdapter extends RecyclerView.Adapter<ManagerFeedbackAdapter.ManagerFeedbackHolder>{

    ArrayList<FeedBack> mFeedback;
    Context context;

    public ManagerFeedbackAdapter(ArrayList<FeedBack> list, Context context) {
        this.mFeedback = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ManagerFeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manager_feedback_item, parent, false);
        return new ManagerFeedbackAdapter.ManagerFeedbackHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ManagerFeedbackHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mFeedback.size();
    }

    public class ManagerFeedbackHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;


        public ManagerFeedbackHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_massge);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen_status);

        }
    }
}
