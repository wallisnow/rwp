package com.congxing.rulemgt.ruleset.socket;

import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class SocketPoolImpl implements SocketPool, Runnable {

	private int initSize;
	private int maxSize;
	private String ipAddress;
	private int port;
	private int timeOut;
	private Vector<Socket> busiVector;
	private Vector<Socket> freeVector;
	private boolean waitIfBusy = true;// 忙时等待

	public SocketPoolImpl(String ipAddress, int port, int initSize, int maxSize, int timeOut, boolean waitIfBusy) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.initSize = initSize;
		this.maxSize = maxSize;
		this.timeOut = timeOut;
		if (initSize > maxSize){
			initSize = maxSize;
		}
		freeVector = new Vector<Socket>();
		busiVector = new Vector<Socket>();

		for (int i = 0; i < initSize && initSize <= maxSize; i++) {
			freeVector.addElement(makeNewSocket());
		}
	}

	public synchronized Socket getSocket() throws Exception {
		if (!freeVector.isEmpty()) {
			int lastIndex = freeVector.size() - 1;
			Socket s = (Socket) freeVector.lastElement();
			freeVector.removeElementAt(lastIndex);
			if (s.isClosed()) {
				notify();
				return getSocket();
			} else {
				busiVector.add(s);
				return s;
			}
		} else {
			if (this.getTotalSize() < maxSize) {
				makeBackgroundSocket();// 建立后台线程Socket连接
			} else if (!waitIfBusy) {// 忙时不等待
				throw new Exception("Socket limit reached");
			}

			try {
				wait();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return getSocket();
		}
	}

	private void makeBackgroundSocket() {
		try {
			Thread thread = new Thread(this);
			thread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		}
	}

	public void run() {
		Socket s = null;
		try {
			s = this.makeNewSocket();
		} catch (Exception ex) {
		}
		synchronized (this) {
			if (s != null) {
				freeVector.addElement(s);
				notifyAll();
			}
		}
	}

	public synchronized int getTotalSize() {
		return this.freeVector.size() + this.busiVector.size();
	}

	private Socket makeNewSocket() {
		try {
			Socket s = new Socket(ipAddress, port);
			s.setSoTimeout(timeOut);
			s.setSendBufferSize(1024);
			return s;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public synchronized void free(Socket socket) {
		if (null != socket) {
			busiVector.remove(socket);
			freeVector.add(socket);
		}
		notifyAll();
	}

	public synchronized void close() {
		this.closeVector(freeVector);
		this.closeVector(busiVector);
		freeVector = new Vector<Socket>();
		busiVector = new Vector<Socket>();
	}

	private void closeVector(Vector<Socket> v) {
		try {
			if (null != v && !v.isEmpty()) {
				for (Iterator<Socket> iter = v.iterator(); iter.hasNext();) {
					Socket s = (Socket) iter.next();
					if (!s.isClosed())
						s.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setInitSize(int initSize) {
		this.initSize = initSize;
	}

	public int getInitSize() {
		return initSize;
	}

}
