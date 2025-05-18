import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class Job{
    public boolean isDone;
    public String title;
    public String created;
    Job(boolean isDone,String title,String created){
        this.isDone = isDone;
        this.title=title;
        if(created!=null)
            this.created=created;
        else {
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
    private final String path = "test.txt";
    ToDoList() {
        try {
            File file = new File(path);
            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    String temp_st = sc.nextLine();
                    String[] temp_arr = temp_st.split("\t");
                    jobs.add(new Job(Boolean.valueOf(temp_arr[0]), temp_arr[1], temp_arr[2]));
                }
            }



        } catch (Exception e) {
            System.out.println(" Error ! " + e);
        }
    }

    void save(){
        try {
            File file = new File(path);
            try (FileWriter fileWriter = new FileWriter(path,false)) {
                for (Job job : jobs) {
                    fileWriter.write(job.isDone + "\t" + job.title + "\t" + job.created + "\n");
                }
            }
        }
        catch (Exception e){
            System.out.println(" Error ! " + e);
        }
    }

    void addjob(String title)
    {
        jobs.add(new Job(false,title,null));
        this.save();
    }
    void done(int index){
        jobs.get(index).checkout();
        this.save();
    }
    void delete(int index)
    {
        jobs.remove(index);
        this.save();
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