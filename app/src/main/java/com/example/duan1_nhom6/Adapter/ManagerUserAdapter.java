package com.example.duan1_nhom6.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.Dao.DaoUsers;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ManagerUserAdapter extends RecyclerView.Adapter<ManagerUserAdapter.ManagerUserHolder>{
    Context context;
    ArrayList<User> mUsers;
    DatabaseReference databaseUsers;
    DaoUsers daoUsers;
    public ManagerUserAdapter(Context context, ArrayList<User> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
    }

    @NonNull
    @Override
    public ManagerUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manager_user_item, parent,false);

        return new ManagerUserAdapter.ManagerUserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerUserHolder holder, final int position) {
            User user = mUsers.get(position);

            if(user.getImageURL().equals("default")){
                holder.imageUser.setImageResource(R.drawable.ic_user);
            }else{
                Picasso.get().load(user.getImageURL()).into(holder.imageUser);
            }
            holder.nameUser.setText(user.getFullname());
            holder.NumberPhone.setText(user.getNumberphone());
            holder.NumberPhone.setText(user.getNumberphone());
            holder.RoleUser.setText(user.getRole());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(position);
                }
            });

    }
    public void updateProfile(int position){
        final User user = mUsers.get(position);
        final DialogPlus dialog = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.dialog_manger_users))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        final View view = dialog.getHolderView();
        view.setTag(dialog);
        final EditText edit_fullname = view.findViewById(R.id.edit_fullname);
        final EditText edit_numberphone = view.findViewById(R.id.edit_idPhone);
        final Spinner spinner = view.findViewById(R.id.spinner_role);
        Button updateBtn = view.findViewById(R.id.updateBtn);

        edit_fullname.setText(user.getFullname());
        edit_numberphone.setText(user.getNumberphone());
        List<String> categories = new ArrayList<String>();
        categories.add("user");
        categories.add("admin");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = edit_fullname.getText().toString();
                String numberphone = edit_numberphone.getText().toString();
                String role = spinner.getSelectedItem().toString();
                User user1 = new User(fullname,numberphone,role);
                user1.setId(user.getId());
                daoUsers = new DaoUsers();
                daoUsers.Update(user1);
//                databaseUsers.child(user.getId()).updateChildren(user1.updateUser());
            }
        });

        dialog.show();

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ManagerUserHolder extends RecyclerView.ViewHolder{
        public TextView nameUser,RoleUser,NumberPhone;
        public ImageView imageUser,ImageDel;


        public ManagerUserHolder(@NonNull View itemView) {
            super(itemView);
            nameUser = itemView.findViewById(R.id.nameUser);
            RoleUser = itemView.findViewById(R.id.Role);
            NumberPhone = itemView.findViewById(R.id.numberPhone);
            imageUser = itemView.findViewById(R.id.image_managerUser);


        }
    }
}
