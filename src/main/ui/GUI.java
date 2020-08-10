package ui;

import model.Task;
import model.ToDoList;
import model.persistence.Reader;
import model.persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI {
    private JFrame frame;
    private ToDoList toDoList;
    private Map<String, JPanel> panels;
    private JPanel cards;
    private CardLayout cl;
    private JLabel allTasks = new JLabel();
    private JLabel label1 = new JLabel();
    private JLabel label2 = new JLabel();
    private JTextField tf1;
    private JTextField tf2;
    private Sound sound = new Sound();


    public GUI() {
        initializeFields();
        createMenuBar();
        frame.setVisible(true);
    }


    private void initializeFields() {
        //Initialize the To-Do-List
        toDoList = new ToDoList();
        //Initialize JFrame
        frame = new JFrame("To-Do-List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,400);
        cards = new JPanel();
        cards.setLayout(new CardLayout());
        setPanels();
        frame.add(cards);
        cl = (CardLayout)(cards.getLayout());
        setCard1();
        setCard2();
        setCard3();
    }

    private void createMenuBar() {
        // creating menuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        mb.add(m1);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        JMenuItem m33 = new JMenuItem("add task");
        JMenuItem m44 = new JMenuItem("All tasks");
        m1.add(m11);
        m1.add(m22);
        m1.add(m33);
        m1.add(m44);
        m11.addActionListener(e -> processOpen());
        m22.addActionListener(e -> processSave());
        m33.addActionListener(e -> processAdd());
        m44.addActionListener(e -> processShow());
        frame.getContentPane().add(BorderLayout.NORTH, mb);
    }

    private void setPanels() {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panels = new HashMap<>();
        panels.put("card1",panel1);
        panels.put("card2",panel2);
        panels.put("card3",panel3);
        panels.put("card4",panel4);
        cards.add(panel1,"card1");
        cards.add(panel2,"card2");
        cards.add(panel3,"card3");
        cards.add(panel4,"card4");
    }

    private void processAdd() {
        String description = tf1.getText();
        int time = Integer.parseInt(tf2.getText());
        Task task = new Task(description,time);
        toDoList.addTask(task);
        allTasks.setText(toDoList.getAllTasks());
        panels.get("card1").add(allTasks);
        sound.playSound("./data/add.wav");
    }

    private void setCard1() {
        JLabel label1 = new JLabel("Description:");
        tf1 = new JTextField(10); // accepts upto 10 characters
        tf1.setBounds(10,10,10,10);
        JLabel label2 = new JLabel("Estimated time: ");
        tf2 = new JTextField(10);
        tf2.setBounds(10,10,10,10);
        JButton button = new JButton("Add task");
        JButton button1 = new JButton("All tasks");

        BoxLayout boxlayout = new BoxLayout(panels.get("card1"),BoxLayout.Y_AXIS);
        panels.get("card1").setLayout(boxlayout);

        panels.get("card1").add(label1);
        panels.get("card1").add(tf1);
        panels.get("card1").add(label2);
        panels.get("card1").add(tf2);
        panels.get("card1").add(button);
        panels.get("card1").add(button1);
        button.addActionListener(e -> processAdd());
        button1.addActionListener(e -> processShow());
    }

    private void processOpen() {
        sound.playSound("./data/menuItem.wav");
        cl.show(cards,"card2");
    }

    private void setCard2() {
        panels.get("card2").setBounds(35,25,100,50);
        JLabel label = new JLabel("Enter the file you want to use: ");
        JTextField tf = new JTextField(10);
        tf.setBounds(10,10,50,10);
        JButton button1 = new JButton("choose");
        JButton button2 = new JButton("cancel");
        panels.get("card2").add(label);
        panels.get("card2").add(tf);
        panels.get("card2").add(button1);
        panels.get("card2").add(button2);
        button1.addActionListener(e -> {
            sound.playSound("./data/button.wav");
            readTasks(tf.getText());
        });
        button2.addActionListener(e -> goBackToCard1());
    }

    private void readTasks(String name) {
        panels.get("card2").add(label1);
        String fileName = "./data/" + name + ".txt";
        try {
            List<Task> tasks = Reader.readTasks(new File(fileName));
            for (Task task : tasks) {
                toDoList.addTask(task);
            }
            sound.playSound("./data/button.wav");
            label1.setText("Successfully read the tasks");
        } catch (IOException e) {
            sound.playSound("./data/error.wav");
            label1.setText("Unable to read tasks from" + fileName);
        }

    }

    private void processSave() {
        sound.playSound("./data/menuItem.wav");
        cl.show(cards,"card3");
    }

    private void setCard3() {
        panels.get("card3").setBounds(35,25,100,50);
        JLabel label = new JLabel("Enter the file you want to save");
        JTextField tf1 = new JTextField(10);
        tf1.setBounds(10,10,10,10);
        JButton button1 = new JButton("save");
        JButton button2 = new JButton("cancel");
        panels.get("card3").add(label);
        panels.get("card3").add(tf1);
        panels.get("card3").add(button1);
        panels.get("card3").add(button2);
        button1.addActionListener(e -> {
            sound.playSound("./data/button.wav");
            saveTasks(tf1.getText());
        });
        button2.addActionListener(e -> goBackToCard1());
    }

    private void saveTasks(String name) {
        String fileName = "./data/" + name + ".txt";
        panels.get("card3").add(label2);
        try {
            Writer writer = new Writer(new File(fileName));
            for (Task task:toDoList.getTasks()) {
                writer.write(task);
            }
            writer.close();
            label2.setText("Successfully save the tasks");
        } catch (FileNotFoundException e) {
            label2.setText("Unable to save tasks to" + fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void processShow() {
        sound.playSound("./data/menuItem.wav");
        renewTasks();
        cl.show(cards,"card4");
    }

    private void setCard4() {
        JLabel info = new JLabel("There are all of your tasks");
        panels.get("card4").add(info);

        allTasks.setText(toDoList.getAllTasks());
        panels.get("card4").add(allTasks);

        JButton button = new JButton("jump to add");
        panels.get("card4").add(button);
        button.addActionListener(e -> goBackToCard1());
    }

    private void renewTasks() {
        panels.get("card4").removeAll();
        setCard4();
    }

    private void goBackToCard1() {
        sound.playSound("./data/button.wav");
        cl.show(cards,"card1");
    }

}
