package com.epam.mjc.nio;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;



public class FileReader {

    public Profile getDataFromFile(File file) {

        String nameLine = "";
        String ageLine = "";
        String emailLine = "";
        String phoneLine = "";
        StringBuilder build = new StringBuilder();

        try(RandomAccessFile aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel()){
            long fileSize = inChannel.size();

            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();

            for(int i = 0; i < fileSize; i++){
                while ((char) buffer.get() != '\n'){
                    build.append((char) buffer.get(i));
                    if (i< (fileSize - 1))
                        i++;
                }
                if (build.toString().contains("Name")){
                    nameLine = build.toString();
                    build.setLength(0);
                }
                else if (build.toString().contains("Age")){
                    ageLine = build.toString();
                    build.setLength(0);
                }
                else if (build.toString().contains("Phone")){
                    phoneLine = build.toString();
                    build.setLength(0);
                }
                else if (build.toString().contains("Email")){
                    emailLine = build.toString();
                    build.setLength(0);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }




        String name = nameLine.substring(nameLine.indexOf(':') + 2);
        String age = ageLine.substring(ageLine.indexOf(':') + 2);
        String email = emailLine.substring(emailLine.indexOf(':') + 2);
        String phone = phoneLine.substring(phoneLine.indexOf(':') +2);


        return new Profile(name.trim(),Integer.valueOf(age.trim()),email.trim(),Long.valueOf(phone.trim()));
    }

}
