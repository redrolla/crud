//package crud;

import java.io.*;

public class Main {
    private File file;

    public static void main(String[] args){
        Main mainInstance = new Main();
        System.out.println(args[0]);
        mainInstance.file = new File(args[0]);
        //input information via console
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)))
        {
        String line;
            while ((line = bufferedReader.readLine()) != null){
                if(line.equals("exit")) break;

                if(line.startsWith("-c ")){
                    System.out.println("Creating new entry.");
                    //TODO check if entry is right?
                    line = line.replaceFirst("-c ","");
                    mainInstance.create(line,mainInstance.file);
                    //if line.split(" ")[0].equals("-c") => call @create
                }else
                if(line.startsWith("-u ")){
                    System.out.println("Updating existing entries.");
                }else
                if(line.startsWith("-d ")){
                    System.out.println("Deleting entry.");
                }else{
                    System.out.println("Wrong request, please rewrite.");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //adds a line to the end of the file with id = max_id + 1;
    public void create(String trafficLine, File file){
        int maxID = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            //cycling through file by lines and finding max ID
            String line;
            while ((line = bufferedReader.readLine()) != null){
                line = line.split(";")[0];
                while (line.startsWith("0")){
                    line = line.replaceFirst("0","");
                }
                int currentID = Integer.parseInt(line);
                if(currentID > maxID) maxID = currentID;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //writing new line with maxID + 1 in the end of file;
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true))){

            String[] splittedTrafficLine = trafficLine.split(";");
            if(splittedTrafficLine.length != 6){
                System.out.println("Error, input must be [ship_name;ship_class;current_crew_capacity;tonnage;destination;has_weapons?] without []");
                System.out.println("Repeat you request please.");
            }else{
                StringBuilder newLine = new StringBuilder();

                /*
                maxID
                splittedTrafficLine[0] => ship_name
                splittedTrafficLine[1] => ship_class
                splittedTrafficLine[2] => current_crew_capacity
                splittedTrafficLine[3] => tonnage
                splittedTrafficLine[4] => destination
                splittedTrafficLine[5] => has_weapons?
                 */
                newLine.append(addZerosBefore(String.valueOf(maxID+1),8));
                newLine.append(";");
                newLine.append(addSpacesAfter(splittedTrafficLine[0],32));
                newLine.append(";");
                newLine.append(addSpacesAfter(splittedTrafficLine[1],24));
                newLine.append(";");
                newLine.append(addZerosBefore(splittedTrafficLine[2],8));
                newLine.append(";");
                newLine.append(addZerosBefore(splittedTrafficLine[3],8));
                newLine.append(";");
                newLine.append(addSpacesAfter(splittedTrafficLine[4],24));
                newLine.append(";");
                newLine.append(addSpacesAfter(splittedTrafficLine[5],5));

                /*
                    TODO write new line in File
                    test entries
                    -c Salvation;Grand cruiser;4353;350000;Eye Of Terror;true
                    -c Retribution;Grand cruiser;00003456;00350000;Holy Terra    ;true
                    -c Harrower          ;Firestorm frigate ;00000246;00005367;Ultramar.Calth;true
                    -c Golden Coin;Privateer frigate;114;6000;Ultramar.Maccrage;false

                 */
                if(file.length() != 0) bufferedWriter.write(System.lineSeparator());
                bufferedWriter.write(newLine.toString());

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //adds zeros before text to the desirableLength
    public static String addZerosBefore(String text, int desirableLength){
        if(text.length() > desirableLength) throw new IllegalArgumentException("Text is longer than desirableLength");
        if(text.length() == desirableLength) return text;

        text = text.trim();
        String result = "";
        for(int i = 0; i < desirableLength-text.length();i++){
            result+="0";
        }
        result+=text;
        return result;
    }

    //adds spaces after text to desirableLength
    public static String addSpacesAfter(String text, int desirableLength){
        if(text.length() > desirableLength) throw new IllegalArgumentException("Text is longer than desirableLength");
        if(text.length() == desirableLength) return text;

        text = text.trim();
        String result = text;
        for(int i = 0; i < desirableLength-text.length();i++){
            result+=" ";
        }
        return result;
    }
}
