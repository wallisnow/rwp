package com.congxing.rulemgt.ruleset.socket;

import java.net.Socket;


public interface SocketPool{
	public Socket getSocket() throws Exception;
	public void free(Socket socket);
	public void close();
}
