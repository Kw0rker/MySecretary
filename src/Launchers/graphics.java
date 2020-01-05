package Launchers;

import javax.swing.*;

public class graphics extends launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame=new JFrame();
            new graphics(frame);
        });
    }
    private graphics(JFrame frame){
        frame.setSize(400,400);
    }
    @Override
    public void printMessage(String message) {

    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String respond(String message) {
        return null;
    }
}
