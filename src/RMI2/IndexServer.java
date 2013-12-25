package RMI2;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.sun.xml.internal.bind.v2.runtime.Name;

public class IndexServer {

	public static void main(String[] args){
		
		try {
			RMIServerService1 rmiServerService1 = new RMIServerServiceImpl1();
			RMIServerService2 rmiServerService2 = new RMIServerServiceImpl2();
			LocateRegistry.createRegistry(1099);
			Naming.bind("RMIService1", rmiServerService1);
			Naming.bind("RMIService2", rmiServerService2);
			
//			String url = "rmi://192.168.233.15:1099/";//rmi://222.201.101.15:1099/
//			RMIServerService serverService = (RMIServerService)Naming.lookup(url + "RMIServerService");
//			String msg = serverService.echo("20111003632 尹川东");
//			System.out.println(msg);
			
			System.out.println("服务器绑定了两个对象");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
