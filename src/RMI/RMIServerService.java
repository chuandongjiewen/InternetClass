package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerService extends Remote{
	 public String echo(String msg) throws RemoteException;
	 public String echo(String yourNo, byte[] yourName) throws RemoteException;

}
