package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerServiceImpl extends UnicastRemoteObject implements RMIServerService{
	protected RMIServerServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	public String echo(String msg)throws RemoteException{
		return "来自老师：" + msg;
	}
	public String echo(String yourNo,byte[] yourName) throws RemoteException{
		return "来自老师："+yourNo + new String(yourName);
	}
}
