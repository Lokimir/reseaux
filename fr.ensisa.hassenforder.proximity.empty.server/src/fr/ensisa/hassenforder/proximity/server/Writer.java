package fr.ensisa.hassenforder.proximity.server;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	}

}
