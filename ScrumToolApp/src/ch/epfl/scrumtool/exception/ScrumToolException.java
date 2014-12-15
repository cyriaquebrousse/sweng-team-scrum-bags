package ch.epfl.scrumtool.exception;

/**
 * Exception thrown when a general application error (Client & Server side) happends
 * & we want to pass some information about that to the user
 * 
 * @author aschneuw
 * @author zenhaeus
 */
public class ScrumToolException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_GUI_MESSAGE = "ScrumTool Exception";
    private final String guiMessage;
    
    /**
     * Default constructor
     * Initialized defualt GUI message
     */
    public ScrumToolException() {
        super();
        guiMessage = DEFAULT_GUI_MESSAGE;
    }
    
    /**
     * 
     * @param message
     * @param guiMessage
     */
    public ScrumToolException(String message, String guiMessage) {
        super(message);
        this.guiMessage = initGuiMessage(guiMessage);
    }
    
    /**
     * 
     * @param throwable
     * @param guiMessage
     */
    public ScrumToolException(Throwable throwable, String guiMessage) {
        super(throwable);
        this.guiMessage = initGuiMessage(guiMessage);
    }
    
    /**
     * 
     * @param guiMessage
     * @return
     */
    private String initGuiMessage(final String guiMessage) {
        if (guiMessage == null) {
            return DEFAULT_GUI_MESSAGE;
        } else {
            return guiMessage;
        }
    }
    
    /**
     * 
     * @return
     */
    public String getGUIMessage() {
        return this.guiMessage;
    }
}