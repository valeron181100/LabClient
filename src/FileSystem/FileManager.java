package FileSystem;

import Clothes.Costume;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.stream.XMLEventWriter;
import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс-менеджер файла с коллекцией.
 * @author Валерий Бондарь
 */
public class FileManager {

    private String filePath;
    private String initDate;


    public boolean isDefaultFileExists(){
        File file = new File(filePath);
        boolean isExists = file.exists();
        return file.exists();
    }

    /**
     * Конструктор - создаёт менеджер файла с коллекцией.
     * @param path путь к файлу с коллекцией
     * @author Валерий Бондарь
     */
    public FileManager(String path){

        File file = new File(path);

        if(!file.exists()){
            System.err.println("Файл с коллекцией не найден.");
        }

        filePath = path;
        initDate = new Date().toString();

    }

    /**
     * Записывает нужную коллекцию представленную в формате XML в файл по умолчанию
     * @param xml xml строка с коллекцией.
     * @author Валерий Бондарь
     * @return Возвращает true, если коллекция была сохранена в файл успешно, иначе - false
     */
    public boolean writeCollectionXML(String xml){
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(xml);
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка записи коллекции!");
            return false;
        }
        return true;
    }

    /**
     * Записывает нужную коллекцию в файл по умолчанию
     * @param collection коллекция для сохранения в файл
     * @author Валерий Бондарь
     * @return Возвращает true, если коллекция была сохранена в файл успешно, иначе - false
     */
    public boolean writeCollection(HashSet<Costume> collection){
        JSONArray array = new JSONArray();
        collection.forEach(p -> array.put(p.getJson()));

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(XML.toString(array));
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка записи коллекции!");
            return false;
        }
        return true;
    }

    /**
     * Записывает нужную коллекцию в файл
     * @param collection коллекция для сохранения в файл
     * @param filePath файл, в который нужно сохранить коллекцию
     * @author Валерий Бондарь
     * @return Возвращает true, если коллекция была сохранена в файл успешно, иначе - false
     */
    public boolean writeCollection(HashSet<Costume> collection, String filePath){
        JSONArray array = new JSONArray();
        collection.forEach(p -> array.put(p.getJson()));

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(XML.toString(array));
            writer.close();
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл коллекции!");
            return false;
        }
        return true;
    }

    /**
     * Меняет файл по умолчанию с коллекцией
     * @author Валерий Бондарь
     * @param path путь к новому файлу с коллекцией
     */
    public void setDefaultCollectionFilePath(String path){
        filePath = path;
    }

    /**
     * Получает дату инициализации коллекции
     * @return String
     */
    public String getInitDate() {
        return initDate;
    }

    public String getXmlFromFile(){
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
            Scanner scanner = new Scanner(reader);
            String content = "";
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }

            reader.close();

            return content;
        } catch (FileNotFoundException e) {
            System.err.println("Файл с коллекцией не найден.");
        } catch (IOException e) {
            System.err.println("Ошибка закрытия файла с коллекцией.");
        }

        return null;
    }

    public String getXmlFromFile(String filePath){
        FileReader reader = null;
        try {
            reader = new FileReader(filePath);
            Scanner scanner = new Scanner(reader);
            String content = "";
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }

            reader.close();

            return content;
        } catch (FileNotFoundException e) {
            System.err.println("Файл с коллекцией не найден.");
        } catch (IOException e) {
            System.err.println("Ошибка закрытия файла с коллекцией.");
        }

        return null;
    }
}

