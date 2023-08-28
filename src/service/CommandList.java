package service;

public enum CommandList {

    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private String command;

    CommandList(String command){
        this.command = command;
    }

    public static CommandList valueOfLabel(String command) {
        for (CommandList e : values()) {
            if (e.command.equals(command)) {
                return e;
            }
        }
        return null;
    }

    public String getCommand() {
        return command;
    }
}
