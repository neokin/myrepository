import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class GnuTerminal {
    boolean running = true;
    Queue<String> queue = new LinkedList<String>() {
    };
    public void start() {

        while (running) {
            System.out.print((char) 27 + "[1;31m user" + (char) 27 + "[0m:~# ");

            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String str;
                StringTokenizer st = new StringTokenizer(bufferRead.readLine(), " ");
                while (st.hasMoreTokens()) {
                   assert false;
                    str = st.nextToken();
                    queue.add(str);
                }


                assert false;
                String command = queue.poll();
                String param = queue.poll();
                switch (command) {
                    case "help":
                        if (param!=null)
                            warning();
                        help();
                        break;

                    case "show":
                        if (param!=null)
                            warning();
                        show();
                        break;

                    case "read":
                        if (param!=null)
                            warning();
                        read();
                        break;

                    case "save":
                        if (param!=null)
                            warning();
                        save();
                        break;

                    case "add":
                        if (param!=null)
                            warning();
                        add();
                        break;

                    case "find":
                        String command1 = queue.poll();
                        find(param, command1);

                    case "remove":
                        remove(param);
                        break;

                    case "exit":
                        if (param!=null)
                            warning();
                        running = false;
                        break;


                    default:
                        System.out.println("\"" + command + "\"" + "— command not found");
                        Logger.add("Warning: Attempt to use wrong command at UI \n\r");
                        break;
                }

            } catch (IOException e) {
                Logger.add("Exception caught \n" + e.getMessage() + "\n at" + System.currentTimeMillis() + "\n\r");

            }
        }
    }
    public void help(){
        System.out.println((char) 27 + "[1;32m find "+ (char) 27 + "[0m");
        System.out.println("Find message by");
        System.out.println("-r regex. (ex: find -r [regex] ) ");
        System.out.println("-a author. (ex: find -a [author] ) ");
        System.out.println("-d period of time. (ex: find -d [firstyear] [lastyear] ) ");
        System.out.println("-m message (ex: find -m [message] ) \n");

        System.out.println((char) 27 + "[1;32m show "+ (char) 27 + "[0m");
        System.out.println("Show all messages\n");

        System.out.println((char) 27 + "[1;32m read "+ (char) 27 + "[0m");
        System.out.println("Read messages from the \"messages.json\" \n");

        System.out.println((char) 27 + "[1;32m save "+ (char) 27 + "[0m");
        System.out.println("Save messages to \"messages.json\" \n");

        System.out.println((char) 27 + "[1;32m add "+ (char) 27 + "[0m");
        System.out.println("Add message to \"messages.json\"\n");

        System.out.println((char) 27 + "[1;32m remove "+ (char) 27 + "[0m");
        System.out.println("Remove message by id ");
        System.out.println("(ex: remove [id] )\n");

        System.out.println((char) 27 + "[1;32m exit "+ (char) 27 + "[0m");
        System.out.println(" Exit the shell.\n");

    }

    MessageRepository repository = new MessageRepository();
    public void show() {
        ArrayList<Message>   message = repository.getAllMessages();
        if (message != null) {
            Logger.add("Note: " + message.size() + " are listed  \n\r");
            print(message);

        } else {
            System.out.print("No messages in chat \n\r");
            Logger.add("Warning: No messages in chat \n\r");
        }
    }

    public void read() {
        if (repository.readMessages()) {
            System.out.print("Messages added \n\r");
            Logger.add("Note: Messages were added successfully \n\r");
        } else {
            System.out.print("Warning: Something went wrong, see log file \n\r");
            Logger.add("Error: failed to read messages \n\r");
        }
    }

    public void save() {
        if (repository.saveMessages()) {
            System.out.print("Messages saved\n\r");
            Logger.add("Note: Messages were saved successfully \n\r");
        } else {
            System.out.print("Warning: Something went wrong, see log file \n\r");
            Logger.add("Error: failed to save messages \n\r");
        }
    }

    public void add() throws IOException {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter new message \n\r");
        String message = bufferRead.readLine();
        System.out.print("Enter author\n\r");
        String author = bufferRead.readLine();

        if (repository.addMessage(message, author)) {
            System.out.print("New message added \n\r");
            Logger.add("Note: New message added successfully \n\r");
        } else {
            System.out.print("Warning: Message or author fields are invalid \n\r");
            Logger.add("Error: failed to add message \n\r");
        }
    }

    public void find(String param, String option) {

        switch (param)
        {
            case "-d":
              String enddata;
                if (!queue.isEmpty())
                    enddata = queue.poll();
                else
                    enddata = param;
                if (option!=null) {
                    ArrayList<Message> message = repository.findMessagePeriod(Long.parseLong(option), Long.parseLong(enddata));
                    print(message);
                } else {
                    System.out.print("Wrong input");
                    Logger.add("Error: Wrong input while trying to find message by period");
                }

                break;
            case "-m":
                print(repository.findMessageText(option));
                break;

            case "-r":
                try {
                    ArrayList<Message> message = repository.findMessagesRegex(Pattern.compile(option));
                    print(message);
                } catch (Exception e) {
                    Logger.add("Error: " + e.getMessage() + "\n\r");
                }
                break;

            case "-a":
                ArrayList<Message> message = repository.findMessagesAuthor(option);
                print(message);
                break;
        }
    }

    public void remove(String id) {
        if (repository.removeMessage(id))
            Logger.add("Message with ID " + id + " was removed \n\r");
        else
            Logger.add("Message with ID " + id + " was not removed \n\r");
    }


    public void print(ArrayList<Message> message) {
        if (message != null && !message.isEmpty()) {
            message.forEach(m -> System.out.print("Author: " + m.getAuthor() + "\n " + m.getMessage() + "\n " +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(m.getTimestamp()) + "\n\r"));
        } else {
            Logger.add("Warning: Something no messages \n\r");
            System.out.print("No messages \n\r");
        }
    }


    public void warning() {
        System.out.println("This command doesn't require any options");
    }


}

