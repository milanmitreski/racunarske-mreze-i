package p03_host_lookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

final class HostLookup {

	public static void main(String[] args) {

		if (args.length > 0) {
			for (String arg : args)
				System.out.println(lookup(arg));
		} else {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter names and IP addresses. Enter \"exit\" to quit.");

			try {
				while (true) {
					String host = in.readLine();
					if (host.equalsIgnoreCase("exit") || host.equalsIgnoreCase("quit"))
						break;
					System.out.println(lookup(host));
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}


	private static String lookup(String host) {
		InetAddress node;

		try {
			node = InetAddress.getByName(host);
		} catch (UnknownHostException ex) {
			return "Cannot find host " + host;
		}

		if (isHostname(host)) {
			return node.getHostAddress();
		} else {
			return node.getHostName();
		}
	}

	private static boolean isHostname(String host) {
		// IPv6 address? Simple quick check, should be done more robustly
		if (host.indexOf(':') != -1)
			return false;

		// To determine if this is a host name or an IPv4 address, we
		// test the number of parts separated by a period - if there
		// are 4 parts, we still aren't sure if it is a hostname or
		// an address, but if it doesn't have 4 parts then it is a
		// hostname
		if (host.split("\\.").length != 4)
			return true;

		// IPv4 consists only of digits and dots
		return host.chars()
				.anyMatch(c -> !Character.isDigit(c) && c != '.');

		// https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
	}

}
