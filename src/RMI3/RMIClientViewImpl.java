package RMI3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientViewImpl extends UnicastRemoteObject implements RMIClientView{

	private RMIClientJFrame jFrame; 
	
	protected RMIClientViewImpl(RMIClientJFrame jFrame) throws RemoteException {
		super();
		this.jFrame = jFrame;
	}

	@Override
	public void showMessageToClient(String msg) throws RemoteException {
		jFrame.appendMessageToArea(msg + "\n");
	}

}
