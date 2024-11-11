package p04_network_interfaces;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Comparator;
import java.util.Enumeration;

final class NetworkInterfaces {

	// https://docs.oracle.com/javase/tutorial/networking/nifs/listing.html
	
	public static void main(String[] args) {

		// Lists all network interfaces

		try {
			// Classic enumeration variant
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				System.out.println(ni);
				System.out.println("\t" + ni.getName() + "\t" + ni.getDisplayName() + "\t" + ni.getIndex());

				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements())
					System.out.println("\t" + addresses.nextElement());
			}

			System.out.println("--- \"loops are evil\" ---");

			// Stream<T> variant
			NetworkInterface.networkInterfaces()
					.sorted(Comparator.comparingInt(NetworkInterface::getIndex))
					.map(ni ->
							ni.toString() + "\n\t"
							+ ni.getName() + "\t" + ni.getDisplayName() + "\t" + ni.getIndex() + "\n\t"
							+ ni.inetAddresses()
									.map(InetAddress::toString)
									.reduce((acc, ip) -> acc + "\n\t" + ip)
									.orElse("?")
					)
					.forEach(System.out::println)
					;
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
	}

}
