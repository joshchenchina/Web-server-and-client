import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
* This class helps find embeded files and read through contents
* 
* @author  Darren Norton, Yizhong Chen
* @since   Feb-19th-2017 
*/
public class MyMarkUp {
    /**
             * This is a default constructor 
             */
    public MyMarkUp(){

    }

    /*public static void main(String[] args){
        MyMarkUp mmu = new MyMarkUp();
        WebPage wp = new WebPage();
        System.out.println(System.getProperty("user.dir"));
        try{
            String startingFile = "Project1/example3.txt";
            File f = new File(startingFile);
            wp.addPageContents(startingFile, mmu.readFile(f));
            Queue<String> q = mmu.findAttachments(f);
            while(q.isEmpty() == false){
                String filename = q.poll();
                //System.out.println(filename);
                File f2 = new File(filename);
                String contents = mmu.readFile(f2);
                //System.out.println("Contents for " + filename + ": " + contents);
                wp.addPageContents(filename, contents);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println(mmu.findAttachments(f));

    }*/
    /**
             * Read through the file
             * 
             * @param file
             * @return contents
             */
    public String readFile(File file) throws Exception {
        try {
            Scanner in = new Scanner(new FileReader(file));
            String contents = "";
            while(in.hasNextLine()){
                contents += in.nextLine() + "\n";
            }
            return contents;
        }
        catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
             * find attached file
             * 
             * @param file
             * @return queue of attachment
             */
    public Queue<String> findAttachments(String contents){
        Queue<String> attachments = new LinkedList<String>();
        try{
            //create a reader for the file
            Scanner in = new Scanner(contents);
            //look for any attachments using regex
            String regex = "<src=.*>";
            Pattern pattern = Pattern.compile(regex);
            //read line by line looking for attachments
            while(in.hasNextLine()){
                String line = in.nextLine();
                Matcher m = pattern.matcher(line);
                //for any matching groups add them to the attachments
                while(m.find()){
                    String match = m.group();
                    //discard the <src=" and "> from the string
                    //for example: <src="example1.txt"> -> example1.txt
                    String discEmbed = match.substring(5, match.length()-1);
                    //if the attachment has not yet been added to the queue
                    //then add it
                    if(attachments.contains(discEmbed) == false){
                        attachments.add(discEmbed);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("file not found");
            //Do nothing just exit
        }
        return attachments;
    }
}
