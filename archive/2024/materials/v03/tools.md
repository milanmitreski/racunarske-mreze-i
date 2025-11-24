### ping

```
ping uses the ICMP protocol's mandatory ECHO_REQUEST datagram to elicit an ICMP 
ECHO_RESPONSE from a host or gateway. ECHO_REQUEST datagrams ("pings") have an 
IP and ICMP header, followed by a struct timeval and then an arbitrary number of 
"pad" bytes used to fill out the packet. ping works with both IPv4 and IPv6. 
Using only one of them explicitly can be enforced by specifying -4 or -6.
```

Examples:
```console
user@pc:~/Desktop# ping google.com
PING google.com (216.58.201.110) 56(84) bytes of data.
64 bytes from prg03s02-in-f110.1e100.net (216.58.201.110): icmp_seq=2 ttl=128 time=29.8 ms
64 bytes from prg03s02-in-f110.1e100.net (216.58.201.110): icmp_seq=3 ttl=128 time=26.7 ms
64 bytes from prg03s02-in-f110.1e100.net (216.58.201.110): icmp_seq=4 ttl=128 time=29.8 ms
64 bytes from prg03s02-in-f110.1e100.net (216.58.201.110): icmp_seq=5 ttl=128 time=27.0 ms
64 bytes from prg03s02-in-f110.1e100.net (216.58.201.110): icmp_seq=6 ttl=128 time=32.0 ms
64 bytes from prg03s02-in-f110.1e100.net (216.58.201.110): icmp_seq=7 ttl=128 time=30.3 ms
^C
--- google.com ping statistics ---
7 packets transmitted, 6 received, 14% packet loss, time 6015ms
rtt min/avg/max/mdev = 26.726/29.324/32.058/1.867 ms
```

### traceroute / tracepath

```
traceroute will show the route of a packet. It attempts to list the series of hosts 
through which your packets travel on their way to a given destination. Also have a 
look at xtraceroute (one of several graphical equivalents of this program). 
traceroute is a network troubleshooting utility which shows number of hops taken to 
reach destination also determine packets traveling path. Below we are tracing route 
to global DNS server IP Address and able to reach destination also shows path of 
that packet is traveling.
```

Examples:
```console
user@pc:~# traceroute -I google.com
traceroute to google.com (216.58.198.78), 128 hops max, 72 byte packets
1  52.93.7.1 (52.93.7.1)  6.361 ms  6.229 ms  6.106 ms
2  72.14.215.85 (72.14.215.85)  5.939 ms  5.460 ms  5.914 ms
3  209.85.252.198 (209.85.252.198)  6.012 ms  5.694 ms  5.761 ms
4  64.233.174.27 (64.233.174.27)  5.079 ms  4.776 ms  4.662 ms
5  dub08s02-in-f78.1e100.net (216.58.198.78)  6.650 ms  5.509 ms  5.596 ms
```

### dig

```
dig is a flexible tool for interrogating DNS name servers. It performs 
DNS lookups and displays the answers that are returned from the name server(s) that 
were queried. Most DNS administrators use dig to troubleshoot DNS problems because 
of its flexibility, ease of use and clarity of output. Other lookup tools tend to 
have less functionality than dig.
```

Examples:
```console
user@pc:~# dig amazon.com

; <<>> DiG 9.8.3-P1 <<>> amazon.com
;; global options: +cmd
;; Got answer:
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 30832
;; flags: qr rd ra; QUERY: 1, ANSWER: 6, AUTHORITY: 0, ADDITIONAL: 0

;; QUESTION SECTION:
;amazon.com.			IN	A

;; ANSWER SECTION:
amazon.com.		17	IN	A	54.239.25.200
amazon.com.		17	IN	A	54.239.25.208
amazon.com.		17	IN	A	54.239.26.128
amazon.com.		17	IN	A	54.239.17.6
amazon.com.		17	IN	A	54.239.17.7
amazon.com.		17	IN	A	54.239.25.192

;; Query time: 3 msec
;; SERVER: 10.78.97.142#53(10.78.97.142)
;; WHEN: Tue May 14 13:56:22 2019
;; MSG SIZE  rcvd: 124
```

### nslookup

```
nslookup is a program to query Internet domain name servers. nslookup has two modes: 
interactive and non-interactive. Interactive mode allows the user to query name 
servers for information about various hosts and domains or to print a list of hosts
in a domain. Non-interactive mode is used to print just the name and requested 
information for a host or domain.
```

Examples:
```console
user@pc:~# nslookup amazon.com
Server:		10.78.97.142
Address:	10.78.97.142#53

Non-authoritative answer:
Name:	amazon.com
Address: 54.239.25.192
Name:	amazon.com
Address: 54.239.25.200
Name:	amazon.com
Address: 54.239.25.208
Name:	amazon.com
Address: 54.239.26.128
Name:	amazon.com
Address: 54.239.17.6
Name:	amazon.com
Address: 54.239.17.7
```

### host

```
host is a simple utility for performing DNS lookups. It is normally used to convert 
names to IP addresses and vice versa.
```

Examples:
```console
user@pc:~# host amazon.com
amazon.com has address 54.239.17.6
amazon.com has address 54.239.17.7
amazon.com has address 54.239.25.192
amazon.com has address 54.239.25.200
amazon.com has address 54.239.25.208
amazon.com has address 54.239.26.128
amazon.com mail is handled by 10 amazon-smtp.amazon.com.
```

### hostname

```
hostname is used to display the system's DNS name, and to  display or set its 
hostname or NIS domain name.
```

### arp

```
ARP (Address Resolution Protocol) is useful to view / add the contents of the 
kernel's ARP tables. To see default table use the command as.
```

### ip

```
Show / Manipulate routing, network devices, interfaces and tunnels.
```

```console
ip addr
Shows addresses assigned to all network interfaces.

ip neigh
Shows the current neighbour table in kernel.

ip link set x up
Bring up interface x.

ip link set x down
Bring down interface x.

ip route
Show table routes.
```

### ifconfig

```
ifconfig is used to configure the kernel-resident network interfaces. It is 
used at boot time to set up interfaces as necessary. After that, it is usually 
only needed when debugging or when system tuning is needed.

In addition to activating and deactivating interfaces with the "up" and "down" 
settings, this command is necessary for setting an interface's address information 
if you don't have the ifcfg script.
  - ifup   - Use ifup device-name to bring an interface up by following a script 
             (which will contain your default networking settings). Simply type 
             ifup and you will get help on using the script.
  - ifdown - Use ifdown device-name to bring an interface down using a script 
             (which will contain your default network settings). Simply type ifdown 
             and you will get help on using the script.
  - ifcfg  - Use ifcfg to configure a particular interface. Simply type ifcfg 
             to get help on using this script.
```