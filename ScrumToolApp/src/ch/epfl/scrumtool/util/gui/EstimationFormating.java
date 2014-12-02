package ch.epfl.scrumtool.util.gui;

/**
 * @author AlexVeuthey
 *
 */
public class EstimationFormating {
    
    public static String estimationAsHourFormat(float est) {
        int hour = (int) est;
        
        final int min = 60;
        int minutes = (int) ((est - hour) * min);
        
        String zero = "";
        if (minutes == 0) {
            zero = "0";
        }
        
        return hour + ":" + minutes + zero;
    }
}
