import java.io.*;

public class CRuD {
    private File file;

    public CRuD(File file){
        this.file = file;
    }

    public void remove(int id){
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

    //checks if id exists in file
    public boolean idExists(int id){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                line = line.split(";")[0];
                int currentID = trimZeroesBefore(line);
                if(currentID == id) return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public int trimZeroesBefore(String line){
        while (line.startsWith("0")){
            line = line.replaceFirst("0","");
        }
        int id = Integer.parseInt(line);
        return id;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
