package fr.ensisa.hassenforder.proximity.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.hassenforder.network.Protocol;
import fr.ensisa.hassenforder.proximity.model.User;
import fr.ensisa.hassenforder.proximity.model.Mode;

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
			case Protocol.MOVE_QUERY :
				moveQuery(reader, writer);
				break;
			case Protocol.RADIUS_QUERY :
				radiusQuery(reader, writer);
				break;
			case Protocol.REFRESH_QUERY :
				refreshQuery(reader, writer);
				break;
			case Protocol.MODE_QUERY :
				changeModeQuery(reader, writer);
				break;
			case Protocol.PREF_LEVEL_QUERY :
				changePrefLevelQuery(reader, writer);
				break;
			case Protocol.PREF_VISIBILITY_QUERY :
				changePrefVisibilityQuery(reader, writer);
				break;
			case Protocol.FIND_QUERY :
				findQuery(reader, writer);
				break;
			default: return false; // connection jammed
			}
			writer.send ();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private void findQuery(Reader reader, Writer writer) {
		// On récupère les infos
		String name = reader.getNameQuery();
		// On traite les infos
		List<User> list = this.document.doFind(name);
		
		// Réponse
		writer.findQuery(list);
		
	}

	private void changePrefVisibilityQuery(Reader reader, Writer writer) {
		// On récupère les infos
		String name = reader.getNameQuery();
		String preference_name = reader.getNameQuery();
		boolean value = reader.getBooleanQuery();
		
		// On traite les infos
		boolean result = this.document.doChangePreferenceVisibility(name, preference_name, value);
		
		// Réponse
		writer.booleanQuery(result);
	}

	private void changePrefLevelQuery(Reader reader, Writer writer) {
		// On récupère les infos
		String name = reader.getNameQuery();
		String preference_name = reader.getNameQuery();
		int value = reader.getIntQuery();
		
		// On traite les infos
		boolean result = this.document.doChangePreferenceLevel(name, preference_name, value);
		
		// Réponse
		writer.booleanQuery(result);
	}

	private void changeModeQuery(Reader reader, Writer writer) {
		// On récupère les infos
		String name = reader.getNameQuery();
		int ord = reader.getIntQuery();
		
		// On traite les infos
		Mode mode = Mode.values()[ord];
		boolean result = this.document.doChangeMode(name, mode);
		
		// Réponse
		writer.booleanQuery(result);
	}

	private void refreshQuery(Reader reader, Writer writer) {
		// On récupère les infos
		String name = reader.getNameQuery();
		
		// On traite les infos
		User u = this.document.doGetState(name);
		
		// Réponse
		writer.refreshQuery(u);
	}

	private void radiusQuery(Reader reader, Writer writer) {
		// Récupération des infos
		String name = reader.getNameQuery();
		int radius = reader.getIntQuery();
		
		
		// Traitement des infos
		boolean result = this.document.doChangeRadius(name, radius);
		
		// Réponse
		writer.booleanQuery(result);
	}

	private void moveQuery(Reader reader, Writer writer) {
		// Récuperation des infos
		String name = reader.getNameQuery();
		int x = reader.getIntQuery();
		int y = reader.getIntQuery();
		
		// Traitement
		boolean result = this.document.doMove(name, x, y);
		
		// Réponse
		writer.booleanQuery(result);
		
	}

	private void connectQuery(Reader reader, Writer writer) {
		// Lecture du nom et connexion
		String currentUser = reader.getNameQuery();
		User u = this.document.doConnect(currentUser);
		
		// On renvoie la connexion
		writer.connectQuery(u);
	}

}
