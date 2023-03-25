package kate0013.cst2335.torunse;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import kate0013.cst2335.torunse.data.ChatMessage;
import kate0013.cst2335.torunse.databinding.LayoutDetailsBinding;

public class MessageDetails extends Fragment{
    ChatMessage chatMessage;


    public MessageDetails(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LayoutDetailsBinding binding = LayoutDetailsBinding.inflate(inflater);

        binding.fragmentMessageTextView.setText("Your Message: " + chatMessage.message);
        binding.fragmentTimeTextView.setText("Date of message: " + chatMessage.timeSent);
        binding.fragmentDBIdTextView.setText("DatabaseId: " + chatMessage.id);

        return binding.getRoot();
    }
}
