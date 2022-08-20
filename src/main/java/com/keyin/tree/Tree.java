package com.keyin.tree;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import com.google.gson.Gson;


public class Tree<I extends Number> {
    public short size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public void clear() {
    }

    public Object get(I i) {
        return null;
    }

    public I inOrder() {
        return null;
    }


    static class Node {
        int value;
        Node left;
        Node right;
        int height;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
            height = 1;
        }

        public int getValue() {
             return value;
         }

        public void setValue(int value) {
             this.value = value;
         }

        public int getHeight() {
            return height;
        }
        public static int getHeight(Node root) {
            if (root == null) {
                return 0;
            }
            return Math.max(getHeight(root.left), getHeight(root.right)) + 1;

        }
        private int balanceHeight (Node currentNode)
        {
            if (currentNode == null)
            {
                return 0;
            }

            // checking left subtree
            int leftSubtreeHeight = balanceHeight (currentNode.left);
            if (leftSubtreeHeight == -1) return -1;
            // if left subtree is not balanced then the entire tree is also not balanced

            //checking right subtree
            int rightSubtreeHeight = balanceHeight (currentNode.right);
            if (rightSubtreeHeight == -1) return -1;
            // if right subtree is not balanced then the entire tree is also not balanced

            //checking the difference of left and right subtree for current node
            if (Math.abs(leftSubtreeHeight - rightSubtreeHeight) > 1)
            {
                return -1;
            }
            //if it is balanced then return the height
            return (Math.max(leftSubtreeHeight, rightSubtreeHeight) + 1);
        }

        public void print() {
            print("", this, false);
        }

        private void print(String prefix, Node n, boolean isLeft) {
            if (n != null) {
                System.out.println(prefix + (isLeft ? "left--" : "right-- ") + n.value);
                print(prefix + (isLeft ? "left-- " : "    "), n.left, true);
                print(prefix + (isLeft ? "left--  " : "    "), n.right, false);
            }
        }


    }
    static Node root;

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            // value already exists
            return current;
        }

        return current;
    }

    public void add(int value, String apple) {
        root = addRecursive(root, value);
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    //adding in the write to database mysql
    public static void post(String inputs,String tree_string) throws Exception{
        final String inputsdb = (inputs);
        final String treeoutputs = (tree_string);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println("Converted String: " + strDate);
        try {
            Connection con = getConnection();
            PreparedStatement posted = con.prepareStatement("INSERT INTO tree (strDate, inputs, treeoutputs) VALUES ('"+strDate+"','"+inputsdb+"','"+treeoutputs+"')");
            posted.executeUpdate();
        } catch(Exception e) {System.out.println(e);    }
        finally {
            System.out.println("Insert into DB Completed!");
        }
    }
    public static void createTable() throws Exception {
        try {
            Connection con = getConnection();
            PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS tree(id int NOT NULL AUTO_INCREMENT, strDate varchar(255),inputs varchar(255), treeoutputs varchar(255), PRIMARY KEY(id))");
            create.executeUpdate();
        }catch(Exception e){System.out.println(e);}
        finally {
            System.out.println("Database is Ready!");
        };
    }
    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/tree";
            String username = "root";
            String password = "GBBProject_71";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("connected to MYSQL DB");
            return conn;
        } catch(Exception e) {System.out.println(e);}
        return null;
    }

    public static class Main {

        public static void main(String[] args) throws Exception {
            // Array to write to MSQL database
            String inputs = "87 21 7 10 42 27 17";
            Gson gson = new Gson();
            Tree<Number> tr = new Tree<Number>();
            tr.add(77, "apple");
            tr.add(20, "apple");
            tr.add(47, "apple");
            tr.add(12, "apple");
            tr.add(32, "apple");
            tr.add(18, "apple");
            tr.add(12, "apple");

            createTable();
            String tree_string = gson.toJson(tr);
            post(inputs, tree_string);

            System.out.println("Print array of nodes in order->");

            tr.traverseInOrder(tr.root );
            System.out.println("\n\nRoot :");
            System.out.println("\n\n Tree balanced Structure" + "\n\nRoot :" );
            tr.root.print();

//            System.out.println(root.height);
            if(root.height == 1){
                System.out.println("\n\nTree is Balanced Bf = " + root.height);
            }else{
                System.out.println( "\n\nTree is not Balanced Bf = " + root.height);
            }
        }


    }

}
