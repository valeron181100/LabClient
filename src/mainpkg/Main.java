package mainpkg;

import Buildings.*;
import Clothes.Costume;
import Enums.*;
import FileSystem.*;
import Humanlike.*;
import NetStuff.ShutdownHandler;
import NetStuff.TransferPackage;
import NetStuff.User;
import PhoneNTalks.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static final String DEFAULT_CHAR_SET = "UTF-8";

    public class Startingprogramm{
        public void start(HashSet<Costume> costumeList) {
            class Baby extends NormalHuman {
                Baby(){
                    super("Малыш", true, 15,
                            Color.Blonde, true, BodyType.MediumBuild, false, 50);
                }
            }
            ArrayList<Costume> costumes = new ArrayList<Costume>(costumeList);

            Baby baby = new Baby();
            baby.set_costume(costumes.get(0));

            FlyableHuman carlson = new FlyableHuman("Карлсон", true, 20,
                    Color.Blonde, true, BodyType.Strong, true, 80){
            };
            carlson.setPropellerModel(PropellerModel.P420);
            carlson.set_costume(costumes.get(1));

            NormalHuman fBock = new NormalHuman("Фрекен Бок", true, 40,
                    Color.Red, false, BodyType.Plump, true, 50);
            fBock.set_costume(costumes.get(2));

            NormalHuman bosse = new NormalHuman("Боссе", true, 17,
                    Color.White, true, BodyType.Athletic, true, 70);
            bosse.set_costume(costumes.get(3));


            NormalHuman frida = new NormalHuman("Фрида", true, 40,
                    Color.Orange, false, BodyType.MediumBuild, false, 50);
            frida.set_costume(costumes.get(4));

            Room lobby = new Room(RoomType.Lobby, baby, carlson, fBock, frida, bosse);

            Room bossesRoom = new Room(RoomType.BedRoom);

            lobby.add_rooms(new Room(RoomType.Hall), new Room(RoomType.BedRoom), new Room(RoomType.Kitchen)
                    , bossesRoom);

            House house = null;
            try {
                house = new House(lobby);
            } catch (HouseException e) {
                e.printStackTrace();
            }
            if (house != null)
                house.add(baby, carlson, fBock, frida, bosse);
            baby.Goto(house.getLobby().get_rooms().get(0));
            carlson.Goto(house.getLobby().get_rooms().get(2));
            fBock.Goto(house.getLobby().get_rooms().get(0));
            frida.Goto(house.getLobby().get_rooms().get(1));
            bosse.Goto(house.getLobby().get_rooms().get(3));

            fBock.becomeBusy();
            PhoneCall call_1 = (PhoneCall) frida.get_phone().call(baby.get_phone().get_number());

            baby.set_positive_mood();

            if (fBock.is_busy()){
                call_1.say(baby, String.format("%s занята!", fBock.toString()));
            }

            for(int i = 0; i < 5; i++ ){
                call_1.ask(frida);
                call_1.answer(baby);
            }

            call_1.say(baby, "Хватит вопросов, я же сказал, что она занята!");

            baby.get_phone().putDown();

            for (Room r : house.getLobby().get_rooms()){
                baby.Goto(r);
                if(baby.search_here(carlson))
                    break;
            }

            Talk talk = new Talk(house, baby.get_position().getRoom(), baby, carlson);

            for(int i = 0; i < 2; i++){
                talk.say_joke(baby);
                talk.answ_joke(carlson);
                talk.say_joke(carlson);
                talk.answ_joke(baby);
            }
            talk.end();

            carlson.fly();
            carlson.get_position().getHouse().get_people().remove(carlson);


            baby.set_negative_mood();
            baby.Goto(house.getLobby().get_rooms().get(3));

            bosse.Goto(house.getLobby().get_rooms().get(2));
            baby.Goto(house.getLobby().get_rooms().get(2));
            fBock.Goto(house.getLobby().get_rooms().get(2));
            frida.Goto(house.getLobby().get_rooms().get(2));

            frida.drink_alcohol();
            fBock.drink_alcohol();
            baby.drink_alcohol();
            bosse.drink_alcohol();

            Main.pause("Конец!!!");
        }
    }

    public Startingprogramm program;

    public Main(){
        program = new Startingprogramm();
    }

    public  static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Введите адресс и порт в соответствующем порядке!");
            System.exit(0);
        }

        if(args.length == 1){
            System.out.println("Введите порт!");
            System.exit(0);
        }

        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);
        clientSocket.setSoTimeout(2000);
        Scanner scanner = new Scanner(System.in);
        FileManager manager = new FileManager("file.xml");
        final boolean[] isConnected = {true};
        String line = "";
        int previousCmdId = 0;
        User user = new User();
        Runtime.getRuntime().addShutdownHook(new ShutdownHandler(user, clientSocket, IPAddress, port));
        //System.out.println("Введите команду help для получения полного списка команд.");
        while (true) {
                try {
                    TransferPackage tpkg = null;
                    if (line.length() == 0) {
                        try {
                            String input = null;
                            if(user.isLoggedIn()) {
                                line = scanner.nextLine();
                                if(findMatches("[ \\?\\\\n\\\\t!/.\\^\\:\\%\\$\\;\\#\\\"\\'\\,\\(\\)\\+\\=\\`\\~]", line).size() != 0) {
                                     System.out.println("Ошибка:\nНеверная комманда!");
                                    line = "";
                                    continue;
                                }
                                 input = line.split(" ")[0];
                            }
                            else {
                                System.out.println("Пожалуйста введите логин и пароль:");
                                System.out.print("Логин: ");
                                String login = scanner.nextLine();
                                user.setLogin(login);
                                System.out.print("Пароль: ");
                                String password = scanner.nextLine();
                                user.setPassword(password);
                                System.out.println();
                                line = "login";
                                tpkg = new TransferPackage(110, "login {" + user.getLogin() +"} {"+user.getPassword()+"}", null);
                            }

                            /// Блок кода разрешающий выполнение упомянутых в блоке комманд если файл не существует
                            if (!manager.isDefaultFileExists() && user.isLoggedIn()) {
                                switch (input) {
                                    case "help":
                                    case "change_def_file_path":
                                        continue;
                                    default:
                                        line = "";
                                        System.out.println("Файл с коллекцией не найден!");
                                        continue;

                                }
                            }
                            ///.....

                        } catch (NoSuchElementException e) {
                            System.err.println("Завершение работы программы.");
                            System.exit(0);
                        }
                        if (user.isLoggedIn())
                            if(line.trim().equals("load"))
                                tpkg = new TransferPackage(666, line,
                                        null, manager.getXmlFromFile().getBytes(Main.DEFAULT_CHAR_SET));
                            else
                                tpkg = new TransferPackage(666, line, null);
                    } else {
                        if(previousCmdId == 6) {
                            byte[] bytes;
                            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                 ObjectOutputStream dos = new ObjectOutputStream(baos)) {
                                dos.writeObject(CollectionManager.getCollectionFromXML(manager.getXmlFromFile(line)));
                                bytes = baos.toByteArray();
                            }
                            line = "I1A8S1D1F0G0H";
                            tpkg = new TransferPackage(666, line,
                                    null, bytes);
                        }
                        else{
                            if(line.equals("help") | line.split(" ")[0].equals("change_def_file_path")){
                                tpkg = new TransferPackage(666, line,
                                        null, CollectionManager.collectionStringXML.getBytes(Main.DEFAULT_CHAR_SET));
                            }
                            else
                            if(line.trim().equals("load"))
                                tpkg = new TransferPackage(666, line,
                                        null, manager.getXmlFromFile().getBytes(Main.DEFAULT_CHAR_SET));
                            else
                                tpkg = new TransferPackage(666, line, null);
                        }
                    }

                    if(user.getLogin().length() == 0 || findMatches("[ \\?\\\\n\\\\t!/.\\^\\:\\%\\,\\$\\;\\#\\\"\\'\\(\\)\\+\\=\\`\\~]", line).size() != 0){
                        System.out.println("Неправильный ввод логина!");
                        line = "";
                        continue;
                    }
                    if (user.getPassword().length() < 8  || findMatches("[ \\?\\\\n\\\\t!/.\\^\\:\\%\\$\\,\\;\\#\\\"\\'\\(\\)\\+\\=\\`\\~]", line).size() != 0){
                        System.out.println("Пароль должен быть не менее 8-ми символов и не содержать специальных символов!");
                        line = "";
                        continue;
                    }
                    byte[] sendData = tpkg.getBytes();
                    DatagramPacket sendingPkg = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    clientSocket.send(sendingPkg);

                    byte[] receiveData = new byte[65536];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    if (!isConnected[0])
                        System.out.println();
                    isConnected[0] = true;
                    TransferPackage recievedPkg = TransferPackage.restoreObject(new ByteArrayInputStream(receivePacket.getData()));
                    if (recievedPkg != null) {
                        switch (recievedPkg.getId()) {
                            case 11:
                            case 2:
                            case 4:
                            case 5:
                                previousCmdId = recievedPkg.getId();
                                System.out.println(recievedPkg.getCmdData());
                                System.out.println(new String(recievedPkg.getAdditionalData(), Main.DEFAULT_CHAR_SET));
                                break;
                            case 9:
                                previousCmdId = recievedPkg.getId();
                                System.out.println(recievedPkg.getCmdData());
                                System.out.println("Завершение работы программы.");
                                System.exit(0);
                                break;
                            case 7:
                            case 3:
                            case 1:
                            case 601:
                                previousCmdId = recievedPkg.getId();
                                System.out.println(recievedPkg.getCmdData());
                                break;
                            case 6:
                                previousCmdId = recievedPkg.getId();
                                line = new String(recievedPkg.getAdditionalData(), Main.DEFAULT_CHAR_SET);
                                System.out.println(line);
                                if(new File(new String(recievedPkg.getAdditionalData(), Main.DEFAULT_CHAR_SET)).exists())
                                    continue;
                                else
                                    break;
                            case -1:
                                previousCmdId = recievedPkg.getId();
                                System.out.println("Ошибка: ");
                                System.out.print(recievedPkg.getCmdData());
                                if (recievedPkg.getAdditionalData() != null) {
                                    System.out.print(new String(recievedPkg.getAdditionalData(), Main.DEFAULT_CHAR_SET));
                                }
                                System.out.println();
                                break;
                            case 10:
                                previousCmdId = recievedPkg.getId();
                                System.out.println(recievedPkg.getCmdData());
                                String filePath = new String(recievedPkg.getAdditionalData(), Main.DEFAULT_CHAR_SET);
                                manager.setDefaultCollectionFilePath(filePath);
                                break;
                            case 8:
                                previousCmdId = recievedPkg.getId();
                                System.out.println(recievedPkg.getCmdData());
                                new Main().program.start(new HashSet<>(recievedPkg.getData().collect(Collectors.toList())));
                                break;
                            case 101:
                                TransferPackage transferPackage = new TransferPackage(666, "login {" + user.getLogin() + "} {" + user.getPassword() + "}", null);
                                byte[] bytes = transferPackage.getBytes();
                                clientSocket.send(new DatagramPacket(bytes, bytes.length, IPAddress, port));
                                System.out.println("Соединение с сервером восстановлено!");
                                break;
                            case 110:
                                if(recievedPkg.getAdditionalData()[0] == 1){
                                    user.setLoggedIn(true);
                                    System.out.println("Вы успешно зарегистрированы!");
                                }
                                if(recievedPkg.getAdditionalData()[0] == 2){
                                    user.setLoggedIn(true);
                                    System.out.println("Вы успешно авторизированы!");
                                }
                                break;
                            case 12:
                                manager.writeCollection(CollectionManager.getCollectionFromBytes(recievedPkg.getAdditionalData()));
                                System.out.println(recievedPkg.getCmdData());
                        }


                    } else {
                        System.out.println("Ничего не пришло!");
                    }
                    line = "";

                } catch (SocketTimeoutException e) {
                    if(isConnected[0])
                        System.out.println("Соединение с сервером было внезапно разорвано! Попытка соединения");
                    else
                        System.out.print(".");
                    isConnected[0] = false;
                }
                catch (Exception e){
                    System.err.println(e.getMessage());
                }
        }
    }

    public static ArrayList<String> findMatches(String patterStr, String text){
        Pattern pattern = Pattern.compile(patterStr);
        Matcher matcher = pattern.matcher(text);
        ArrayList<String> collection = new ArrayList<>();
        while(matcher.find()){
            collection.add(text.substring(matcher.start(), matcher.end()));
        }
        return collection;
    }

    public static void pause(String message){
        System.out.println(message);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}




