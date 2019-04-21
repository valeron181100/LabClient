package NetStuff;

import FileSystem.Command;
import FileSystem.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerThreadHandler implements Runnable {

    private Socket socket;

    public ServerThreadHandler(Socket incomingSocket){
        socket = incomingSocket;
    }

    @Override
    public void run() {
        try{
            try{
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                Scanner in = new Scanner(inputStream);
                PrintWriter out = new PrintWriter(outputStream, true);

                out.println("Для получения справки введите команду help.");

                boolean done = false;

                while(!done){

                    try {
                        TransferPackage transferPackage = TransferPackage.restoreObject(inputStream);

                        Command cmd = null;

                        if (transferPackage != null) {
                            cmd = Command.parseCmd(transferPackage.getCmdData());
                          //  out.print(cmd.start());

                            if (cmd == Command.EXIT){
                                done = true;
                            }
                        }
                        else{
                            out.println("Ошибка: команда не найдена");
                        }



                    }
                    catch (IllegalArgumentException e){
                        if (e.getMessage().length() != 0)
                            out.println("Ошибка: команда не найдена или " + e.getMessage());
                        else
                            out.println("Ошибка: команда не найдена");
                    }
                }

            }
            finally {

            }
        }
        catch (IOException e){
            // TODO: 27.03.2019
        }
    }
}
