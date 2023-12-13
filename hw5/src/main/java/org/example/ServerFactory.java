package org.example;

import java.io.IOException;

public interface ServerFactory {
    void listen(int port, Object service) throws IOException;
}
