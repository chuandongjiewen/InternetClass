package RMI3;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientView extends Remote{
	public void showMessageToClient(String msg) throws RemoteException;
}
