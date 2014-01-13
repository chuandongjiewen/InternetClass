package RMI;

import java.rmi.Naming;

public class IndexServer1 {
	public static void main(String[] args){
		
		try {
			
//			String url = "rmi://192.168.233.15:1099/";//rmi://222.201.101.15:1099/
//			RMIClientService clientService = (RMIClientService)Naming.lookup(url + "RMIClientService");
//			String msg = clientService.getYourName();
//			System.out.println(msg);
			
//			String url = "rmi://192.168.233.15:1099/";//rmi://222.201.101.15:1099/
			String url = "rmi://222.201.101.15:1099/";//rmi://222.201.101.15:1099/
			RMIServerService serverService = (RMIServerService)Naming.lookup(url + "RMIService");
			//String msg = serverService.echo("20111003632 Òü´¨¶«");
			String msg = serverService.echo("20111003632","Òü´¨¶«".getBytes());
			System.out.println(msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
