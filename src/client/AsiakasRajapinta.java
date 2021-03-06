package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AsiakasRajapinta extends Remote{
	
	/**
	 * Metodi vastaanottaa viestin?
	 * @param msg
	 * @throws RemoteException
	 */
	public void vastaanotaViesti(String msg) throws RemoteException;
	
	/**
	 * Paikallaolon selvittämiseen
	 * @param msg
	 * @return 
	 * @throws RemoteException
	 */
	public int ping() throws RemoteException;
	/**
	 * Pelin tuloksien vastaanottaminen
	 * @param tulokset
	 * @throws RemoteException
	 */
	public void vastaanotaTulokset(String[] tulokset) throws RemoteException;

}
