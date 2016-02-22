import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MessageRepository {

    private ArrayList<Message> messages;

    public MessageRepository() {
        messages = new ArrayList<>();
    }

    //читает сообщение из файла
    public boolean readMessages() {

        Logger.add("Attempt to get messages\n");
        try {
            Reader reader = new FileReader("messages.json");
            Gson gson = new GsonBuilder().create();

            Message[] messageArray = gson.fromJson(reader, Message[].class);

            Collections.addAll(messages, messageArray);

            reader.close();
            Logger.add("Note: " + messages.size() + " messages were added to chat at " + System.currentTimeMillis() + "\n\r");
        } catch (Exception e) {
            Logger.add(e);
            return false;
        }
        return true;
    }

    //сохраняет сообщение в файл
    public boolean saveMessages() {
        Logger.add("Attempt to save messages\n");
        try {
            Writer writer = new FileWriter("messages.json");
            writer.write(new Gson().toJson(messages));
            writer.close();
            Logger.add(messages.size() + " messages were added to file at " + System.currentTimeMillis() + "\n\r");
        } catch (Exception e) {
            Logger.add(e);
            return false;
        }
        return true;
    }

//Добавляет сообщение
    public boolean addMessage(String message, String author) {

        Logger.add("Attempt to create new message\n");
        if (message.isEmpty()) {
            Logger.add("Error: Attempt to send empty message at " + System.currentTimeMillis() + "\n\r");
            return false;
        } else if (message.length() >= 140) {
            Logger.add("Warning: Message length is higher than 140  symbols " + System.currentTimeMillis() + "\n\r");
        } else {
            messages.add(new Message(author, System.currentTimeMillis(), message));
        }
        return true;
    }


//Возврашает список всех сообщений
    public ArrayList<Message> getAllMessages() {
        Logger.add("Attempt to list all messages\n");
        return messages;
    }


   //удаляет сообщение по его идентификатору
    public boolean removeMessage(String id) {
        Logger.add("Attempt to remove message by ID\n");
        if (id.isEmpty()) {
            Logger.add("Error: The id is empty\n\r");
            return false;
        }

        return messages.removeIf(m -> m.getId().equalsIgnoreCase(id));
    }

    //Возвращает список всех сообщений, найденных по автору, иаче возвращает null
    public ArrayList<Message> findMessagesAuthor(String author) {
        Logger.add("Attempt to find message by author\n");
        if (author.isEmpty()) {
            Logger.add("Error: The name of author hasn't been added\n\r");
            return null;
        }
        ArrayList<Message> filteredMessages =
                messages.stream()
                        .filter(m -> m.getAuthor().equalsIgnoreCase(author))
        .collect(Collectors.toCollection(ArrayList::new));

        Logger.add("Note: " + filteredMessages.size() + " messages found\n\r");
        return filteredMessages;
    }

    //Находит сообщение, которое содержит данный текст
    public ArrayList<Message> findMessageText(String text) {
        Logger.add("Attempt to find messages containing text\n");
        if (text.isEmpty()) {
            Logger.add("Error: The search line is empty\n\r");
            return null;
        }

        ArrayList<Message> filteredMessages =
                messages.stream()
                        .filter(m -> m.getMessage().contains(text))
                        .collect(Collectors.toCollection(ArrayList::new));

        Logger.add("Note: " + filteredMessages.size() + " messages containing ******" + text + "******found\n\r");
        return filteredMessages;
    }

    //Возвращает сообщение по регулярному выражению
    public ArrayList<Message> findMessagesRegex(Pattern pattern) throws Exception {
        Matcher matcher;
        Logger.add("Attempt to find messages by regex\n");
        ArrayList<Message> result = new ArrayList<>();
        try {
            for (Message m : messages) {
                matcher = pattern.matcher(m.getMessage());
                if (matcher.find())
                    result.add(m);
            }
            Logger.add("Note: " + result.size() + " messages containing pattern ******" + pattern + "******found\n\r");
        } catch (Exception e) {
            Logger.add("Error:" + e.getMessage() + "\r\n");
        }
        return result;
    }

//Возвращает сообщения за определённый период времени
    public ArrayList<Message> findMessagePeriod(Long beginning, Long ending) {
        Logger.add("Attempt to find messages by period\n");
        if (beginning <= 0) {
            Logger.add("Error: No starting date, all messages are listed\n\r");
            return messages;
        }
        if (ending <= 0) {
            Logger.add("Warning: No ending date found, all messages from " + beginning + " till the end of the chat are listed\r");
            ArrayList<Message> filteredMessages =
                    messages.stream()
                            .filter(o -> o.getTimestamp() >= beginning)
                            .collect(Collectors.toCollection(ArrayList::new));
            Logger.add("Note: " + filteredMessages.size() + " messages starting from *** " + beginning + " *** found\n\r");
            return filteredMessages;
        }
        ArrayList<Message> filteredMessages =
                messages.stream()
                        .filter(o -> o.getTimestamp() >= beginning &&
                                o.getTimestamp() <= ending)
                        .collect(Collectors.toCollection(ArrayList::new));

        Logger.add("Note: " + filteredMessages.size() + " messages starting from *** " + beginning + "*** ending *** " + ending + " *** found\n\r");
        return filteredMessages;
    }
}