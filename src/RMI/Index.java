package RMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.sun.xml.internal.bind.v2.runtime.Name;

public class Index {

	public static void main(String[] args){
		
		try {
			RMIClientService rmiClientService = new RMIClientServiceImpl();
			LocateRegistry.createRegistry(1099);
			Naming.bind("RMIClientService", rmiClientService);
			
//			RMIServerService rmiServerService = new RMIServerServiceImpl();
//			LocateRegistry.createRegistry(1099);
//			Naming.bind("RMIServerService", rmiServerService);
			
//			String url = "rmi://192.168.233.15:1099/";//rmi://222.201.101.15:1099/
//			RMIServerService serverService = (RMIServerService)Naming.lookup(url + "RMIServerService");
//			String msg = serverService.echo("20111003632 Òü´¨¶«");
//			System.out.println(msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
