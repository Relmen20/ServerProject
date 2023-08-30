package model;

public enum CommandList {

    CREATE("create"),
    READ("read"),
    READ_ALL("read all"),
    UPDATE("update"),
    DELETE("delete");

    private String command;

    CommandList(String command){
        this.command = command;
    }

    public static CommandList fromString(String text) {
        for (CommandList b : CommandList.values()) {
            if (b.command.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String getCommand() {
        return command;
    }
}
