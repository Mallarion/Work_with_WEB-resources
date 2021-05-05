package com.first_direction.blog;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;



public class Win_Lab extends Frame implements ActionListener{
    File elem=null;

    public File getElem() {
        return elem;
    }

    public void setElem(File elem) {
        this.elem = elem;
    }

    Button sea=new Button("Search");
    Button open =new Button("Open");
    Button start =new Button("Start");
    TextArea txa = new TextArea("You can write any words, and yuo will see how many words, which you write are in texts.\n Press <Start> to continue");
    public  Win_Lab()
    {
        super("my window");
        addWindowListener(new WindowAdapter(){
                              public void windowClosing(WindowEvent e) {
                                  dispose();
                              }});

                setLayout(null);
        setBackground(new Color(150,200,100));
        setSize(600,250);

        add(sea);
        add(txa);
        add(open);
        add(start);
        start.setBounds(250,215,100,20);
        start.addActionListener(this);
        open.setBounds(250,190,100,20);
        open.addActionListener(this);
        sea.setBounds(250,165,100,20);
        sea.addActionListener(this);
        txa.setBounds(20,50,550,100);

        this.show();
        this.setLocationRelativeTo(null);


    }

    public void actionPerformed(ActionEvent ae)
    {

        {
            String [] keywords=txa.getText().split(",");
            for (int j=0;j<keywords.length;j++)
            {
                System.out.println(keywords[j]);
            }
            // Здесь нужно указать путь до файлов SearchFile1 и SearchFile2, по котрым будет происходить поиск
            File f = new File("D:/Мои документы/Коля/IntelliJ IDEA Community Edition 2020.2/blog/src/main/java/com/first_direction/blog/source_html");
            ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
            txa.setText("");
            int k=0;

            for (File elem : files)
            {
                int zcoincidence = test_url(elem,keywords);
                txa.append("\n"+elem+"  :"+zcoincidence);
                if(k==0){
                    k=zcoincidence;
                setElem(elem);
                }
                else
                    if(zcoincidence>k){
                        k=zcoincidence;
                        setElem(elem);
                    }
                    else
                        if(zcoincidence==k)
                            setElem(elem);
            }}
            if(ae.getSource()==start){
                txa.setText(null);
            }
     if (ae.getSource()==open){
        if (elem!=null){
            openFile(getElem());
        }
    }
    }

    public static int test_url(File elem, String [] keywords)
    {
        int res=0;
        URL url = null;
        URLConnection con = null;
        int i;
        try
        {
            String ffele=""+elem;
            url = new URL("file:/"+ffele.trim());
            con = url.openConnection();
            // Здесь нужно указать путь до файла, в который будет сохраняться результат поиска
            File file = new File("D:/Мои документы/Коля/rezult.html");
            BufferedInputStream bis = new BufferedInputStream(
                    con.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            String bhtml=""; //file content in byte array
            while ((i = bis.read()) != -1) {
                bos.write(i);
                bhtml+=(char)i;
            }
            bos.flush();
            bis.close();
            String htmlcontent=
                    (new String(bhtml)).toLowerCase(); //file content in string
            System.out.println("New url content is: "+htmlcontent);
            for (int j=0;j<keywords.length;j++)
            {
                if(htmlcontent.indexOf(keywords[j].trim().toLowerCase())>=0)
                    res++;
            }}

        catch (MalformedInputException malformedInputException)
        {
            System.out.println("error "+malformedInputException.getMessage());
            return -1;
        }
        catch (IOException ioException)
        {
            System.out.println("error "+ioException.getMessage());
            return -1;
        }
        catch(Exception e)
        {
            System.out.println("error "+e.getMessage());
            return -1;
        }
        return res;
    }

    public void openFile(File elem){
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            assert desktop != null;
            desktop.open(new File(String.valueOf(elem)));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Win_Lab();
    }
}
