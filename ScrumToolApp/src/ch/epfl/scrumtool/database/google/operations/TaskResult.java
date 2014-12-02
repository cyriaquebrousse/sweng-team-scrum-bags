package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.exception.ScrumToolException;
import ch.epfl.scrumtool.util.Preconditions;

/**
 * 
 * @author aschneuw
 *
 * Based on: http://netzkinder.blogspot.ch/2013/03/android-exception-handling-in-asynctasks.html 19.11.2014
 * @param <A>
 */

public final class TaskResult<A> {
    private ScrumToolException exception;  
    
    private A result;  
  
    public TaskResult(final A result) {
        Preconditions.throwIfNull("Must contain a valid result", result);
        this.result = result;  
    }  
  
    public TaskResult(final ScrumToolException exception) {
        Preconditions.throwIfNull("Must contain a valid exception", exception);
        this.exception = exception;  
    }  
  
    public boolean isValid() {  
        return exception == null;  
    }  
  
    public ScrumToolException getException() {  
        return exception;  
    }  
  
    public A getResult() {  
        return result;  
    }  

}
