package ch.fhnw.mada.hoschiho;

public class Node {
    Node left = null;
    Node right = null;
    String value;
    int frequency;


    public Node(int frequency, String value) {
        this.frequency = frequency;
        this.value = value;
    }

    public Node(Node left, Node right) {
        if (left.frequency > right.frequency) {
            this.left = right;
            this.right = left;
        } else {
            this.left = left;
            this.right = right;
        }

        this.frequency = left.frequency + right.frequency;
        this.value = left.value + right.value;
    }

    public int getFrequency() {
        return frequency;
    }
}

