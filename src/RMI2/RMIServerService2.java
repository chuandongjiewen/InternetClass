package RMI2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerService2 extends Remote{

	public String invertString(String msg) throws RemoteException;
}
