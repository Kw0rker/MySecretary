package Launchers;

import interfaces.answerable;

import javax.swing.*;
import java.awt.*;

public class graphics extends launcher {
    private TextField textLog=new TextField();
    private TextField message=new TextField();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame frame=new JFrame();
            new graphics(frame);
        });
    }
    private graphics(JFrame frame){
        frame.setSize(400,400);
        frame.setBackground(Color.DARK_GRAY);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BorderLayout layout =new BorderLayout();
        frame.setLayout(layout);

        JButton button =new JButton("Отправить");
        button.setBackground(Color.LIGHT_GRAY);
        GridLayout gridLayout =new GridLayout(1,2);
        gridLayout.addLayoutComponent("",message);
        gridLayout.addLayoutComponent("",button);
        ScrollPane scrollPane=new ScrollPane();
        layout.addLayoutComponent(scrollPane,BorderLayout.CENTER);
        scrollPane.add(textLog);
        //layout.addLayoutComponent(gridLayout(1),BorderLayout.NORTH);

    }
    @Override
    public void printMessage(String message) {
       // textLog
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String respond(String message) {
        return null;
    }

    @Override
    public void setReply(String message) {

    }

    @Override
    public void restart() {

    }

    @Override
    public answerable getInstance() {
        return null;
    }
}
