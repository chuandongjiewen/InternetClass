package RMI3;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.sun.xml.internal.bind.v2.runtime.Name;

public class IndexServer {

	public static void main(String[] args){
		
		try {
			RMIServerService rmiServerService1 = new RMIServerServiceImpl();
			LocateRegistry.createRegistry(1099);
			Naming.bind("RMIService3", rmiServerService1);			
			System.out.println("服务器绑定了1个对象");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
