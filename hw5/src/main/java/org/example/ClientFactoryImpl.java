package org.example;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientFactoryImpl implements ClientFactory{
    protected final Socket socket;

    private final InetSocketAddress socketAddress;

    public ClientFactoryImpl(String ip, int port) throws IOException {
        socketAddress = new InetSocketAddress(ip, port);
        socket = new Socket(ip, port);
    }

    public void closeConnection() throws IOException {
        socket.close();
    }

    // class must be an interface
    @Override
    @SuppressWarnings("unchecked")
    public <T> T newClient(Class<T> client) throws IOException {
        ArrayList<Class<?>> clientsInterfaces = new ArrayList<>(List.of(client.getInterfaces()));
        clientsInterfaces.add(client);
        return (T) Proxy.newProxyInstance(
                        client.getClassLoader(),
                        new Class[]{client},
                        new ClientProxy()
                );
    }

    protected class ClientProxy implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws IOException {
            Object result = null;

            try (Socket socket = new Socket(socketAddress.getAddress(), socketAddress.getPort());
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
                result = inputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            return result;
        }
    }
}
