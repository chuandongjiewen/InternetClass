package RMI2;

import java.rmi.Naming;

public class IndexClient {
	public static void main(String[] args){
		
		try {
			
//			String url = "rmi://192.168.233.15:1099/";//rmi://222.201.101.15:1099/
			String url = "rmi://222.201.101.15:1099/";//rmi://222.201.101.15:1099/
			RMIServerService1 serverService1 = (RMIServerService1)Naming.lookup(url + "RMIService1");
			RMIServerService2 serverService2 = (RMIServerService2)Naming.lookup(url + "RMIService2");
			
			System.out.println(serverService1.echo("20111003632", "Òü´¨¶«".getBytes()));
			System.out.println(serverService2.invertString(serverService2.invertString("·´×ª×Ö·û´®£º20111003632 Òü´¨¶«")));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
