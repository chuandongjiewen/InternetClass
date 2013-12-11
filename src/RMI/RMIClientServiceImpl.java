package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientServiceImpl extends UnicastRemoteObject implements RMIClientService{

	protected RMIClientServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getYourName() throws RemoteException {
		// TODO Auto-generated method stub
		return "20111003632 Òü´¨¶«";
	}

}
