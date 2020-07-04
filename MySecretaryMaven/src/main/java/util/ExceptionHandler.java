package util;

import Secretary.Secretary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    static private final String email = "ruslankhamroev@gmail.com";
    static Secretary secretary;

    public ExceptionHandler(Secretary secretary) {
        ExceptionHandler.secretary = secretary;
    }

    public static void logException(Thread t, Throwable e) {
        StringBuilder exception = new StringBuilder();
        StackTraceElement[] elements = e.getStackTrace();
        if (e instanceof NullPointerException || e instanceof ArrayIndexOutOfBoundsException || e instanceof Error)
            exception.append("CRITICAL ERROR\n");
        exception.append("Exception occurred in Thread with id: ").append(t.getId());
        for (StackTraceElement el : elements) exception.append(el.toString()).append("\n   ");
        File file = new File("/home/exception.log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(exception.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        secretary.redirect(exception.toString(), "Exception Handled", email);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logException(t, e);
    }

}
