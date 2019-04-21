package mainpkg;

import Buildings.*;
import Clothes.Costume;
import Enums.*;
import FileSystem.*;
import Humanlike.*;
import NetStuff.TransferPackage;
import PhoneNTalks.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public  static void fmain(String[] args) throws IOException {

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
        System.out.println("Введите команду help для получения полного списка команд.");
        while (true) {
                try {
                    TransferPackage tpkg;
                    if (line.length() == 0) {

                        try {

                            line = scanner.nextLine();
                            String input = line.split(" ")[0];
                            if (!manager.isDefaultFileExists()) {
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

                        } catch (NoSuchElementException e) {
                            System.err.println("Завершение работы программы.");
                            System.exit(0);
                        }
                        tpkg = new TransferPackage(666, line,
                                null, manager.getXmlFromFile().getBytes(Main.DEFAULT_CHAR_SET));
                    } else {
                        if(previousCmdId == 6) {
                            byte[] bytes;
                            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                 DataOutputStream dos = new DataOutputStream(baos)) {
                                dos.writeUTF(manager.getXmlFromFile());
                                dos.writeUTF(manager.getXmlFromFile(line));
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
                                tpkg = new TransferPackage(666, line,
                                    null, manager.getXmlFromFile().getBytes(Main.DEFAULT_CHAR_SET));
                        }
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
                                manager.writeCollectionXML(new String(recievedPkg.getAdditionalData(), Main.DEFAULT_CHAR_SET));
                                break;
                            case 6:
                                previousCmdId = recievedPkg.getId();
                                line = new String(recievedPkg.getAdditionalData(), Main.DEFAULT_CHAR_SET);
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
                                try {
                                    new Main().program.start(CollectionManager.getCollectionFromXML(manager.getXmlFromFile()));
                                } catch (EmptyFileException e) {
                                    System.err.println("Файл пуст!");
                                }
                                break;
                            case 101:
                                System.out.println("Соединение с сервером восстановлено!");
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

    public static void pause(String message){
        System.out.println(message);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendPackage(TransferPackage transferPackage, DatagramSocket socket, InetAddress adress, int port) throws IOException{
        byte[] tpkgBytes = transferPackage.getBytes();
        Byte[] bytes = new Byte[tpkgBytes.length];
        for(int i = 0; i < tpkgBytes.length; i++){
            bytes[i] = tpkgBytes[i];
        }
        ArrayList<Byte> list =new ArrayList<>();
        Collections.addAll(list, bytes);
        List<List<Byte>> choppedList = chopped(list, 1022);
        int N = (choppedList.size() - 1);
        for(int j = 0; j < choppedList.size(); j++) {
            List<Byte> subList = choppedList.get(j);
            byte[] subBytes = new byte[subList.size() + 2];
            for (int i = 2; i < subBytes.length; i++) {
                subBytes[i] = subList.get(i);
            }
            subBytes[1] = (byte)N;
            subBytes[0] = (byte)j;

            socket.send(new DatagramPacket(subBytes, subBytes.length, adress, port));
            byte[] receiveData = new byte[1];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            if (receivePacket.getData()[0] == 1){
                continue;
            }
            else{
                throw new SocketTimeoutException();
            }
        }

    }

    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }
}




