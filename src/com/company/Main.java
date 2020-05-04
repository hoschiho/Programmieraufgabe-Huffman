package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        String FILE_NAME = "exampletext.txt";

        //get the textfile as string
        String fileAsString = readFromFile(FILE_NAME);

        //create a map with the key as the ascii code and value as count.
        Map map = addCountToMap(fileAsString);





        //print out map
        for (Object i : map.keySet()) {
            System.out.println("key: " + i + " value: " + map.get(i));
        }
    }



    public static Map addCountToMap(String fileAsString){

        //fill a map with all chars and the value 0
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 127; i++) {
            map.put(i,0);
        }

        //itterates trough the string to count each letter.
        for (int i = 0; i < fileAsString.length(); i++) {
            int counter = 0;
            char c = fileAsString.charAt(i);

            for (int j = 0; j < fileAsString.length(); j++) {
                if(fileAsString.charAt(j) == c){
                    counter++;
                }
            }
            //convert char to ASCII
            int charInASCII = (int) c;

            //add ascii char & count to the map.
            map.put(charInASCII,counter);
        }

        return map;
    }

    //reads a textFile into a String (Aufgabe 1.)
    public static String readFromFile(String FILE_NAME) throws IOException {

        InputStream inputStream = new FileInputStream(FILE_NAME);
        BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));

        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();

        while(line != null){
            sb.append(line).append("\n");
            line = buf.readLine();
        }
        String fileAsString = sb.toString();

        return fileAsString;
    }
}
