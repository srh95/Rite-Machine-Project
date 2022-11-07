import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test class for the GearMeasurement class
 */

public class GearTester {
    
    GearMeasurement spur = new GearMeasurement(55,3,20,0.004);  
        
    GearMeasurement helical = new GearMeasurement(14,18.3850656,14.50,40,0.002);  
    
    /* Tests an example for a spur gear */
    @Test
    public void testExample1(){
        assertEquals("Tests the getter for the number of teeth", "55", Integer.toString(spur.getNumTeeth()));
        assertEquals("Tests the getter for the diametral pitch", "3.0", Double.toString(spur.getPitch()));
        assertEquals("Tests the getter for the pressure angle", "0.3490658503988659", Double.toString(spur.getPressureAngle()));
        assertEquals("Tests the getter for the pitch diameter D", "18.333333333333332", Double.toString(spur.getD()));
        assertEquals("Tests the getter for the standard tooth thickness", "0.5235987755982988", Double.toString(spur.getTeeth()));
        assertEquals("Tests the value of dB", "17.227698047741654", Double.toString(spur.getDB()));
        assertEquals("Tests the getter for the involute of phi", "0.014904383867336446", Double.toString(spur.getInvPhi()));
        assertEquals("Tests the value of G", "0.576", Double.toString(spur.getG()));
        assertEquals("Tests the value of the involute of phiW", "0.019778981395399707", Double.toString(spur.getInvPhiW()));
        assertEquals("Tests the value of phi", "0.3823041018727549", Double.toString(spur.getPhi()));
        assertEquals("Tests the value of phiW", "0.38228071775667394", Double.toString(spur.getPhiW()));
        assertEquals("Tests the value of dW", "18.568006255963784", Double.toString(spur.getDW()));
        assertEquals("Tests the value of m for an odd tooth gear", "19.136434089846375", Double.toString(spur.getM()));
        assertEquals("Tests the value of kM", "2.5190297445139116", Double.toString(spur.getKM()));
        assertEquals("Tests the value of change in M", "0.010076118978055647", Double.toString(spur.getChangeM()));
        assertEquals("Tests the value of the calculation method", "19.12635797086832", Double.toString(spur.spurCalc()));
    }
    
    /* Tests an example for a heical gear */
    @Test
    public void testExample2(){
       assertEquals("Tests the value of pN", "23.999998649092124", Double.toString(helical.getPN()));
       assertEquals("Tests the value of tN", "0.06544985063381731", Double.toString(helical.getTN()));
       assertEquals("Tests the value of tanPhi", "0.3376012797676846", Double.toString(helical.getTanPhi()));
       assertEquals("Tests the value of newPhi", "0.32558677550878634", Double.toString(helical.getNewPhi()));
       assertEquals("Tests the value of dB", "0.7214815056158727", Double.toString(helical.getHDB()));
       assertEquals("Tests the value of the helx angle at base of circle", "0.6716945343604036", Double.toString(helical.getHelixAngBase()));
       assertEquals("Tests the value of G", "0.07200000405272386", Double.toString(helical.getHelicG()));
       assertEquals("Tests the value of gPrim", "0.09198125144995774", Double.toString(helical.getGPrim()));
       assertEquals("Tests the value of invPhiW", "0.02730417659337711", Double.toString(helical.getInvPhiW()));
       assertEquals("Tests the value of phi", "0.42370563334404454", Double.toString(helical.getHPhi()));
       assertEquals("Tests the value of phiW", "0.4236330566736443", Double.toString(helical.getHPhiW()));
       assertEquals("Tests the value of dW", "0.7914439642865178", Double.toString(helical.getHDW()));
       assertEquals("Tests the value of m", "0.8634439683392416", Double.toString(helical.getHM()));
       assertEquals("Tests the value of k", "2.3048423417905024", Double.toString(helical.getK()));
       assertEquals("Tests the value of chngM", "0.006017515987470002", Double.toString(helical.getChngM()));
       assertEquals("Tests the value of helical calculation", "0.8574264523517716", Double.toString(helical.helicalCalc()));
    }
    
}