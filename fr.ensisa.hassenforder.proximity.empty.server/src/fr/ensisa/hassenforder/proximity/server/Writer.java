package fr.ensisa.hassenforder.proximity.server;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.network.Protocol;
import fr.ensisa.hassenforder.proximity.model.Preference;
import fr.ensisa.hassenforder.proximity.model.User;

public class Writer extends BasicAbstractWriter {

	public Writer(OutputStream outputStream) {
		super (outputStream);
	}

	public void connectQuery(User u) {
		if (u != null){
			// On envoie l'acquittement 
			this.writeInt(Protocol.QUERY_OK);
			this.sendUser(u);
			}
		else
			this.writeInt(Protocol.QUERY_ERROR);
			
	}

	public void booleanQuery(boolean result) {
		this.writeBoolean(result);
	}

	public void refreshQuery(User u) {
		this.sendUser(u);
	}

	
	private void sendUser(User u){
		// On envoie les données de l'utilisateur
		this.writeInt(u.getX());
		this.writeInt(u.getY());
		this.writeInt(u.getRadius());
		this.writeInt(u.getMode().ordinal());
		
		// On envoie le nombre de préférences
		this.writeInt(u.getPreferences().size());
		
		// On envoie les préférences
		for(Map.Entry<String, Preference> entry : u.getPreferences().entrySet()){
			Preference p = entry.getValue();
			
			this.writeString(entry.getKey());
			this.writeInt(p.getLevel());
			this.writeBoolean(p.isVisibility());
		}
	}

	public void findQuery(List<User> list) {
		int size = list.size();
		
		// On envoie le nombre d'utilisateur à récupérer
		this.writeInt(size);
		
		for(User u : list)
			sendUser(u);
	}
}
