package com.hibali.IT_Library.http.testServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ClientTester {
    public static void main(String[] args) {
        try {
            URL url = new URI("http://localhost:8080").toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))){
                String line;
                while((line = in.readLine()) != null){
                    System.out.println(line);
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
