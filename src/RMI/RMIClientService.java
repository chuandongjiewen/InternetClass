package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientService extends Remote{

	public String getYourName() throws RemoteException;
}
