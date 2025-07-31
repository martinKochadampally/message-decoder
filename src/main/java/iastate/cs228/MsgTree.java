package iastate.cs228;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Represents a binary tree used for encoding and decoding messages.
 * Each node in the tree contains a payload character.
 * The tree is constructed from an encoding string and used to decode a message.
 * The class also provides methods for printing codes and decoding messages.
 *
 * @author martinKochadampally
 */
public class MsgTree {
    public char payloadChar;
    public MsgTree left;
    public MsgTree right;

    /**
     * Constructs an MsgTree from the given encoding string.
     * @param encodingString    The string containing the structure of the MsgTree.
     */
    public MsgTree(String encodingString) {
        if (encodingString != null && encodingString.length() > 1) {
            int index = 0;

            //continue fill left if left is null, then right if right is null, then back track.
            boolean isParent = false;
            MsgTree currNode = new MsgTree(encodingString.charAt(index));
            Stack<MsgTree> ancestors = new Stack<>();

            while (index < encodingString.length()) {
                if (!isParent) {
                    currNode.payloadChar = encodingString.charAt(index);
                }
                if (currNode.payloadChar == '^') {
                    isParent = false;
                    // If left child is null, create a new node and assign it to left child.
                    if (currNode.left == null) {
                        currNode.left = new MsgTree('^');
                        ancestors.push(currNode);
                        currNode = currNode.left;
                    }
                    // If right child is null, create a new node and assign it to right child.
                    else if (currNode.right == null) {
                        currNode.right = new MsgTree('^');
                        ancestors.push(currNode);
                        currNode = currNode.right;
                    }
                    // If both children are not null, back track to parent.
                    else if (!ancestors.isEmpty()) {
                        currNode = ancestors.pop();
                        isParent = true;
                    }
                    else break;

                    if (!isParent) {
                        index++;
                    }
                } else {
                    if (!ancestors.isEmpty()) {
                        currNode = ancestors.pop();
                    }
                    isParent = true;
                }
            }
            payloadChar = currNode.payloadChar;
            left = currNode.left;
            right = currNode.right;
        } else {
            throw new NullPointerException();
        }
    }

//    /**
//     * Recursively builds the tree.
//     */
//    public static MsgTree recursiveTreeBuilder() {
//        MsgTree tree = null;
//        if (staticCharIdx < encodingString.length()) {
//            char Value = encodingString.charAt(staticCharIdx++);
//            tree = new MsgTree(Value);
//            if (Value == '^') {
//                tree.left = recursiveTreeBuilder();
//                tree.right = recursiveTreeBuilder();
//            }
//        }
//        return tree;
//    }

    /**
     * Construct an MsgTree with no children.
     * @param payloadChar   the character held by the node.
     */
    public MsgTree(char payloadChar) {
        left = null;
        right = null;
        this.payloadChar = payloadChar;
    }

    /**
     * This method recursively prints out each character in the MsgTree with its code.
     * @param root  The root node of the MsgTree.
     * @param code  This string stores the code for each of the items.
     */
    public static void printCodes(MsgTree root, String code) {
        if (root == null) {
            return;
        }
        if (root != null) {
            printCodes(root.left, code + "0");
            printCodes(root.right, code + "1");

            // Print the payload character and its code if it is not a '^'.
            if (root.payloadChar == '\n') {
                System.out.println("    \\n" + "         " + code);
            } else if (root.payloadChar != '^') {
                System.out.println("    " + root.payloadChar + "          " + code);
            }
        }
    }

    /**
     * Decodes the given message using the provided MsgTree
     * @param codes MsgTree to be used to decode.
     * @param msg   String containing the coded message.
     */
    public void decode(MsgTree codes, String msg) {
        if (codes != null) {
            String Answer = "";
            int index = 0;
            // A loop that iterates through all the codes given
            for (int i = 0; i < msg.length() - 1; i++) {
                if (index < msg.length() - 1) {
                    MsgTree node = codes;
                    while (node != null) {
                        // If the nodes aren't null add one to S and add the char to the answer
                        if (node.payloadChar != '^') {
                            Answer += node.payloadChar;
                            break; // break while loop
                        } else {
                            char c = msg.charAt(index);
                            if (c == '0') {
                                node = node.left;
                            } else {
                                node = node.right;
                            }
                            index++;
                        }
                    }
                }
            }
            System.out.println(Answer);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Takes file input from user using a scanner
        // Please place files directly in the hw4 folder. Thanks!
        System.out.print("Please enter the filename to decode:  ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.next();
        File fileAddress = new File("../../../../tests/" + fileName);

        String treeString;
        String msgString;

        // Reads file
        try (Scanner fileScanner = new Scanner(fileAddress)) {
            String tempString;
            treeString = "";
            msgString = "";
            // The temporaryString is assigned to the next line in the file
            tempString = fileScanner.nextLine();
            // While loop that checks if there is a second line in the file
            while (fileScanner.hasNextLine()) {
                // For loop that adds the characters of the message and treeString
                for (int i = 0; i < tempString.length(); i++) {
                    msgString += tempString.charAt(i);
                    treeString += tempString.charAt(i);
                }
                // else go to the next line
                tempString = fileScanner.nextLine();
                
                // If the character is not a one or a zero then add a new line into the treeString
                if (tempString.charAt(0) != '1' && tempString.charAt(0) != '0') {
                    treeString += "\n";
                }
                // else assign the TemporaryString to the MessageString
                else msgString = tempString;
            }
            // Creates a new tree with the treeString
            MsgTree BST = new MsgTree(treeString);

            // Keeps track of the characters and the codes
            System.out.println(" Character     Code   ");
            System.out.println("----------------------");

            // Prints the codes of the characters of the tree
            String codes = "";
            printCodes(BST, codes);

            // Decodes the Codes
            System.out.println("\nMessage: ");
            BST.decode(BST, msgString);
        } 
        catch(FileNotFoundException e) {
            System.err.println("Error: The file '" + fileName + "' was not found."); 
            // e.printStackTrace();
        }
    }
}