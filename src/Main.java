import java.io.File;
import java.nio.file.*;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class Job{
    public boolean isDone;
    public String title;
    public String created;
    Job(boolean isDone,String title,String created){
        if(isDone)
            this.isDone = true;
        else
            this.isDone = false;
        this.title=title;
        if(created!=null)
            this.created=created;
        else {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = localDateTime.format(formatter);
            this.created=formattedDate;
        }
    }
    void checkout()
    {
        isDone = true;
    }
}

class ToDoList {
    public ArrayList<Job> jobs = new ArrayList<>();

    ToDoList() {
        try {
            File file = new File("test.txt");
            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String temp_st = sc.nextLine();
                    String[] temp_arr = temp_st.split(";/;;");
                    jobs.add(new Job(Boolean.valueOf(temp_arr[0]), temp_arr[1], temp_arr[2]));
                }
            }



        } catch (Exception e) {
            System.out.println(" Error ! " + e);
        }
    }

    void addjob(String title)
    {
        jobs.add(new Job(false,title,null));
        try {
            File file = new File("test.txt");
            if (file.exists())
                file.delete();
            try (FileWriter fileWriter = new FileWriter("test.txt")) {
                for (Job job : jobs) {
                    fileWriter.write(job.isDone + ";/;;" + job.title + ";/;;" + job.created + "\n");
                }
            }
        }
        catch (Exception e){
            System.out.println(" Error ! " + e);
        }
    }
    void done(int index){
        jobs.get(index).checkout();
        try {
            File file = new File("test.txt");
            if (file.exists())
                file.delete();
            try (FileWriter fileWriter = new FileWriter("test.txt")) {
                for (Job job : jobs) {
                    fileWriter.write(job.isDone + ";/;;" + job.title + ";/;;" + job.created + "\n");
                }
            }
        }
        catch (Exception e){
            System.out.println(" Error ! " + e);
        }
    }
    void delete(int index)
    {
        jobs.remove(index);
        try {
            File file = new File("test.txt");
            if (file.exists())
                file.delete();
            try (FileWriter fileWriter = new FileWriter("test.txt")) {
                for (Job job : jobs) {
                    fileWriter.write(job.isDone + ";/;;" + job.title + ";/;;" + job.created + "\n");
                }
            }
        }
        catch (Exception e){
            System.out.println(" Error ! " + e);
        }
    }


}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ToDoList job_list = new ToDoList();
        while (true) {
            System.out.println("  <<<<<<  HELLO TO JOBLIST  >>>>>>  \n THERE ARE LIST OF JOBS YOU TO DO IT : ");
            int i=0;
            for(Job lists : job_list.jobs) {
                System.out.printf(" %d : " + (lists.isDone ? " [✓] " : " [ ] ") + " %s  , created : %s \n"
                        , i, lists.title, lists.created);
                i+=1;
            }
            System.out.println("PLEASE CHOOSE OPERATOR 1=>add 2=>delete 3=>done 4=>exit");
            String op = sc.nextLine();
            int index;
            if(Integer.parseInt(op)>0 && Integer.parseInt(op)<5)
                switch (Integer.parseInt(op)){
                    case 1:
                        System.out.println("Enter job title : ");
                        job_list.addjob(sc.nextLine());
                        break;
                    case 2:
                        System.out.println("Enter job index ");
                        index = Integer.parseInt(sc.nextLine());
                        if(index>=0 && index < job_list.jobs.size())
                            job_list.delete(index);
                        else {
                            System.out.println("out of index ! \n press any key to continue ... ");
                            sc.nextLine();
                        }
                        break;
                    case 3:
                        System.out.println("Enter job index ");
                        index = Integer.parseInt(sc.nextLine());
                        if(index>=0 && index < job_list.jobs.size())
                            job_list.done(index);
                        else {
                            System.out.println("out of index ! \n press any key to continue ... ");
                            sc.nextLine();
                        }
                        break;
                    case 4:
                        System.out.println("GoodBye");
                        System.exit(0);
                }
            else {
                System.out.println("invalid operation ! \n Press Any Key To Continue ! ");
                sc.nextLine();
            }
        }

    }
}