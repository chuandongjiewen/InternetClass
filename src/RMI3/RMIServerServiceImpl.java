package RMI3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServerServiceImpl extends UnicastRemoteObject implements RMIServerService{

	private ArrayList<RMIClientView> onLine = new ArrayList<RMIClientView>(50);
	
	protected RMIServerServiceImpl() throws RemoteException {
		super();
		
	}

	@Override
	public String echo(String msg) throws RemoteException {
		String result = "��ʦ������echo��"+msg;
		sendMessageToServer(result);
		return result;
	}

	@Override
	public String addClientToOnLine(RMIClientView client, String name)
			throws RemoteException {
		onLine.add(client);
		System.out.println(name + "������");
		return "��ʦ������addClientToOnLine:"+name;
	}

	@Override
	public String delClientFromOnLine(RMIClientView client)
			throws RemoteException {
		if (onLine.contains(client)) {
			onLine.remove(client);
		}
		System.out.println("�����˳���");
		return "��ʦ������delClientFromOnLine��";
	}

	@Override
	public void sendMessageToServer(String msg) throws RemoteException {
		for (RMIClientView client : onLine) {
			client.showMessageToClient(msg);
		}
	}

}
