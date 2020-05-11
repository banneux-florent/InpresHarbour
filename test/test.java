
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author WADI
 */
public  class test {

    public static  Properties load(String filename) throws IOException, FileNotFoundException {
        Properties properties = new Properties();

        FileInputStream input = new FileInputStream(filename);
        try {

            properties.load(input);
            return properties;

        } finally {

            input.close();

        }

    }

}
