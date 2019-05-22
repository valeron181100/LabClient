package FileSystem;

import Clothes.Costume;
import NetStuff.Net.TransferPackage;

import mainpkg.Main;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *Класс для дешифровки команд и их выполнения.
 */
public enum Command {

    @SuppressWarnings("unchecked")
    REMOVE((command,transferPackage, data) ->{
        try{
            String strData = "";
            for(Object s : data.toArray()) strData += s.toString();
            JSONObject jsonObject = new JSONObject(strData);
            Costume costume = new Costume(jsonObject);

            data = null;

            HashSet<Costume> collection = new HashSet<>();

            ((Stream<Costume>)transferPackage.getData()).sequential().collect(Collectors.toCollection(() -> collection));

            collection.remove(costume);



            command.setData(Stream.of(new TransferPackage(1, "Команда выполнена.", collection.stream())));

            System.out.println("Команда выполнена.");
        }
        catch (JSONException e){
            System.err.println("Ошибка: json-объект введён неверно.");
        }

    }),

    SHOW((command,transferPackage, data)->{
        data = null;

        HashSet<Costume> collection = new HashSet<>();

        transferPackage.getData().sequential().collect(Collectors.toCollection(() -> collection));

        String output = "";

        for(Costume p : collection){
            output += p.toString() + "\t";
        }

        command.setData(Stream.of(new TransferPackage(2, "Команда выполнена.",null, output.getBytes(Main.DEFAULT_CHAR_SET))));

        System.out.println("Команда выполнена.");
    }),
    ADD_IF_MAX((command,transferPackage, data)->{
        try {

            // Now data has a Costume object in json format
            String strData = "";
            for (Object s : data.toArray()) strData += s.toString();
            JSONObject jsonObject = new JSONObject(strData);
            Costume costume = new Costume(jsonObject);
            data = null; //Now data is null

            //Create collection
            HashSet<Costume> collection = new HashSet<>();

            //Fill collection
            ((Stream<Costume>)transferPackage.getData()).sequential().collect(Collectors.toCollection(() -> collection));

            Costume maxCostume = Collections.max(collection);

            if (costume.compareTo(maxCostume) > 0) {
                collection.add(costume);
            }

            data = Stream.of(new TransferPackage(3, "Команда выполнена.", collection.stream()));
            // Now data has Transfer Package for sending
            System.out.println("Команда выполнена.");
        }
        catch (JSONException e){
            System.err.println("Ошибка: json-объект введён неверно.");
        }
    }),
    LOAD((command,manager, data)->{
        data = Stream.of(new TransferPackage(4, "Команда выполнена.", null, "Load collection to server".getBytes(Main.DEFAULT_CHAR_SET)));
        System.out.println("Команда выполнена.");
    }),
    INFO((command,transferPackage, data)->{
        ByteArrayOutputStream byteObject = new ByteArrayOutputStream();

        HashSet<Costume> collection = new HashSet<>();

        ((Stream<Costume>)transferPackage.getData()).sequential().collect(Collectors.toCollection(() -> collection));

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteObject);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteObject.close();
            data = Stream.of(new TransferPackage(5, "Команда выполнена.", null,
                    String.format(
                            "Тип коллекции: %s \nТип элементов коллекции: %s\nДата инициализации: %s\nКоличество элементов: %s\nРазмер: %s байт\n",
                            collection.getClass().getName(),
                            "Clothes.Costume", new Date().toString(), collection.size(), byteObject.toByteArray().length
                    ).getBytes(Main.DEFAULT_CHAR_SET)));

        } catch (IOException e) {
            data = Stream.of(new TransferPackage(-1, "Команда выполнена.",null,
            "Ошибка при определении размера памяти коллекции.".getBytes(Main.DEFAULT_CHAR_SET)));
        }
        System.out.println("Команда выполнена.");
    }),
    IMPORT((command,transferPackage, data)->{
        String path = "";
        for(Object s : data.toArray()) path += s.toString();
        data = Stream.of(new TransferPackage(601, "Команда выполнена.", null, path.getBytes(Main.DEFAULT_CHAR_SET)));
        System.out.println("Первый этап импорта пройден.");
    }),

    I1A8S1D1F0G0H((command,transferPackage, data)->{
        ByteArrayInputStream bis = new ByteArrayInputStream(transferPackage.getAdditionalData());
        DataInputStream in = new DataInputStream(bis);

        boolean isFileExists = false;
        try {
            isFileExists = in.readBoolean();

        if(isFileExists){
            data = Stream.of(new TransferPackage(602, "Команда выполнена.", null));
        }
        else{
            data = Stream.of(new TransferPackage(-1, "Команда выполнена.",
                    null, "Файл с коллекцией не найден.".getBytes(Main.DEFAULT_CHAR_SET)));
        }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }),

    I1A6S1D0F0G0H((command,transferPackage, data)->{

        HashSet<Costume> mainCollection = new HashSet<>();

        ((Stream<Costume>)transferPackage.getData()).sequential().collect(Collectors.toCollection(() -> mainCollection));

        //Коллекция присылается уже в виде стрима который был получен после конкатенации двух стримов коллекций

        data = Stream.of(new TransferPackage(-1, "Команда выполнена.",
               mainCollection.stream()));

    }),

    ADD((command,transferPackage, data)->{
        try {
            String strData = "";
            for (Object s : data.toArray()) strData += s.toString();
            JSONObject jsonObject = new JSONObject(strData);
            Costume costume = new Costume(jsonObject);

            data = null;

            HashSet<Costume> collection = new HashSet<>();

            ((Stream<Costume>)transferPackage.getData()).sequential().collect(Collectors.toCollection(() -> collection));

            collection.add(costume);

            data = Stream.of(new TransferPackage(7, "Команда выполнена.", collection.stream()));
            System.out.println("Команда выполнена.");
        }
        catch (JSONException e){
            data = Stream.of(new TransferPackage(-1, "Команда выполнена.", null, "Ошибка: json-объект введён неверно.".getBytes(Main.DEFAULT_CHAR_SET)));
        }
    }),
    START((command,transferPackage, data)->{

        HashSet<Costume> collection = new HashSet<>();

        ((Stream<Costume>)transferPackage.getData()).sequential().collect(Collectors.toCollection(() -> collection));

        if (collection.size() != 0 ||
                collection.size() >= 5) {

            data = Stream.of(new TransferPackage(8, "Команда выполнена.", collection.stream()));

            //new Main().program.start(manager.getCollectionCase());
        }
        else{
            System.err.println("Ошибка: для запуска программы необходимо хотя бы 5 элементов в коллекции.");
        }
    }),
    EXIT((command,transferPackage, data)->{
        command.setData(Stream.of(new TransferPackage(9, "Команда выполнена.", null, "null".getBytes(Main.DEFAULT_CHAR_SET))));
        System.out.println("Команда выполнена.");
    }),
    HELP((command,transferPackage, data)->{
        command.setData(Stream.of(new TransferPackage(10, "Команда выполнена.", null,
                ("remove {element}: удалить элемент из коллекции по его значению\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "load: перечитать коллекцию из файла\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "import {String path}: добавить в коллекцию все данные из файла\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "start: начать выполнение программы\n" +
                "exit: выйти из программмы\n" +
                "change_def_file_path {String path}: меняет путь к файлу с коллекцией на новый.\n" +
                "trimToMin: обрезает коллекцию до минимума.\n" +
                "help: справка \n" +
                "Пример правильного формата ввода объекта json: \n" +
                "{\"topClothes\":{\"growth_sm\":170,\"size\":50,\"color\":\"White\",\"material\":\"Chlopoc\",\"is_hood\":false,\"name\":\"T-Shirt\",\"is_for_man\":true,\"hand_sm_length\":60},\"downClothes\":{\"size\":50,\"color\":\"Black\",\"material\":\"Chlopoc\",\"diametr_leg_sm\":40,\"name\":\"Trousers\",\"leg_length_sm\":70,\"is_for_man\":true},\"underwear\":{\"sex_lvl\":100,\"size\":50,\"color\":\"Red\",\"material\":\"Chlopoc\",\"name\":\"Panties\",\"is_for_man\":true},\"hat\":{\"cylinder_height_sm\":15,\"size\":50,\"color\":\"White\",\"material\":\"Len\",\"visor_length_sm\":20,\"name\":\"BaseballHat\",\"is_for_man\":true},\"shoes\":{\"is_shoelaces\":true,\"size\":38,\"color\":\"White\",\"material\":\"Leather\",\"outsole_material\":\"Rubber\",\"name\":\"Sneackers\",\"is_for_man\":true}}"
        ).getBytes())));
    }),
    CHANGE_DEF_FILE_PATH(((command,transferPackage, data) -> {
        String strData = "";
        for(Object s : data.toArray()) strData += s.toString();
        data = Stream.of(new TransferPackage(10, "Команда выполнена.", null, strData.getBytes(Main.DEFAULT_CHAR_SET)));
    })),
    TRIMTOMIN((command,transferPackage,data) -> {
        HashSet<Costume> collection = new HashSet<>();

        ((Stream<Costume>)transferPackage.getData()).sequential().collect(Collectors.toCollection(() -> collection));

        ArrayList<Costume> list = new ArrayList<>(collection);
        for(int i = 0; collection.size() > 5; i++){
            collection.remove(list.get(list.size() - 1 - i));
        }
        data = Stream.of(new TransferPackage(7, "Команда выполнена.", collection.stream()));
        System.out.println("Команда выполнена.");
    });

    Command(ICommand cmd){
        this.cmd = cmd;
    }

    /**Тело выполняемой команды.*/
    private ICommand cmd;

    /**Данные, с которыми оперирует команда.*/
    private Stream data;

    private TransferPackage sendingPackage;

    private void setData(Stream data) {
        this.data = data;
    }

    /**
     * Метод для дешифровки команды, представленной в строке формата json.
     * @author Валерий Бондарь
     * @param jsonInput - строка формата json
     */
    public static Command parseCmd(String jsonInput) throws IllegalArgumentException{
        if(jsonInput.contains("{")){
            Command command = Command.valueOf(jsonInput.split(Pattern.quote("{"))[0].replace(" ", "").toUpperCase());

            String data =  jsonInput.substring(command.toString().length() + 2, jsonInput.length() - 1);
            command.data = Stream.of(data);
            return command;
        }
        else{
            Command command = Command.valueOf(jsonInput.replace(" ", "").toUpperCase());
            if(command == Command.REMOVE || command == Command.ADD || command == Command.ADD_IF_MAX ||
                    command == Command.CHANGE_DEF_FILE_PATH || command == Command.IMPORT){
                throw new IllegalArgumentException("Отсутствует аргумент команды");
            }
            else {
            return command;
            }
        }
    }

    /**
     * Метод для дешифровки команды, представленной в строке формата json.
     * @author Валерий Бондарь
     */
    @SuppressWarnings("unchecked")
    public TransferPackage start(Command command,TransferPackage transferPackage){
        try {
            this.cmd.start(command,transferPackage, Stream.of(transferPackage.getCmdData()));
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        }
        Object obj = data.findFirst().orElse(null);
        return (TransferPackage) obj ;
    }
}
