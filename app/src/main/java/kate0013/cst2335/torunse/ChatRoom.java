package kate0013.cst2335.torunse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kate0013.cst2335.torunse.data.ChatMessage;
import kate0013.cst2335.torunse.data.ChatRoomViewModel;
import kate0013.cst2335.torunse.databinding.ActivityChatRoomBinding;
import kate0013.cst2335.torunse.databinding.ReceiveMessageBinding;
import kate0013.cst2335.torunse.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private ChatMessage chatMessageObj;
    private ArrayList<ChatMessage> messages;
    private RecyclerView.Adapter adapter;
    private ChatMessageDAO chatMessageDAO;
    ChatRoomViewModel cvm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cvm=new ViewModelProvider(this).get(ChatRoomViewModel.class);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        chatMessageDAO = db.chatMessageDAO();



        if (messages == null) {
            messages = cvm.messages.getValue();
            cvm.messages.postValue(messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(chatMessageDAO.getAllMessages());
                runOnUiThread(() -> binding.recycleView.setAdapter(adapter));
            });
        }

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        cvm.selectedMessage.observe(this, newMessageValue -> {
            MessageDetails messageDetailsFragment = new MessageDetails(newMessageValue);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sw600Fragment, messageDetailsFragment)
                    .addToBackStack("")
                    .commit();
        });
        binding.sendBtn.setOnClickListener(click -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");

            String currentDetectedTime = simpleDateFormat.format(new Date());
            String txt = binding.messageEditText.getText().toString();
            chatMessageObj = new ChatMessage(txt, currentDetectedTime, true);
            messages.add(chatMessageObj);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                long id = chatMessageDAO.insertMessage(chatMessageObj);
                chatMessageObj.id = id;
            });
            adapter.notifyItemInserted(messages.size() - 1);
            binding.messageEditText.setText("");
        });

        binding.receiveBtn.setOnClickListener(click -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDetectedTime = simpleDateFormat.format(new Date());
            String txt = binding.messageEditText.getText().toString();
            chatMessageObj = new ChatMessage(txt, currentDetectedTime, false);
            messages.add(chatMessageObj);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                long id = chatMessageDAO.insertMessage((chatMessageObj));
                chatMessageObj.id = id;
            });
            adapter.notifyItemInserted(messages.size() - 1);
            binding.messageEditText.setText("");
        });

        adapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding sentMessageBinding = null;
                ReceiveMessageBinding receiveMessageBinding = null;
                if (viewType == 0) {
                    sentMessageBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(sentMessageBinding.getRoot());
                } else {
                    receiveMessageBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(receiveMessageBinding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage messagePosition = messages.get(position);
                holder.messageTextView.setText(messagePosition.getMessage());
                holder.timeTextView.setText(messagePosition.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (messages.get(position).getIsSendButton()) return 0;
                else return 1;
            }
        };

    }


    //the inner class
    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView messageTextView;
        public TextView timeTextView;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            messageTextView = itemView.findViewById(R.id.messageTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            itemView.setOnClickListener(click->{
                int position = getAdapterPosition();
                ChatMessage selectedMessage = messages.get(position);
                cvm.selectedMessage.postValue(selectedMessage);
            });
//            itemView.setOnClickListener(clk -> {
//                int position = getAdapterPosition();
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChatRoom.this);
//                alertBuilder.setMessage("Do you want to delete the message: " + messageTextView.getText().toString()).setTitle("Alert").setPositiveButton("Yes", (dialog, yesClick) -> {
//                    ChatMessage clickedMessage = messages.get(position);
//                    Executor thread = Executors.newSingleThreadExecutor();
//                    thread.execute(() -> {
//                        chatMessageDAO.deleteMessage(clickedMessage);
//                        messages.remove(position);
//                        runOnUiThread(() -> adapter.notifyItemRemoved(position));
//                    });
//                    Snackbar.make(messageTextView, "You deleted message #" + position, Snackbar.LENGTH_LONG).setAction("Undo", undoClick -> {
//                        thread.execute(() -> {
//                            chatMessageDAO.insertMessage(clickedMessage);
//                            messages.add(position, clickedMessage);
//                            runOnUiThread(() -> adapter.notifyItemInserted(position));
//                        });
//                    }).show();
//                }).setNegativeButton("No", (dialog, click) -> {
//                }).create().show();
//            });
        }
    }
}