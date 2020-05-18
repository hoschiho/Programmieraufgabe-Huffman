package ch.fhnw.mada.hoschiho;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        // get the textfile as string
        String fileAsString = readFromFile("exampletext.txt");

        int[] table = createTable(fileAsString);
        TreeMap<String, String> map = createCode(table);
        saveToFile(map);

        // create bitString (Aufgabe 5).
        StringBuilder bitString = new StringBuilder();

        for (char c : fileAsString.toCharArray()) {
            bitString.append(map.get(String.valueOf(Integer.valueOf(c)))); //encodes the text with the huffman table
        }
        // Appends a 1 and tailing 0 until it is divisible by 8 (Aufgabe 6.)
        bitString.append("1");
        while (bitString.length() % 8 != 0) {
            bitString.append("0");
        }

        // create byteArray (aufgabe 7.)
        byte[] byteArray = new byte[bitString.length() / 8];
        for (int i = 0; i < bitString.length() / 8; i++) {
            byteArray[i] = (byte) Integer.parseInt(bitString.substring(i * 8, (i + 1) * 8), 2); // parsing int base 2 -> binary number.
        }

        // Aufgabe 8.
        FileOutputStream fos = new FileOutputStream("output.dat");
        fos.write(byteArray);
        System.out.println("byteArray saved as output.dat");
        fos.close();

        ////DECOMPRESS////
        byte[] compressedByteArray = readByteArray("output-mada.dat");
        StringBuilder compressedBitString = new StringBuilder();

        for (byte b : compressedByteArray) {
            compressedBitString.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
        }

        compressedBitString = new StringBuilder(compressedBitString.substring(0, compressedBitString.lastIndexOf("1"))); //remove last 0 and 1

        String decTab = readFromFile("dec_tab-mada.txt");
        System.out.println(decTab);

        TreeMap<String, String> compressedTable = new TreeMap<>();
        String[] mappings = decTab.split("-");

        for (String mapping : mappings) { // Splits the saved huffman table and converts it back to a map.
            String[] mappingPair = mapping.split(":");
            String c = String.valueOf((char) Integer.parseInt(mappingPair[0])); // the character
            String v = mappingPair[1]; // the corresponding code
            compressedTable.put(v, c);
        }
        System.out.println("compressedTable: " + compressedTable);

        StringBuilder decoded = new StringBuilder();
        String codeChunk = "";

        for (char c : compressedBitString.toString().toCharArray()) {
            codeChunk += c;
            if (compressedTable.get(codeChunk) != null) { // Try to find a sequence of bits which match a key from the table
                decoded.append(compressedTable.get(codeChunk)); // Get the corresponding char
                codeChunk = "";
            }
        }

        System.out.println(decoded.toString());
        // saves String as result.txt
        try (PrintWriter out = new PrintWriter("result.txt")) {
            out.print(decoded.toString());
            System.out.println("result saved as result.txt");
        }
    }

    // Aufgabe 2 //
    private static int[] createTable(String fileAsString) {
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

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequency)); // sorted frequency table
        TreeMap<String, String> map = new TreeMap<>(); //table as treemap
        for (int i = 0; i < table.length; i++) {
            if (table[i] > 0) {
                queue.add(new Node(table[i], String.valueOf(i)));
            }
        }
        Node left;
        Node right;
        Node top;

        // huffman coding
        do {
            // takes the two lowest nodes
            left = queue.poll();
            right = queue.poll();
            top = new Node(left, right); //generates a parent node with left and right
            queue.add(top); // adds parent node
        } while (queue.size() > 1); //repeats until only one element is left in the queue

        getCodes(map, "", top); //use recursion to generate the huffman code

        return map;
    }

    // fills the huffman tree
    private static void getCodes(TreeMap<String, String> map, String code, Node top) {
        if (top.left == null && top.right == null) { //takes only chars and not a parent node
            map.put(top.value, code); //puts the code and the value in the map
        } else {
            getCodes(map, code + "0", top.left); //if we go left, we add a 0 to the code
            getCodes(map, code + "1", top.right); //if we go right, we add a 1 to the code

        }
    }

    // saves map to a textFile
    public static void saveToFile(TreeMap<String, String> map) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        // iterate trough map and save it as string
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append("-");
        }
        String huffmanAsString = sb.toString();

        // delete last delimeter
        if (huffmanAsString.charAt(huffmanAsString.length() - 1) == '-') {
            huffmanAsString = huffmanAsString.substring(0, huffmanAsString.length() - 1);
            System.out.println(huffmanAsString);

        }
        // saves String as dec_tab.txt
        try (PrintWriter out = new PrintWriter("dec_tab.txt")) {
            out.print(huffmanAsString);
            System.out.println("table saved as dec_tab.txt");
        }

    }

    // reads a file
    public static String readFromFile(String fileName) {
        try {
            byte[] encoded;
            encoded = Files.readAllBytes(Paths.get(fileName));
            return new String(encoded, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // reads a compressed file
    public static byte[] readByteArray(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bFile);
        fis.close();

        return bFile;
    }
}
