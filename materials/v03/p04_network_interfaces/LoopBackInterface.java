package p04_network_interfaces;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

final class LoopBackInterface {

	// The loopback device is a special, virtual network interface that
	// your computer uses to communicate with itself. It is used mainly
	// for diagnostics and troubleshooting, and to connect to servers
	// running on the local machine.
	
	public static void main(String[] args) {
		try {
			InetAddress local = InetAddress.getLoopbackAddress();
			// Line below has the same effect if 127.0.0.1 is loopback address
			// InetAddress local = InetAddress.getByName("127.0.0.1");

			// NetworkInterface class represents a local IP address.
			// This can either be a physical interface such as an
			// additional Ethernet card (common on firewalls and
			// routers) or it can be a virtual interface bound to
			// the same physical hardware as the machine’s other
			// IP addresses. The Network Interface class provides
			// methods to enumerate all the local addresses,
			// regardless of interface, and to create InetAddress
			// objects from them. These InetAddress objects can
			// then be used to create sockets, server sockets,
			// and so forth (we will explain these later).
			NetworkInterface ni = NetworkInterface.getByInetAddress(local);
			
			if (ni == null) {
				System.err.println("No local loopback address.");
				return;
			}

			System.out.println(ni);
			System.out.printf("%s\t%s\n%s\n", ni.getName(), ni.getDisplayName(), ni.getIndex());
		} catch (SocketException ex) {
			System.err.println("Could not list sockets.");
		}
	}

}
