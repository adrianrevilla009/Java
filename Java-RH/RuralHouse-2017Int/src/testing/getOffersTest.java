package testing;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.Test;

import businessLogic.FacadeImplementationWS;
import domain.Offer;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import junit.framework.Assert;
import junit.framework.TestCase;

public class getOffersTest extends TestCase{
	public FacadeImplementationWS a=new FacadeImplementationWS();
	public Vector<Offer> ofertas=new Vector<Offer>();
	
	//test correcto
	@Test
	public void testCorrecto() throws OverlappingOfferExists, BadDates {
		RuralHouse x=new RuralHouse(a.getAllRuralHouses().size(),"Casa 1","Donostia");
		a.createOffer(x,new Date(118,9,22), new Date(118,9,25), 50);
		Offer y=new Offer();
		y.setRuralHouse(x);
		y.setFirstDay(new Date(118,9,22));
		y.setLastDay(new Date(118,9,25));
		y.setPrice(50);
		ofertas.add(y);
		Vector<Offer> ofertas2=a.getOffers(x,new Date(118,9,22),new Date(118,9,25));
		Offer c=ofertas.firstElement();
		Offer b=ofertas2.firstElement();
		ofertas.clear();
		assertEquals(c,b);
		
	}
	
	//no existe la casa en la BD
	@Test
	public void testCasaNoExistente(){	
		try{
			RuralHouse x=new RuralHouse(100,"Casa 2","Vitoria");
			Vector<Offer> ofertas2=a.getOffers(x,new Date(118,9,22),new Date(118,9,25));
			fail();
		}catch(Exception e){
			assertTrue(true);
		}
	}

	
	//firstDay>lastDay
	@Test(expected = BadDates.class)
		public void testFechaValida(){
			RuralHouse x=new RuralHouse(a.getAllRuralHouses().size(),"Casa 1","Donostia");
			Vector<Offer> ofertas2=a.getOffers(x,new Date(118,9,25),new Date(118,9,22));	
		}
	//firstDay<diaActual
	@Test(expected = BadDates.class)
	public void testFechaValida2(){
		RuralHouse x=new RuralHouse(a.getAllRuralHouses().size(),"Casa 1","Donostia");
		Vector<Offer> ofertas2=a.getOffers(x,new Date(118,9,15),new Date(118,9,25));	
	}
	
	
	//no existe la oferta en la BD
	@Test
	public void testOfertaNoExistente(){
		RuralHouse x=new RuralHouse(a.getAllRuralHouses().size(),"Casa 3","Bilbao");
		x.offers.clear();
		Vector<Offer> ofertas2=a.getOffers(x,new Date(118,11,22),new Date(118,11,25));
		Vector<Offer> nulo=new Vector<Offer>();
		assertEquals(ofertas2,nulo);
		
	}
		
		
}
	
	
	


