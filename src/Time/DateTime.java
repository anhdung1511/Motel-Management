/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Time;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author nguye
 */
public class DateTime {
    public void showTime(JLabel time) {
        new Timer(0, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date d = new Date();
            SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
            time.setText(s.format(d));
        }
        }).start();
    }
        
    public void showDate(JLabel date ){
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("EEEE dd, MMM, yyyy");
        date.setText(s.format(d));
    }
}
