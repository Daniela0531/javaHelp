package org.example;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerFactoryImpl implements ServerFactory{

    Executor executor = Executors.newFixedThreadPool(10);
    @Override
    public void listen(int port, Object service) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        executor.execute(new ClientWorker(service, serverSocket.accept()));
    }

    protected record ClientWorker(Object service, Socket socket) implements Runnable {
        public void run() {
            try (
                    ObjectInputStream inputStream =
                            new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                    ObjectOutputStream outputStream =
                            new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ) {

                while (true) {
                    int argsNum = inputStream.readInt();

                    Class<?>[] argClasses = new Class<?>[argsNum];
                    Object[] args = new Object[argsNum];
                    for (int i = 0; i < argsNum; ++i) {
                        args[i] = inputStream.readObject();
                        argClasses[i] = inputStream.readObject().getClass();
                    }

                    Method method = service.getClass().getMethod(inputStream.readUTF(), argClasses);

                    outputStream.writeObject(method.invoke(service, args));
                    
                    // to be sure that all your data from buffer is written.
                    outputStream.flush();
                }
            } catch (Exception ignored) {
            }

            try {
                socket.close();
            } catch (Exception ignored) {
            }
        }
    }
}
