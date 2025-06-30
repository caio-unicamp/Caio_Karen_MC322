package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_FILE = "mission_log.txt";
    private static PrintWriter printWriter;

    static {
        try {
            // O 'true' no FileWriter habilita o modo de apêndice
            FileWriter fileWriter = new FileWriter(LOG_FILE, true);
            printWriter = new PrintWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        printWriter.println(timestamp + " - " + message);
        printWriter.flush(); // Garante que a mensagem seja escrita imediatamente
    }

    public static void close() {
        if (printWriter != null) {
            printWriter.close();
        }
    }
}