import java.io.*;
import java.util.Arrays;

public class CRuD {
    private File file;

    public CRuD(File file){
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void create(String line){
        //writing new line with maxID + 1 in the end of file;
        String[] splittedTrafficLine = line.split(";");
        if(splittedTrafficLine.length != 6){
            System.out.println("Error, input must be [ship_name;ship_class;current_crew_capacity;tonnage;destination;has_weapons?] without []");
            System.out.println("Repeat you request please.");
        }else {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                String newLine = buildString(maxId() + 1, splittedTrafficLine);
                if (file.length() != 0) bufferedWriter.write(System.lineSeparator());
                bufferedWriter.write(newLine);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //updates the String by id
    public void update(String line){
        //-u 3;Salvation;Cruiser;00002353;00150000;Eye Of Terror;true

        String[] splittedTrafficLine = line.split(";");
        if(splittedTrafficLine.length != 7) {
            System.out.println("Error, input must be [id;ship_name;ship_class;current_crew_capacity;tonnage;destination;has_weapons?] without []");
            System.out.println("Repeat you request please.");
        }else {
            int id = trimZeroesBefore(splittedTrafficLine[0]);
            splittedTrafficLine = Arrays.copyOfRange(splittedTrafficLine,1,splittedTrafficLine.length);
            if (idExists(id)){
                String newLine = buildString(id,splittedTrafficLine);
                System.out.println(newLine);

                //create .tmp file, copy all contents from original file except line with id, line replaced by update
                File tempFile = new File(file.getAbsolutePath()+".tmp");
                try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))){

                    int currentID =-1;
                    String tempLine = null;
                    while ((tempLine = bufferedReader.readLine()) != null){
                        // extracts id from line
                        if(tempLine.length() > 8){
                            currentID = trimZeroesBefore(tempLine.split(";")[0]);
                        }

                        if(currentID != id){
                            //write the line in temp file
                            bufferedWriter.write(tempLine);
                        }else{
                            //write updated line
                            bufferedWriter.write(newLine);
                        }
                        bufferedWriter.write(System.lineSeparator());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    //Rename the new file to the filename the original file had.
                    file.delete();
                    if (!tempFile.renameTo(file))
                        System.out.println("Could not rename file");

                }
            }
        }
    }

    public void delete(int id){
        if(idExists(id)){
            //create .tmp file, copy all contents from original file except line with id
            File tempFile = new File(file.getAbsolutePath()+".tmp");
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))){

                int currentID =-1;
                String line = null;
                while ((line = bufferedReader.readLine()) != null){
                    // extracts id from line
                    if(line.length() > 8){
                        currentID = trimZeroesBefore(line.split(";")[0]);
                    }

                    if(currentID != id){
                        //write the line in temp file
                        bufferedWriter.write(line);
                        bufferedWriter.write(System.lineSeparator());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                //Rename the new file to the filename the original file had.
                file.delete();
                if (!tempFile.renameTo(file))
                    System.out.println("Could not rename file");

            }
        }else{
            System.out.println("There is no such ID in file.");
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

    //forms up the String in needed format
    public String buildString(int id, String[] splittedLine){
        StringBuilder newLine = new StringBuilder();
                /*
                id
                splittedTrafficLine[0] => ship_name
                splittedTrafficLine[1] => ship_class
                splittedTrafficLine[2] => current_crew_capacity
                splittedTrafficLine[3] => tonnage
                splittedTrafficLine[4] => destination
                splittedTrafficLine[5] => has_weapons?
                 */
        newLine.append(addZerosBefore(String.valueOf(id),8));
        newLine.append(";");
        newLine.append(addSpacesAfter(splittedLine[0],32));
        newLine.append(";");
        newLine.append(addSpacesAfter(splittedLine[1],24));
        newLine.append(";");
        newLine.append(addZerosBefore(splittedLine[2],8));
        newLine.append(";");
        newLine.append(addZerosBefore(splittedLine[3],8));
        newLine.append(";");
        newLine.append(addSpacesAfter(splittedLine[4],24));
        newLine.append(";");
        newLine.append(addSpacesAfter(splittedLine[5],5));

        return newLine.toString();
    }


    //checks if id exists in file
    public boolean idExists(int id){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                line = line.split(";")[0];
                int currentId = trimZeroesBefore(line);
                if(currentId == id) return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int maxId(){
        int maxId = -1;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            //cycling through file by lines and finding max Id
            String line;
            while ((line = bufferedReader.readLine()) != null){
                line = line.split(";")[0];
                int currentId = trimZeroesBefore(line);
                if(currentId > maxId) maxId = currentId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return maxId;
    }

    public int trimZeroesBefore(String line){
        while (line.startsWith("0")){
            line = line.replaceFirst("0","");
        }
        int id = Integer.parseInt(line);
        return id;
    }
}
