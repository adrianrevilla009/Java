package dataAccess;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Offer;
import domain.RuralHouse;
import exceptions.OverlappingOfferExists;

public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c;

	public DataAccess(boolean initializeMode)  {
		
		c=ConfigXML.getInstance();
		
		System.out.println("Creating DB4oManager instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode)
			fileName=fileName+";drop";
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
	}

	
	public void initializeDB(){
		
		
		
		
		
		db.getTransaction().begin();
		try {
			RuralHouse rh1=new RuralHouse(1, "Ezkioko etxea","Ezkio");
			RuralHouse rh2=new RuralHouse(2, "Etxetxikia","Iru�a");
			RuralHouse rh3=new RuralHouse(3, "Udaletxea","Bilbo");
			RuralHouse rh4=new RuralHouse(4, "Gaztetxea","Renteria");
	        
			db.persist(rh1);
			db.persist(rh2);
			db.persist(rh3);
			db.persist(rh4);
		 
		 
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) {
		System.out.println(">> DataAccess: createOffer=> ruralHouse= "+ruralHouse+" firstDay= "+firstDay+" lastDay="+lastDay+" price="+price);

		try {
			RuralHouse rh = db.find(RuralHouse.class, ruralHouse.getHouseNumber());

			db.getTransaction().begin();
			Offer o = rh.createOffer(firstDay, lastDay, price);
			db.persist(o);
			db.getTransaction().commit();
			return o;
		}
		catch (Exception e){
			System.out.println("Offer not created: "+e.toString());
			return null;
		}
	}
	
	
	public Vector<RuralHouse> getAllRuralHouses() {
		System.out.println(">> DataAccess: getAllRuralHouses");
		Vector<RuralHouse> res = new Vector<RuralHouse>();

		TypedQuery<RuralHouse> query = db.createQuery("SELECT c FROM RuralHouse c", RuralHouse.class);
		List<RuralHouse> results = query.getResultList();

		Iterator<RuralHouse> itr = results.iterator();

		while (itr.hasNext()){
			res.add(itr.next());
		}

		return res;
	}
	
	 public Vector<Offer> getOffers( RuralHouse rh, Date firstDay,  Date lastDay) {
		 System.out.println(">> DataAccess: getOffers");
			Vector<Offer> res=new Vector<Offer>();
			RuralHouse rhn = db.find(RuralHouse.class, rh.getHouseNumber());
			res = rhn.getOffers(firstDay,lastDay);
			return res;
	 }
	 
	public boolean existsOverlappingOffer(RuralHouse rh,Date firstDay, Date lastDay) throws  OverlappingOfferExists{
		 try {

			RuralHouse rhn = db.find(RuralHouse.class, rh.getHouseNumber());
			if (rhn.overlapsWith(firstDay, lastDay)!=null) return true;
			else return false; 
	     } catch (Exception e) {
	    	 System.out.println("Error: "+e.toString());
			 return true;	    
		   }
	}

	

	public boolean createBook( Offer off, String telephoneNumber) {
		 System.out.println(">> DataAccess: createBook");

		Offer offer = db.find(Offer.class, off.getOfferNumber());
		boolean booked=false;
		if (offer.getReservedPhone()==null){
			db.getTransaction().begin();
			offer.setReservedPhone(telephoneNumber);
			db.getTransaction().commit();
			booked=true;
		}
		return booked;
	}
	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
}
