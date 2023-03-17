package kate0013.cst2335.torunse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kate0013.cst2335.torunse.data.ChatMessage;

@Dao
public interface ChatMessageDAO {

    @Insert
    long insertMessage(ChatMessage chatMessage);

    @Query("SELECT * FROM ChatMessage;")
    List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage chatMessage);
}

