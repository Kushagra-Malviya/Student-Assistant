import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JButton;
import java.awt.GridLayout;

public class APP_Code
{
    public static void main()
    {
        new login_page();
    }
}

class login_page extends JFrame implements ActionListener
{
    JTextField TBox_username;
    JPasswordField TBox_password;
    JButton button1,button2;
    JLabel password,username;
    JFrame frame;
    login_page(){
        JPanel panel = new JPanel();
        panel.setLayout(null);

        frame = new JFrame();
        frame.setTitle("LOGIN PAGE");
        frame.setLocation(new Point(500, 300));
        frame.add(panel);
        frame.setSize(new Dimension(400, 230));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        username = new JLabel("Username");
        username.setBounds(100, 8, 70, 20);
        panel.add(username);

        TBox_username=new JTextField();
        TBox_username.setBounds(100, 28, 193, 28);
        panel.add(TBox_username);

        password = new JLabel("Password");
        password.setBounds(100, 55, 70, 20);
        panel.add(password);

        TBox_password=new JPasswordField();
        TBox_password.setBounds(100, 75, 193, 28);
        panel.add(TBox_password);

        button1=new JButton("Login");
        button1.setBounds(100, 110, 193, 25);
        button1.setForeground(Color.WHITE);
        button1.setBackground(Color.BLACK);
        button1.addActionListener(this);
        panel.add(button1);

        button2=new JButton("Sign up");
        button2.setBounds(100, 140, 193, 25);
        button2.setForeground(Color.WHITE);
        button2.setBackground(Color.BLACK);
        button2.addActionListener(this);
        panel.add(button2);

        frame.add(panel);
        frame.setVisible(true);
    }

    void login(String u, String p)
    {
        int f=0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            Statement st = con.createStatement();
            String q="SELECT * FROM User;";
            ResultSet rs= st.executeQuery(q);
            while(rs.next()){
                if(rs.getString("U_Name").equals(u) && rs.getString("Pswd").equals(p)){
                    new MainScr(rs.getInt("UID"));
                    frame.setVisible(false);
                    f=1;
                    break;
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        if(f==0){
            JOptionPane.showMessageDialog(frame ,"User Not Found", "Invalid User" , JOptionPane.WARNING_MESSAGE);
        }
    }

    void signin(String u, String p)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            Statement st = con.createStatement();
            String q="SELECT * FROM User;";
            ResultSet rs= st.executeQuery(q);
            int f=0,UID=1;
            while(rs.next()){
                if(rs.getString("U_Name").equals(u)){
                    JOptionPane.showMessageDialog(frame ,"User Already Exists" ,"Invalid User" , JOptionPane.WARNING_MESSAGE);
                    f=1;
                    break;
                }
                UID=rs.getInt("UID")+1;
            }
            if(f==0){
                PreparedStatement stm=con.prepareStatement("INSERT INTO User VALUES(?,?,?)");
                stm.setInt(1,UID); 
                stm.setString(2,u);
                stm.setString(3,p);
                stm.executeUpdate();
                JOptionPane.showMessageDialog(frame ,"User Created Successfully" ,"Success" , JOptionPane.PLAIN_MESSAGE);
                frame.setVisible(false);
                new MainScr(UID);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        String Username = TBox_username.getText();
        String Password = TBox_password.getText();
        if(e.getSource()==button1)
        {
            login(Username,Password);
        }
        if(e.getSource()==button2)
        {
            if(Username.isEmpty() || Password.isEmpty()){
                JOptionPane.showMessageDialog(frame ,"Username or Password Cannot be Empty" ,"Invalid Value" , JOptionPane.WARNING_MESSAGE);
            }
            else
                signin(Username,Password);
        }
    }
}

class MainScr extends JFrame implements ActionListener
{
    static class ImagePanel extends JPanel {
        private Image image;
        ImagePanel(Image image) {
            this.image = image;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        }
    }
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    int UID;
    MainScr(int U){
        UID=U;
        JLabel label=new JLabel();
        label.setText("<html>Welcome To your Very own Student Assistant<br>Choose From the Following:</html>");
        label.setFont(new Font("Times New Roman",Font.BOLD,20));
        label.setForeground(new Color(255, 195, 0));
        label.setHorizontalAlignment(JLabel.CENTER);

        Image image = Toolkit.getDefaultToolkit().getImage("background2.gif");

        ImagePanel imagePanel = new ImagePanel(image);

        imagePanel.add(label);
        imagePanel.setBounds(0,0,500,75);

        JPanel panel2=new JPanel();
        panel2.setBounds(0,75,500,125);
        panel2.setLayout(new GridLayout(1,4));
        panel2.setBackground(Color.darkGray);

        ImageIcon i=new ImageIcon("Assistant.png");

        JFrame frame=new JFrame("Student Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBackground(Color.black);
        frame.setSize(515,235);
        frame.setIconImage(i.getImage());
        frame.setLocation(new Point(500, 300));

        b1 = new JButton("Time Table");
        b1.addActionListener(this);
        b1.setFocusable(false);
        b1.setHorizontalTextPosition(JButton.CENTER);
        b1.setVerticalTextPosition(JButton.CENTER);
        b1.setBorder(BorderFactory.createEtchedBorder(1, Color.RED, Color.PINK));
        b1.setFont(new Font("Comic Sans",Font.BOLD,18));
        b1.setBackground(new Color(183, 110, 121));
        b1.setForeground(new Color(255, 195, 0));
        b2 = new JButton("Event Planner");
        b2.addActionListener(this);
        b2.setFocusable(false);
        b2.setHorizontalTextPosition(JButton.CENTER);
        b2.setVerticalTextPosition(JButton.CENTER);
        b2.setBorder(BorderFactory.createEtchedBorder(1, Color.RED, Color.PINK));
        b2.setFont(new Font("Comic Sans",Font.BOLD,15));
        b2.setBackground(new Color(183, 110, 121));
        b2.setForeground(new Color(255, 195, 0));
        b3 = new JButton("Notes");
        b3.addActionListener(this);
        b3.setFocusable(false);
        b3.setHorizontalTextPosition(JButton.CENTER);
        b3.setVerticalTextPosition(JButton.CENTER);
        b3.setBorder(BorderFactory.createEtchedBorder(1, Color.RED, Color.PINK));
        b3.setFont(new Font("Comic Sans",Font.BOLD,18));
        b3.setBackground(new Color(183, 110, 121));
        b3.setForeground(new Color(255, 195, 0));
        b4 = new JButton("Money Manage");
        b4.addActionListener(this);
        b4.setFocusable(false);
        b4.setHorizontalTextPosition(JButton.CENTER);
        b4.setVerticalTextPosition(JButton.CENTER);
        b4.setBorder(BorderFactory.createEtchedBorder(1, Color.RED, Color.PINK));
        b4.setFont(new Font("Comic Sans",Font.BOLD,15));
        b4.setBackground(new Color(183, 110, 121));
        b4.setForeground(new Color(255, 195, 0));

        panel2.add(b1);
        panel2.add(b2);
        panel2.add(b3);
        panel2.add(b4);

        frame.add(imagePanel);
        frame.add(panel2);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1) {
            new TimeTable(UID);
        }
        if(e.getSource()==b2) {
            new EventPlanner(UID);
        }  
        if(e.getSource()==b3) {
            new Notes(UID);
        }  
        if(e.getSource()==b4) {
            new Money_Tracker(UID);
        }  
    }
}

class TimeTable extends JFrame implements ActionListener
{
    int UID;
    JTextField txtBox[][];
    JPanel p1,p2;
    JButton button;
    TimeTable(int U)
    {
        UID=U;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(700,550);
        this.setResizable(false);
        this.setTitle("Time Table");
        ImageIcon img=new ImageIcon("schedule.png");
        this.setIconImage(img.getImage());

        p1=new JPanel();
        p1.setBounds(0,0,700,500);
        p1.setLayout(new GridLayout(5,11));
        p1.setBackground(Color.darkGray);

        p2=new JPanel();
        p2.setBounds(700,0,700,50);
        p2.setLayout(new FlowLayout());
        p2.setBackground(Color.gray);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            PreparedStatement stm=con.prepareStatement("SELECT * FROM time_table WHERE UID=(?);");
            stm.setInt(1,UID);
            ResultSet rs= stm.executeQuery();
            txtBox=new JTextField[5][11];
            if(!rs.next()){
                for(int i=0;i<5;i++){
                    for(int j=0;j<11;j++){
                        if(j==0){
                            txtBox[i][j] = new JTextField("Day "+(i+1));
                            txtBox[i][j].setBackground(Color.black);
                            txtBox[i][j].setForeground(Color.white);
                            txtBox[i][j].setEditable(false);
                        }
                        else
                            txtBox[i][j] = new JTextField("FREE");
                        p1.add(txtBox[i][j]);
                    }
                }
                button=new JButton("Submit");
                button.addActionListener(this);
                p2.add(button);
                this.add(p1,BorderLayout.CENTER);
                this.add(p2,BorderLayout.SOUTH);
            }
            else{
                for(int i = 0 ; i < 5; i++)
                {
                    for(int j=0;j<11;j++)
                    {
                        txtBox[i][j] = new JTextField(rs.getString(j+2));
                        txtBox[i][j].setEditable(false);
                        p1.add(txtBox[i][j]);
                        if(j==0){
                            txtBox[i][j].setForeground(Color.white);
                            txtBox[i][j].setBackground(Color.black);
                        }
                        else if(rs.getString(j+2).equals("FREE")){
                            txtBox[i][j].setBackground(Color.pink);
                        }
                        else{
                            txtBox[i][j].setBackground(Color.yellow);
                        }
                    }
                    rs.next();
                }
                this.add(p1);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }

        this.setVisible(true);
    }

    public void enterDatabase()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            for(int i=0;i<5;i++){
                PreparedStatement stm=con.prepareStatement("INSERT INTO Time_Table VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
                stm.setInt(1,UID);
                for(int j=0;j<11;j++){
                    stm.setString(j+2,txtBox[i][j].getText());
                }
                stm.executeUpdate();
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button) {
            enterDatabase();
            this.setVisible(false);
        }
    }
}

class EventPlanner extends JFrame implements ActionListener
{
    JButton b1,b2;
    JTextField textField1,textField2;
    String date,name;
    int UID;
    EventPlanner(int U){
        UID=U;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setTitle("Event Planner");
        this.setResizable(false);
        ImageIcon img=new ImageIcon("calendar.png");
        this.setIconImage(img.getImage());

        b1 = new JButton("Submit");
        b1.addActionListener(this);
        b1.setFocusable(false);
        b1.setBackground(new Color(183, 110, 121));
        b1.setForeground(new Color(255, 195, 0));

        b2 = new JButton("Display Events");
        b2.addActionListener(this);
        b2.setFocusable(false);
        b2.setBackground(new Color(183, 110, 121));
        b2.setForeground(new Color(255, 195, 0));

        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(250,40));
        textField1.setText("Event Date(YYYY-MM-DD):");

        textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(250,40));
        textField2.setText("Event Name:");

        this.add(b1);
        this.add(b2);
        this.add(textField1);
        this.add(textField2);
        this.getContentPane().setBackground(Color.darkGray);
        this.pack();
        this.setVisible(true);
    }

    public void enterDatabase()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            PreparedStatement stm=con.prepareStatement("INSERT INTO Event_Planner VALUES(?,?,?)");
            stm.setInt(1,UID); 
            stm.setString(2,date);
            stm.setString(3,name);
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void showDatabase()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            PreparedStatement stm=con.prepareStatement("SELECT * FROM event_planner WHERE UID=(?);");
            stm.setInt(1,UID);
            ResultSet rs= stm.executeQuery();
            String s="";
            while (rs.next()) {
                s+=rs.getString("E_Date")+": "+rs.getString("E_Name")+"\n";
            }
            JOptionPane.showMessageDialog(null,s, "Events:", JOptionPane.PLAIN_MESSAGE);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1) {
            date=textField1.getText();
            name=textField2.getText();
            enterDatabase();
        }

        if(e.getSource()==b2) {
            showDatabase();
        }
    }
}

class Notes extends JFrame implements ActionListener
{
    JButton button;
    JTextField textField1,textField2;
    JPanel panel1,panel2;
    String note,note_title;
    Connection con;
    Statement st;
    int j=0;
    JButton[] b;
    String[] ref;
    int UID;
    Notes(int U)
    {
        UID=U;
        panel1=new JPanel();
        panel1.setBounds(0,0,400,500);
        panel1.setLayout(new FlowLayout());
        panel1.setBackground(Color.darkGray);

        panel2=new JPanel();
        panel2.setBounds(400,0,200,500);
        panel2.setLayout(new FlowLayout());
        panel2.setBackground(Color.gray);

        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(390,40));
        textField1.setText("Note Title:");

        textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(390,350));
        textField2.setText("Enter Note:");

        button = new JButton("Submit");
        button.addActionListener(this);
        button.setFocusable(false);

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            st = con.createStatement();
            PreparedStatement stm=con.prepareStatement("SELECT * FROM notes WHERE UID=(?);");
            stm.setInt(1,UID);
            ResultSet rs= stm.executeQuery();
            b = new JButton[100];
            ref=new String[100];
            while (rs.next()) {
                b[j]=new JButton(rs.getString("N_Title"));
                ref[j]=rs.getString("N_Title");
                b[j].addActionListener(this);
                panel2.add(b[j]);
                j++;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        panel1.add(textField1);
        panel1.add(textField2);
        panel1.add(button);

        this.add(panel1);
        this.add(panel2);

        ImageIcon img=new ImageIcon("notes.png");
        this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Notes");
        this.setSize(600,500);
        this.setVisible(true);
    }

    public void displayNote(String Title)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            PreparedStatement stm=con.prepareStatement("SELECT * FROM Notes WHERE N_Title=(?) AND UID=(?)");
            stm.setString(1,Title);
            stm.setInt(2,UID);
            ResultSet rs= stm.executeQuery();
            String s="";
            while (rs.next()) {
                s=rs.getString("Note");
            }
            JOptionPane.showMessageDialog(null,s,Title , JOptionPane.PLAIN_MESSAGE);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void enterDatabase()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            Statement st = con.createStatement();
            PreparedStatement stm=con.prepareStatement("INSERT INTO Notes VALUES(?,?,?)");
            stm.setInt(1,UID);
            stm.setString(2,note_title);
            stm.setString(3,note);
            stm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button) {
            note_title=textField1.getText();
            note=textField2.getText();
            enterDatabase();
        }
        for(int i=0;i<j;i++){
            if(e.getSource()==b[i]){
                displayNote(ref[i]);
            }
        }
    }
}

class Money_Tracker extends JFrame implements ActionListener
{
    JButton b1,b2,bd;
    JTextField textField1,textField2;
    String item;
    JLabel label;
    JPanel panel1,panel2;
    int UID,value,total=0;
    Money_Tracker(int U){
        UID=U;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle("Money Tracker");
        this.setResizable(false);
        ImageIcon img=new ImageIcon("growth.png");
        this.setIconImage(img.getImage());

        panel1=new JPanel();
        panel1.setBounds(0,0,400,200);
        panel1.setLayout(new FlowLayout());
        panel1.setBackground(Color.darkGray);

        panel2=new JPanel();
        panel2.setBounds(0,200,400,200);
        panel2.setLayout(new FlowLayout());
        panel2.setBackground(Color.darkGray);

        b1 = new JButton("Add Amount");
        b1.addActionListener(this);
        b1.setFocusable(false);
        b1.setBackground(new Color(183, 110, 121));
        b1.setForeground(new Color(255, 195, 0));

        b2 = new JButton("Subtract Amount");
        b2.addActionListener(this);
        b2.setFocusable(false);
        b2.setBackground(new Color(183, 110, 121));
        b2.setForeground(new Color(255, 195, 0));

        bd = new JButton("History");
        bd.addActionListener(this);
        bd.setFocusable(false);
        bd.setBackground(new Color(183, 110, 121));
        bd.setForeground(new Color(255, 195, 0));

        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(250,40));
        textField1.setText("Item:");

        textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(250,40));
        textField2.setText("0");

        label=new JLabel();
        label.setForeground(Color.pink);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            PreparedStatement stm=con.prepareStatement("SELECT * FROM Money WHERE UID=(?);");
            stm.setInt(1,UID);
            ResultSet rs= stm.executeQuery();
            String s="";
            while (rs.next()) {
                total+=rs.getInt("Value");
            }
            label.setText("Total: "+total);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }

        panel1.add(textField1);
        panel1.add(textField2);
        panel1.add(b1);
        panel1.add(b2);
        panel2.add(label);
        panel2.add(bd);

        this.add(panel1,BorderLayout.CENTER);
        this.add(panel2,BorderLayout.SOUTH);
        this.getContentPane().setBackground(Color.gray);
        this.pack();
        this.setVisible(true);
    }

    public void enterDatabase()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            PreparedStatement stm=con.prepareStatement("INSERT INTO Money VALUES(?,?,?)");
            stm.setInt(1,UID); 
            stm.setString(2,item);
            stm.setInt(3,value);
            stm.executeUpdate();
            total+=value;
            label.setText("Total: "+total);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void showDatabase()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Assistant", "root", "06july2004");
            PreparedStatement stm=con.prepareStatement("SELECT * FROM Money WHERE UID=(?);");
            stm.setInt(1,UID);
            ResultSet rs= stm.executeQuery();
            String s="";
            while (rs.next()) {
                s+=rs.getString("Item")+": "+rs.getInt("Value")+"\n";
            }
            JOptionPane.showMessageDialog(null,s, "History:", JOptionPane.PLAIN_MESSAGE);
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1) {
            item=textField1.getText();
            value=Integer.parseInt(textField2.getText());
            enterDatabase();
        }

        if(e.getSource()==b2) {
            item=textField1.getText();
            value=0-(Integer.parseInt(textField2.getText()));
            enterDatabase();
        }

        if(e.getSource()==bd) {
            showDatabase();
        }

    }
}