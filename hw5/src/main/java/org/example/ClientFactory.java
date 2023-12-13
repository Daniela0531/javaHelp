package org.example;

import java.io.IOException;

public interface ClientFactory {
    <T> T newClient(Class<T> client) throws IOException;
}
