
import java.util.UUID;
public class Message  {

    private String id;
    private String author;
    private Long timestamp;
    private String message;
    public Message(String author, long timestamp, String message) {

        this.id = (UUID.randomUUID()).toString();
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }
    public String getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }
}
