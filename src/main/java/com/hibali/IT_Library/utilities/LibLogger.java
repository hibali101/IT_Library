package com.hibali.IT_Library.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class LibLogger {
    private File outputFile;
    private LogLevel level;

    private LibLogger(File file, LogLevel level) {
        this.outputFile = file;
        this.level = level;
    }

    public static LibLogger getInfoLogger() {
        return getInstance(LogLevel.INFO);
    }

    public static LibLogger getWarningLogger() {
        return getInstance(LogLevel.WARNING);
    }

    public static LibLogger getSevereLogger() {
        return getInstance(LogLevel.SEVERE);
    }

    public void log(String message) {
        writer((LogLevel l, String m, LinkedHashMap<String,String> prop)->{
            String line = buildLog(l, message, prop);
            return line;
        }, message, new LinkedHashMap<>());
    }

    public void log(String message, LinkedHashMap<String, String> properties) {
        writer((LogLevel l, String m, LinkedHashMap<String,String> prop)->{
            String line = buildLog(l, message, prop);
            return line;
        }, message, properties);
    }

    public static Optional<String> getFileName(LogLevel level) {
        Properties props = new Properties();
        Optional<String> folderName = Optional.empty();
        try {
            props.load(new FileInputStream(".env"));
            folderName = Optional.ofNullable(props.getProperty("LOG_FOLDERNAME"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(folderName.isPresent()){
            File folder = new File(folderName.get());
                if(!folder.exists()){
                    folder.mkdir();
                }
            return Optional.ofNullable(new File(folder,level+"Logger.log").getPath());
        }
        return Optional.ofNullable(level+"Logger.log");
    }

    private String buildLog(LogLevel level, String message, LinkedHashMap<String, String> properties) {
        StringBuilder line = new StringBuilder();
        Date now = new Date();
        line.append("\"time\": \"").append(now).append("\", \"level\": \"").append(level)
                .append("\", \"message\": \"").append(message).append("\"");
        try{
            Set<String> keys = properties.keySet();
            for (String key : keys) {
                line.append(", \"").append(key).append("\":\"").append(properties.get(key))
                        .append("\"");
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return line.toString();
    }

    private void writer(InnerLibLogger interf, String message, LinkedHashMap<String, String> properties) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.outputFile, true)))) {
            String line = interf.exec(this.level, message, properties);
            out.println(line);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static LibLogger getInstance(LogLevel level){
        Optional<String> outputFileName = getFileName(level);
        if (outputFileName.isEmpty()) {
            return null;
        }
        File file = new File(outputFileName.get());
        return new LibLogger(file, level);
    }

    public static void main(String[] args) {
        LibLogger infoLogger = LibLogger.getInfoLogger();
        infoLogger.log("hehehehe");
    }

    @FunctionalInterface
    interface InnerLibLogger {
        String exec(LogLevel level, String message, LinkedHashMap<String, String> properties);
    }
}

enum LogLevel {
    INFO, WARNING, SEVERE
}
