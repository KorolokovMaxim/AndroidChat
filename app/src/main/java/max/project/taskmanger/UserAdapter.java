package max.project.taskmanger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    private ArrayList<User> users;
    private OnUserClickListener listener;
    String theLastMessage;


    public interface OnUserClickListener {
        void onUserClick(int position);
    }


    public void setOnUserClickListener(OnUserClickListener listener) {
        this.listener = listener;
    }

    public UserAdapter(ArrayList<User> users) {
        this.users = users;

    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User currentUser = users.get(position);
        holder.avatarImageView.setImageResource(currentUser.getAvatarMockUpResource());
        holder.userNameTextView.setText(currentUser.getName());
        lastMessages(currentUser.getId() , holder.subMessageUerTV);
    }




    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatarImageView;
        public TextView userNameTextView;
        public TextView subMessageUerTV;

        public UserViewHolder(@NonNull View itemView, final OnUserClickListener listener) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            subMessageUerTV = itemView.findViewById(R.id.subMessageUerTV);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUserClick(position);
                        }
                    }
                }
            });
        }
    }


    private void lastMessages(final String userId, final TextView subMessage) {


        theLastMessage = "default";

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("message");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TaskMessage message = dataSnapshot.getValue(TaskMessage.class);
                    if (message.getRecipient().equals(firebaseUser.getUid()) && message.getSender().equals(userId)
                            || message.getRecipient().equals(userId) && message.getSender().equals(firebaseUser.getUid())
                    ) {
                        theLastMessage = message.getText();
                    }

                }

                switch (theLastMessage){
                    case "default" :
                        subMessage.setText("No Message");
                        break;

                    default:
                        subMessage.setText(theLastMessage);
                        break;
                }
                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
