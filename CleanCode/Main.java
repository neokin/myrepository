
public class Main {

    public static void main(String[] args) {
        System.out.println("This is a simulator of GNU bash.");
        System.out.println("Type 'help' to see command list.\n");
        GnuTerminal terminal = new GnuTerminal();
        terminal.start();
    }
}
