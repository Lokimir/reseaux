package fr.ensisa.hassenforder.proximity.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.hassenforder.network.Protocol;
import fr.ensisa.hassenforder.proximity.model.User;

public class SessionServer {

	private Socket connection;
	private Document document;
	
	public SessionServer (Document document, Socket connection) {
		this.document = document;
		this.connection = connection;
	}

	public boolean operate() {
		try {
			Writer writer = new Writer (connection.getOutputStream());
			Reader reader = new Reader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType ()) {
			case 0 : return false; // socket closed
			case Protocol.CONNECT_QUERY :
				connectQuery(reader, writer);
				break;
			default: return false; // connection jammed
			}
			writer.send ();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void connectQuery(Reader reader, Writer writer) {
		// Lecture du nom et connexion
		String currentUser = reader.connectQuery();
		User u = this.document.doConnect(currentUser);
		
		// On renvoie la connexion
		writer.connectQuery(u);
	}

}
