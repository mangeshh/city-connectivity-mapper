package city.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mc.cache.CityConnectivityMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CityConnectivityMap.class)
public class CityConnectivityMapTest {
	
   @Autowired
   CityConnectivityMap cityConnectivityMap;
		   
   @Test
   public void testConnectivitySymentric(){
	   cityConnectivityMap.put("A", "B");
	   cityConnectivityMap.put("B", "C");
	   String ans = cityConnectivityMap.isConnectedQuickCheck("A","A");
	   assertEquals("yes", ans);
	   String ans1 = cityConnectivityMap.isConnectedQuickCheck("B","B");
	   assertEquals("yes", ans);
	   assertEquals("yes", ans1);
   }
   
   @Test
   public void testConnectivityTransitivity1(){
	   cityConnectivityMap.put("A", "B");
	   cityConnectivityMap.put("B", "C");
	   cityConnectivityMap.put("C", "D");
	   String ans = cityConnectivityMap.isConnectedQuickCheck("A","D");
	   System.out.println(ans);
	   assertEquals("yes", ans);
   }
   
   @Test
   public void testConnectivityTransitivity2(){
	   cityConnectivityMap.put("A", "B");
	   cityConnectivityMap.put("B", "C");
	   cityConnectivityMap.put("C", "D");
	   String ans = cityConnectivityMap.isConnectedQuickCheck("D","A");
	   assertEquals("yes", ans);
   }

   @Test
   public void testConnectivityTransitivity3(){
	   cityConnectivityMap.put("A", "B");
	   cityConnectivityMap.put("B", "C");
	   cityConnectivityMap.put("D", "C");
	   String ans = cityConnectivityMap.isConnectedQuickCheck("D","A");
	   assertEquals("yes", ans);
   }
   
   @Test
   public void testConnectivityTransitivity4(){
	   cityConnectivityMap.put("A", "B");
	   cityConnectivityMap.put("B", "C");
	   cityConnectivityMap.put("D", "C");
	   cityConnectivityMap.put("E", "D");
	   String ans = cityConnectivityMap.isConnectedQuickCheck("A","E");
	   assertEquals("yes", ans);
   }
   
   @Test
   public void testConnectivityReflective(){
	   cityConnectivityMap.put("A", "B");
 	   String ans = cityConnectivityMap.isConnectedQuickCheck("B","A");
	   assertEquals("yes", ans);
   }
   
   @Test
   public void testConnectivityReflective1(){
	   cityConnectivityMap.put("A", "B");
 	   String ans = cityConnectivityMap.isConnectedQuickCheck("B","A1");
	   assertEquals("no", ans);
   }
   
   @Test
   public void testConnectivityCircle1(){
	   cityConnectivityMap.put("A", "B");
	   cityConnectivityMap.put("B", "C");
 	   String ans = cityConnectivityMap.isConnectedQuickCheck("C","A");
	   assertEquals("yes", ans);
   }
   
   @Test
   public void testConnectivitySimple1(){
	   cityConnectivityMap.put("A", "B");
 	   String ans = cityConnectivityMap.isConnectedQuickCheck("A","B");
	   assertEquals("yes", ans);
	   String ans1 = cityConnectivityMap.isConnectedQuickCheck("B","A");
	   assertEquals("yes", ans1);
   }
   
   @Test
   public void testConnectivityLongRelation(){
	    cityConnectivityMap.put("A", "B");
	    cityConnectivityMap.put("B", "F");
	    cityConnectivityMap.put("C", "D");
	    cityConnectivityMap.put("D", "E");
	    cityConnectivityMap.put("E", "F");
	    cityConnectivityMap.put("F", "G");
	    cityConnectivityMap.put("G", "F");
	    cityConnectivityMap.put("G", "I");

	    cityConnectivityMap.put("Boston", "New York");
	    cityConnectivityMap.put("Philadelphia", "Newark");
	    cityConnectivityMap.put("Newark", "Boston");
	    cityConnectivityMap.put("Trenton", "Albany");

		assertEquals("yes",cityConnectivityMap.isConnectedQuickCheck("G", "I"));
		assertEquals("yes",cityConnectivityMap.isConnectedQuickCheck("A", "I"));
		assertEquals("yes",cityConnectivityMap.isConnectedQuickCheck("A", "D"));
		assertEquals("yes",cityConnectivityMap.isConnectedQuickCheck("D", "A"));

		assertEquals("yes",cityConnectivityMap.isConnectedQuickCheck("I", "A"));
		assertEquals("no",cityConnectivityMap.isConnectedQuickCheck("New York1", "Newark"));
		assertEquals("yes",cityConnectivityMap.isConnectedQuickCheck("New York", "Newark"));
		assertEquals("yes",cityConnectivityMap.isConnectedQuickCheck("Boston", "Newark"));
   }	   
   
   
}
