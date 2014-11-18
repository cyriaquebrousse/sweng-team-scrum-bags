package ch.epfl.scrumtool.database.google.operations;

/**
 * 
 * @author aschneuw
 *
 * Based on: http://netzkinder.blogspot.ch/2013/03/android-exception-handling-in-asynctasks.html 19.11.2014
 * @param <A>
 */

public final class OperationResult<A> {
    private Exception exception;  
    
    private A result;  
  
    public OperationResult(final A result) {  
        this.result = result;  
    }  
  
    public OperationResult(final Exception exception) {  
        this.exception = exception;  
    }  
  
    public boolean isValid() {  
        return exception == null;  
    }  
  
    public Exception getException() {  
        return exception;  
    }  
  
    public A getResult() {  
        return result;  
    }  

}
