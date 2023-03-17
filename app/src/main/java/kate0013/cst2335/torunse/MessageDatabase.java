package kate0013.cst2335.torunse;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kate0013.cst2335.torunse.data.ChatMessage;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract ChatMessageDAO chatMessageDAO();
}
