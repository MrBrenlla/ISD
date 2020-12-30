package es.udc.ws.isd060.runfic.client.responses;

public class OperationalErrorException extends Exception implements ConsoleOutput {
    private Exception causedBy;
    private String[] parameters;

    public OperationalErrorException ( Exception causedBy , String[] parameters) {
        super("Error during operation "+parameters.toString());
        this.causedBy=causedBy;
        this.parameters=parameters;
    }

    public OperationalErrorException(Exception causedBy) {
        super(causedBy);
        this.causedBy = causedBy;
    }

    public Exception getCausedBy() {
        return causedBy;
    }

    public String[] getParameters() {
        return parameters;
    }

    @Override
    public void consoleOutput() {
        System.out.println("ERROR :"+parameters[0].toString()+" "+ parameters[1].toString()
                +"\n"+causedBy.getMessage()+"\n");
    }
}
