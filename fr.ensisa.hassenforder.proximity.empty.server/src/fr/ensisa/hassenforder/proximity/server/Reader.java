package fr.ensisa.hassenforder.proximity.server;


import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.network.Protocol;
import fr.ensisa.hassenforder.proximity.model.Mode;

public class Reader extends BasicAbstractReader {

	public Reader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case 0 :
			break;
		case Protocol.CONNECT_QUERY :
			break;
		}
	}

	public String connectQuery() {
		return this.readString();
	}
	
}
