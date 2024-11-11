package p02_version;

import java.net.InetAddress;
import java.net.UnknownHostException;

final class InetAddressVersion {
	
	public static void main(String[] args) {
		try {
			InetAddress addressv4 = InetAddress.getByName("google.com");
			System.out.println(addressv4.getHostAddress());
			printAddress(addressv4.getAddress());
			System.out.println("IPv" + getVersion(addressv4));
			System.out.println();
			InetAddress addressv6 = InetAddress.getByName("ipv6.google.com");
			System.out.println(addressv6.getHostAddress());
			printAddress(addressv6.getAddress());
			System.out.println("IPv" + getVersion(addressv6));
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}

		/* Sample output
			216.58.206.14
			IP address bytes: 216 58 206 14
			IPv4

			2a00:1450:4001:806:0:0:0:200e
			IP address bytes: 42 0 20 80 64 1 8 6 0 0 0 0 0 0 32 14
			IPv6
		 */
	}

	private static int getVersion(InetAddress addr) {
		byte[] address = addr.getAddress();

		// IPv4 has 4 bytes, IPv6 has 16 bytes
		switch (address.length) {
			case 4: return 4;
			case 16: return 6;
			default: return -1;
		}

		// Option 2, check the type of `addr`
		// Slower than first option, due to reflection.
		// Not as robust, because there are more classes
		// extending InetAddress, such as InetSocketAddress!
		// Do this only if you know that `addr` will be of
		// either Inet4 or Inet6 type!
		/*
		if (addr instanceof Inet4Address)
			return 4;
		else if (addr instanceof Inet6Address)
			return 6;
		else
			return -1;
		 */
	}

	private static void printAddress(byte[] address) {
		if (address.length != 4)
			throw new IllegalArgumentException("We only want to show how to interpret IPv4 addresses.");

		System.out.print("IP address bytes: ");
		for (byte b : address) {
			// InetAddress bytes need to be shifted by 256 if they are negative
			int unsignedByte = b < 0 ? b + 256 : b;
			System.out.print(unsignedByte + " ");
		}
		System.out.println();
	}

}
