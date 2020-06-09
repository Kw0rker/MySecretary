package util;

import Secretary.Secretary;

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
        secretary.redirect(exception.toString(), "Exception Handled", email);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logException(t, e);
    }

}
