package com.company;

public class Node {
    Node left = null;
    Node right = null;
    String value;
    int frequency;


    public Node(String value, int frequency){
        this.value = value;
        this.frequency = frequency;

    }

    public Node(Node left, Node right){
        if(left.frequency > right.frequency){
            this.left = right;
            this.right = left;
        }
        else{
            this.left = left;
            this.right = right;
        }

        this.value = left.value + right.value;
        this.frequency = left.frequency + right.frequency;
    }
    @Override
    public String toString(){
        return this.frequency + ": " + this.value;
    }

}

