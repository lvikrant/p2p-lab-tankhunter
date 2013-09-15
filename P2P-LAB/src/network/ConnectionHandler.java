package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import model.NetworkObject;
import model.NetworkObject.dataType;
import model.NetworkTarget;

public class ConnectionHandler extends Thread {

	ConnectionManager conManger;
	Socket baseSocket;
	public String targetIP = null;
	public int targetOpenPort = -1;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	AtomicBoolean running = new AtomicBoolean();

	private Queue<NetworkObject> recived = new ConcurrentLinkedQueue<NetworkObject>();
	private Queue<NetworkObject> toSend = new ConcurrentLinkedQueue<NetworkObject>();

	/**
	 * Constructor to set Port number and IP address
	 * 
	 * @param s
	 *            - open port number
	 * @param manager
	 *            instance of ConnectionManager class
	 */
	public ConnectionHandler(Socket s, ConnectionManager manager) {
		conManger = manager;
		running.set(true);
		baseSocket = s;
		targetIP = s.getInetAddress().getHostAddress();
	}

	/**
	 * Method to initialize Input and Output Streams
	 */
	private void init() {
		try {
			out = new ObjectOutputStream(baseSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(baseSocket.getInputStream());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The Data to be send is added to toSend Queue
	 * 
	 * @param data
	 *            the serialized data to be send
	 */
	public void send(NetworkObject data) {
		toSend.add(data);
		// new Thread(this).start();
	}

	/**
	 * Method to return received data
	 * 
	 * @return data from received queue
	 */
	public NetworkObject getNewObject() {

		synchronized (recived) {
			return recived.poll();
		}

	}

	/**
	 * Method to close the connection
	 */
	public void StopAndClose() {
		running.set(false);
	}

	public void run() {
		NetworkObject data;

		System.out.println("Starting ConnectionHandler");
		while (running.get()) {
			// System.out.println("Hello3");
			// System.out.println(baseSocket + "\n"+targetIP);
			try {

				while (baseSocket.getInputStream().available() > 0) {
					if (in == null)
						init();

					// System.out.println("Hello4");
					data = (NetworkObject) (in.readObject());
					data.target.IP = baseSocket.getInetAddress()
							.getHostAddress();
					data.reciver.PORT = baseSocket.getLocalPort();

					targetOpenPort = data.openPort;

					synchronized (recived) {
						System.out.println("Recieved: " + data.type);
						data.reciver.PORT = this.baseSocket.getLocalPort();
						recived.add(data);
					}

					if (data.type == dataType.Ping) {
						NetworkObject blub = new NetworkObject();
						blub.type = dataType.Pong;
						synchronized (toSend) {
							toSend.add(blub);
						}

					}

				}
				synchronized (toSend) {
					while (!toSend.isEmpty()) {
						if (out == null)
							init();

						data = toSend.poll();
						System.out.println("Send: " + data.type);
						data.target = new NetworkTarget("",
								conManger.myServerSocket.getLocalPort());
						data.reciver = new NetworkTarget(this.targetIP,
								this.targetOpenPort);
						out.writeObject(data);
						out.flush();

					}
				}

				Thread.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		try {
			in.close();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
