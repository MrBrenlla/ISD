package es.udc.ws.isd060.runfic.client.responses;

public class InvalidArgumentException extends Exception implements ConsoleOutput {

    private String expectedArgumentType;
    private String argument;

    public InvalidArgumentException() {
            super("Invalid Argument");
    }

    public InvalidArgumentException(String expectedArgumentType, String argument) {
        super("Invalid Argument "+ argument +" , could not be converted into "+expectedArgumentType);
        this.expectedArgumentType = expectedArgumentType;
        this.argument = argument;
    }

    public String getExpectedArgumentType() {
        return expectedArgumentType;
    }

    public String getArgument() {
        return argument;
    }

    @Override
    public void consoleOutput() {
        System.out.println("ERROR :"+argument+" NOT OF TYPE "+ expectedArgumentType);
    }
}
