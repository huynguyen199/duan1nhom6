package com.example.duan1_nhom6.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.Model.FeedBack;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ManagerFeedbackAdapter extends RecyclerView.Adapter<ManagerFeedbackAdapter.ManagerFeedbackHolder>{

    ArrayList<FeedBack> mFeedback;
    Context context;
    DatabaseReference databaseFeedBack;
    DatabaseReference databaseUsers;

    EditText idFeedbackET,reasonET,describeET,senderET;
    Button insertBtn;
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
    public void onBindViewHolder(@NonNull final ManagerFeedbackHolder holder, int position) {
        final FeedBack feedBack = mFeedback.get(position);
        databaseFeedBack = FirebaseDatabase.getInstance().getReference("FeedBack");
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        holder.reasonText.setText("Lý do: " + feedBack.getReason());
        holder.describeText.setText("Miêu tả: "+feedBack.getDescribe());

        databaseUsers.child(feedBack.getSender()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                        User user = snapshot.getValue(User.class);
                        if(user.getImageURL() == "default"){
                            Picasso.get().load(R.drawable.ic_user).into(holder.image_Feedback);
                        }else{
                            Picasso.get().load(user.getImageURL()).into(holder.image_Feedback);
                        }
                        holder.senderText.setText("Người gửi: "+user.getFullname());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.ImageDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseFeedBack.child(feedBack.getId()).removeValue();
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(v.getContext())
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.dialog_feedback))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                final View view = dialog.getHolderView();
                view.setTag(dialog);
                idFeedbackET = view.findViewById(R.id.edit_idPhone);
                reasonET = view.findViewById(R.id.edit_price);
                describeET = view.findViewById(R.id.edit_producer);
                senderET = view.findViewById(R.id.edit_phoneName);
                insertBtn = view.findViewById(R.id.updatePhone);

                insertBtn.setText("Cập nhật");
                idFeedbackET.setText(feedBack.getId());
                reasonET.setText(feedBack.getReason());
                describeET.setText(feedBack.getDescribe());
                senderET.setText(feedBack.getSender());

                dialog.show();
            }
        });


        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    User user = null;
                    Log.d("tpma", "ton tai");
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        user = dataSnapshot.getValue(User.class);
                        Log.d("user", "user" + user.getFullname());
                    }
                    if (user != null) {
                        if (feedBack.getSender().equals(user.getId())) {
                            holder.senderText.setText(user.getFullname());
                            Log.d("user", "vao" + user.getFullname());
                            if (user.getImageURL().equals("default")) {
                                holder.image_Feedback.setImageResource(R.drawable.ic_user);
                            } else {
                                Picasso.get().load(user.getImageURL()).into(holder.image_Feedback);

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeedback.size();
    }

    public class ManagerFeedbackHolder extends RecyclerView.ViewHolder {
        public ImageView image_Feedback,ImageDel;
        public TextView senderText;
        public TextView describeText;
        public TextView reasonText;


        public ManagerFeedbackHolder(@NonNull View itemView) {
            super(itemView);

            senderText = itemView.findViewById(R.id.edit_phoneName);
            image_Feedback = itemView.findViewById(R.id.image_feedback);
            describeText = itemView.findViewById(R.id.describe);
            reasonText = itemView.findViewById(R.id.reason);
            ImageDel= itemView.findViewById(R.id.imageDel);

        }
    }
}
