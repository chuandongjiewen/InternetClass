package RMI2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerServiceImpl1 extends UnicastRemoteObject implements RMIServerService1{
	protected RMIServerServiceImpl1() throws RemoteException {
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
