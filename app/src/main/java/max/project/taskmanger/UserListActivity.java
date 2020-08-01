package max.project.taskmanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private String userName;

    private DatabaseReference databaseReference;
    private ChildEventListener usersChildEventListener;
    private ChildEventListener chatChildListener;
    private ArrayList<User> usersArrayList;
    private ArrayList<TaskMessage> messageArrayList;
    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private RecyclerView.LayoutManager userLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);



        Intent intent = getIntent();
        if(intent != null){
            userName = intent.getStringExtra("userName");
        }



        auth = FirebaseAuth.getInstance();
        usersArrayList = new ArrayList<>();
        attachUserDatabaseReferenceListener();
        buildRecyclerView();


    }

    private void attachUserDatabaseReferenceListener() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        if(usersChildEventListener == null){
            usersChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    User user = snapshot.getValue(User.class);

                    if(!user.getId().equals(auth.getCurrentUser().getUid())){

                        user.setAvatarMockUpResource(R.drawable.ic_baseline_person_24);
                        usersArrayList.add(user);
                        userAdapter.notifyDataSetChanged();
                    }
                    }


                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    TaskMessage message = snapshot.getValue(TaskMessage.class);


                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            databaseReference.addChildEventListener(usersChildEventListener);
        }
    }

    private void buildRecyclerView() {
        userRecyclerView = findViewById(R.id.userListRecyclerView);
        userRecyclerView.setHasFixedSize(true);

        userRecyclerView.addItemDecoration(new DividerItemDecoration(userRecyclerView.getContext(),LinearLayoutManager.VERTICAL));

        userLayoutManager = new LinearLayoutManager(this);
        userAdapter = new UserAdapter(usersArrayList);

        userRecyclerView.setLayoutManager(userLayoutManager);
        userRecyclerView.setAdapter(userAdapter);

        userAdapter.setOnUserClickListener(new UserAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int position) {
                goToChat(position);
            }
        });
    }

    private void goToChat(int position) {
        Intent intent = new Intent(UserListActivity.this ,
                ChatActivity.class);
        intent.putExtra("recipientUserId" , usersArrayList.get(position).getId());
        intent.putExtra("recipientUserName" , usersArrayList.get(position).getName());
        intent.putExtra("userName" , userName);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionMode mode;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu , menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sing_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserListActivity.this , SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}