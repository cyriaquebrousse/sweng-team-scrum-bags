package ch.epfl.scrumtool.exception;

/**
 * 
 * @author aschneuw, zenhaeus
 *
 */
public class ScrumToolException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_GUI_MESSAGE = "ScrumTool Exception";
    private final String guiMessage;
    
    public ScrumToolException() {
        super();
        guiMessage = DEFAULT_GUI_MESSAGE;
    }
    
    public ScrumToolException(String message, String guiMessage) {
        super(message);
        this.guiMessage = initGuiMessage(guiMessage);
    }

    public ScrumToolException(Throwable throwable, String guiMessage) {
        super(throwable);
        this.guiMessage = initGuiMessage(guiMessage);
    }
    
    private String initGuiMessage(final String guiMessage) {
        if (guiMessage == null) {
            return DEFAULT_GUI_MESSAGE;
        } else {
            return guiMessage;
        }
    }
    
    public String getGUIMessage() {
        return this.guiMessage;
    }
}
