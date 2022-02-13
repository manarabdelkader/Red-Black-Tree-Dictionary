package RedBlackTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

enum Color{BLACK,RED}

class Node{
 Color color;
 String data;
 Node left;
 Node right;
 Node parent;
}

public class RBT {
    public Node root;
    public Node getRoot(){ return this.root;}
    public static Node NILL;

    public RBT() {
        NILL = new Node();
        NILL.color = Color.RED;
        NILL.left = null;
        NILL.right = null;
        root = NILL;
    }
    public static int size(Node root) {

        return (root == NILL ? 0 : 1 + size(root.left) + size(root.right));
    }

    public static int height(Node root) {
        if (root == NILL)
            return -1;
        else {
            int left = height(root.left);
            int right = height(root.right);
            if (left >= right)
                return (left + 1);
            else
                return (right + 1);
        }
    }

    public int search(Node root, String word) {
        if (root == NILL) {
            return 0;
        } else if (root.data.compareToIgnoreCase(word) == 0) {
            return 1;
        } else if (root.data.compareToIgnoreCase(word) > 0) {
            return search(root.left, word);
        } else {
            return search(root.right, word);
        }
    }

    public void RotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NILL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void RotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != NILL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    private void balanceRBT(Node node){
        Node uncle;
        while (node.parent.color == Color.RED) {
            if (node.parent == node.parent.parent.right) {
                uncle = node.parent.parent.left;
                if (uncle.color == Color.RED) {
                    uncle.color = Color.BLACK;
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color =Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        RotateRight(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    RotateLeft(node.parent.parent);
                }
            } else {
                uncle = node.parent.parent.right;
                if (uncle.color == Color.RED) {
                    uncle.color = Color.BLACK;
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        RotateLeft(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    RotateRight(node.parent.parent);
                }
            }
            if (node == root) {
                break;
            }
        }
        root.color = Color.BLACK;
    }

    public Node NewNode(String data){
        Node node = new Node();
        node.parent = null;
        node.data = data;
        node.left = NILL;
        node.right = NILL;
        node.color = Color.RED;
        return node;
    }

    public void insert(String word){
        Node node = NewNode(word);
        Node y = null;
        Node x = root;
        while (x != NILL) {
            y = x;
            if (node.data.compareToIgnoreCase(x.data) <0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data.compareToIgnoreCase(y.data)< 0) {
            y.left = node;
        } else {
            y.right = node;
        }
        if (node.parent == null){
            node.color = Color.BLACK;
            return;
        }
        if (node.parent.parent == null) {
            return;
        }
        balanceRBT(node);
    }



    public static void main(String[] args) {
        RBT rbt = new RBT();
        try
        {
            File file=new File("EN-US-Dictionary.txt");
            BufferedReader br=new BufferedReader(new FileReader(file));
            String line;
            while((line=br.readLine())!=null)
            {
                rbt.insert(line);
            }
            System.out.println("File 'EN-US-Dictionary.txt' is loaded successfully...\n");
        }
        catch(IOException e)
        {
            System.out.println("File NOT Loaded!\n");
            e.printStackTrace();
            return;
        }
        Scanner inputOperation = new Scanner( System.in );
        Scanner inputSearch = new Scanner( System.in );
        Scanner inputInsert = new Scanner( System.in );
        int x=1;
        while(x==1){
            System.out.println("\nCHOOSE OPERATION:");
            System.out.println("1.Tree Height \n2.Tree Size\n3.Search Word\n4.Insert Word\n5.Exit");
            int operation = inputOperation.nextInt();
            switch (operation){
                case 1:
                System.out.println("Tree Height = "+ height(rbt.getRoot()));
                break;
                case 2:
                    System.out.println("Tree Size =  "+ size(rbt.getRoot()));
                    break;
                 case 3:
                     System.out.println("Enter the word: ");
                     String searchWord = inputSearch.next();
                     if(rbt.search(rbt.getRoot(),searchWord)==1) {
                         System.out.println("YES " + searchWord + " is found");
                     }
                     else {  System.out.println("NO " + searchWord + " is not found");
                     }
                     break;
                case 4:
                    System.out.println("Enter the word you want to insert: ");
                    String insertWord = inputInsert.next();
                    if(rbt.search(rbt.getRoot(),insertWord)==1){
                        System.out.println("ERROR: Word already in the dictionary!");
                    }
                    else{
                        rbt.insert(insertWord);
                        System.out.println("Tree Height after insertion= "+ height(rbt.getRoot()));
                        System.out.println("Tree Size after insertion=  "+ size(rbt.getRoot()));
                    }
                    break;
                case 5:
                    x=0;
                    break;
            }

        }

    }
}