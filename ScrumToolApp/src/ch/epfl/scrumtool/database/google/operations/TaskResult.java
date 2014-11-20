package ch.epfl.scrumtool.database.google.operations;

import ch.epfl.scrumtool.exception.ScrumToolException;

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
        this.result = result;  
    }  
  
    public TaskResult(final ScrumToolException exception) {  
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