//package crud;

import java.io.*;

public class Main {
    private File file;

    public static void main(String[] args){
        Main mainInstance = new Main();
        System.out.println(args[0]);
        mainInstance.file = new File(args[0]);

        CRuD crud = new CRuD(mainInstance.file);

        //input information via console
        /*
                    test entries
                    -c Salvation;Grand cruiser;4353;350000;Eye Of Terror;true
                    -c Retribution;Grand cruiser;00003456;00350000;Holy Terra    ;true
                    -c Harrower          ;Firestorm frigate ;00000246;00005367;Ultramar.Calth;true
                    -c Golden Coin;Privateer frigate;114;6000;Ultramar.Maccrage;false

                    -r 1
                    -r 3

                    -u 3;Salvation;Cruiser;00002353;00150000;Eye Of Terror;true

         */
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
        String line;
            while ((line = bufferedReader.readLine()) != null){
                if(line.equals("exit")) break;

                if(line.startsWith("-c ")){
                    System.out.println("Creating new entry.");
                    //TODO check if entry is right?
                    line = line.replaceFirst("-c ","");
                    crud.create(line);
                    //if line.split(" ")[0].equals("-c") => call @create
                }else
                if(line.startsWith("-u ")){
                    System.out.println("Updating existing entry.");
                    line = line.replaceFirst("-u ","");
                    crud.update(line);
                }else
                if(line.startsWith("-r ")){
                    //-r id   => -r 1
                    System.out.println("Removing entry.");
                    line = line.replaceFirst("-r ","");
                    crud.remove(Integer.valueOf(line));
                }else{
                    System.out.println("Wrong request, please rewrite.");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
