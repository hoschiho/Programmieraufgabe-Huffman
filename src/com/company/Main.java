package com.company;

import com.sun.source.tree.Tree;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        String FILE_NAME = "exampletext.txt";

        //get the textfile as string
        String fileAsString = readFromFile(FILE_NAME);
        int[] table = createTable(fileAsString);
        TreeMap map = createCode(table);
        saveToFile(map);



    }

    //Aufgabe 2 //
    private static int[] createTable(String fileAsString){
        int[] table = new int[256]; //position of element in array is char identifier.
        for (char c : fileAsString.toCharArray()) {
            if (c < 256) { //make sure to only get ascii characters
                table[c]++;
            }
        }
        return table;
    }

        //Create Huffman tree (Aufgabe 3.)//
private static TreeMap<String, String> createCode(int[] table) {

    PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> (o1.frequency < o2.frequency) ? -1 : 1); //sorted frequency table
    TreeMap<String, String> map = new TreeMap<>(); //table as treemap
    for (int i = 0; i < table.length; i++) {
        if ((table[i] > 0) && queue.add(new Node(String.valueOf(i), table[i]))) ; //saves char and frequency

    }
    Node left = null;
    Node right = null;
    Node top = null;

    //huffman coding
    while (queue.size() != 1) { //repeats until only one element is left in the queue

        //takes the two lowest nodes
        left = queue.poll();
        right = queue.poll();
        top = new Node(left, right); //generates a parent node with left and right
        queue.add(top); //adds parent node
    }

    getCodes(map, "", top); //use recursion to generate the huffman code

    return map;

    }

//fills the huffman tree
private static void getCodes(TreeMap<String, String> map, String code, Node top){
    if(top.left == null && top.right == null){ //takes only chars and not a parent node
        map.put(code,top.value); //puts the code and the value in the map
    } else{
        getCodes(map, code + "0", top.left); //if we go left, we add a 0 to the code
        getCodes(map, code + "1", top.right); //if we go right, we add a 1 to the code

    }
}

    //saves map to a textFile
    public static void saveToFile(TreeMap<String, String> map) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        //iterate trough map and save it as string
        for (Map.Entry<String, String> entry : map.entrySet()){
            sb.append(entry.getValue());
            sb.append(":");
            sb.append(entry.getKey());
            sb.append("-");
        }
        String huffmanAsString = sb.toString();

        //delete last delimeter
        if (huffmanAsString.charAt(huffmanAsString.length() -1) == '-') {
            huffmanAsString = huffmanAsString.substring(0, huffmanAsString.length()-1);
            System.out.println(huffmanAsString);

        }
        //saves String as dec_tab.txt
        try (PrintWriter out = new PrintWriter("dec_tab.txt")){
            out.print(huffmanAsString);
            System.out.println("table saved as dec_tab.txt");
        }

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
