package RMI2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerServiceImpl2 extends UnicastRemoteObject implements RMIServerService2{

	protected RMIServerServiceImpl2() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String invertString(String msg) throws RemoteException {
		StringBuilder builder = new StringBuilder(msg);
		builder.reverse();
		return builder.toString();
	}

}
