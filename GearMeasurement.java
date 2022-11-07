import javax.swing.*;
import java.lang.*;

/**
 * Class GearMeasurement contains methods used to calculate measurements for spur and helical gears
 * 
 * @author Sophia Hall
 * @version 05/24/2018
 */
public class GearMeasurement {
    
    /** Stores the number of teeth on the gear */
    private int numTeeth;
    
    /** Stores the diametral pitch */
    private double pitch;
    
    /** Stores the pressure angle */
    private double pressureAngle;
    
    /** Stores the helix angle */
    private double helixAngle;
    
    /** Stores the pitch diameter */
    private double d;
    
    /** Stores the amount of teeth to be thin */
    private double teethThin;
    
    /**
     * Initializes the number of teeth, pitch, and pressure angle
     * 
     * @param numTeeth is the number of teeth
     * @param pitch is the value for the pitch
     * @param angle is the pressure angle
     * @param teeth is the amount the teeth will be thinned
     */
    public GearMeasurement(int numTeeth, double pitch, double angle, double teeth){
        this.numTeeth = numTeeth;
        this.pitch = pitch;
        this.pressureAngle = angle;
        this.teethThin = teeth;
        this.helixAngle = 0;
    }
    
    /**
     * Initializes the number of teeth, pitch, pressure angle, and helix angle
     * 
     * @param numTeeth is the number of teeth
     * @param pitch is the value for the pitch
     * @param angle is the pressure angle
     * @param helix is the helix angle
     * @param teeth is the amount the teeth will be thinned
     */
    public GearMeasurement(int numTeeth, double pitch, double angle, double helix, double teeth){
        this.numTeeth = numTeeth;
        this.pitch = pitch;
        this.pressureAngle = angle;
        this.helixAngle = helix;
        this.teethThin = teeth;
    }
    
    /**
     * Returns the number of teeth on the gear
     * 
     * @return the number of teeth
     */
    public int getNumTeeth(){
        return this.numTeeth;
    }
    
    /**
     * Returns the diametral pitch
     * 
     * @return the diametral pitch
     */
    public double getPitch(){
        return this.pitch;
    }
    
    /**
     * Returns the pressure angle
     * 
     * @return the pressure angle
     */
    public double getPressureAngle(){
        return this.pressureAngle * (Math.PI / 180);
    }
    
    /**
     * Returns the helix angle
     * 
     * @return the helix angle
     */
    public double getHelixAngle(){
        return this.helixAngle * (Math.PI / 180);
    }
    
    /**
     * Returns the pitch diameter
     * 
     * @return the pitch diameter
     */
    public double getD(){
        this.d = numTeeth/pitch;
        return this.d;
    }
    
    /**
     * Returns the amount the teeth will be thinned
     * 
     * @return the amount by which the teeth will be thinned
     */
    public double getTeethThin(){
        return this.teethThin;
    }
    
    /**
     * Returns the standard tooth thickness
     * 
     * @return standard tooth thickness
     */
    public double getTeeth(){
        return Math.PI/(2 * pitch);
    }
    
    /**
     * Calcultes the involute of the pressure angle
     * 
     * @return the involute of the pressure angle
     */
    public double getInvPhi(){
       return Math.tan(this.getPressureAngle()) - (this.getPressureAngle());
    }
    
    /**
     * Calculates a Laskin approximation
     * 
     * @param phi is the initial value of phi
     * @param count is the number of times to run the approximation
     * @return the approximated value of phi
     * 
     */
    public double laskinApprox(double phi, int count){
        double newPhi = phi + (this.getInvPhiW() - (Math.tan(phi) - phi)) / Math.pow(Math.tan(phi), 2);
        if(count == 8)
            return newPhi;
        else{
            count++;
            return this.laskinApprox(newPhi, count);
        }
    }
    
    /**
     * Calculates measurements for the spur gear
     * 
     * @return the measurement over wires for thin teeth
     */
    public double spurCalc(){
        // Stores the diameter of the base circle
        double dB = this.getD() * Math.cos(this.getPressureAngle());
      
        // Stores the diameter of the wires
        double g = 1.728 / this.getPitch();
        
        // Stores the involude of phiW
        double invPhiW = (this.getTeeth() / this.getD()) + this.getInvPhi() + (g / dB) - (Math.PI/numTeeth);
        
        // Stores the value of the first phi for the Laskin approximation
        double phi = 1.441 * Math.pow(invPhiW, 0.333333333) - (0.374 * invPhiW);
        
        // Stores the pressure angle at the center of wires
        double phiW = this.laskinApprox(phi,0);
        
        // Stores the diameter to the center of wires 
        double dW = dB / Math.cos(phiW);
        
        // Stores the measurement over wires
        double m;
        // For an even tooth gear
        if(this.getNumTeeth() % 2 == 0)
            m = dW + g;
        // For an odd tooth gear
        else 
            m = dW * Math.cos((90 * (Math.PI / 180)) / this.getNumTeeth()) + g;
        
        // Stores the change factor
        double kM = Math.cos(this.getPressureAngle()) / Math.sin(phiW);
        
        // Stores the reduction in measurement to thin teeth
        double changeM = kM * this.getTeethThin();
        
        // Stores the measurement over wires for thin teeth
        double m1 = m - changeM;
        
        return m1;
    }
    
    /**
     * Calculates measurements for the helical gear
     * 
     * @return the tangent of the helix angle at base circle
     */
    public double helicalCalc(){
        
        // Stores the value of the normal diametral pitch Pn
        double pN = this.getPitch() / Math.cos(this.getHelixAngle());
        
        // Stores the normal tooth thickness
        double tN = Math.PI / (2 * pN);
        
        // Stores the tangent of the pressure angle
        double tanPhi = Math.tan(this.getPressureAngle()) / Math.cos(this.getHelixAngle());
        
        // stores the value for phi
        double newPhi = Math.atan(tanPhi);
        
        // Stores the value of the diameter of the base circle
        double dB = this.getD() * Math.cos(newPhi);
        
        // Stores the value of the helix angle at the base of the circle
        double helixAngBase = Math.atan(Math.tan(this.getHelixAngle()) * Math.cos(newPhi));
        
        // Stores th diameter of wires
        double g = 1.728 / pN;
        
        // Stores the diameter of the imaginary disc
        double gPrim = g / Math.cos(helixAngBase);
        
        // Stores the involute of phiW
        double invPhiW = (gPrim / dB) + (Math.tan(newPhi) - newPhi) - (Math.PI / (2 * this.getNumTeeth()));
        
        // Stores the value of the first phi for the Laskin approximation
        double phi = 1.441 * Math.pow(invPhiW, 0.333333333) - (0.374 * invPhiW);
        
        // Stores the pressure angle at the center of wires
        double phiW = this.laskinApprox(phi,0);
        
        // Stores the diameter to the center of wires
        double dW = dB / Math.cos(phiW);
        
        // Stores the measurement over wires without backlash
        double m = dW + g;
        
        // For odd tooth gear
        if(this.getNumTeeth() % 2 != 0)
            m /= 2;
        
        // Stores the change factor
        double k = Math.cos(newPhi) / Math.sin(phiW);
        
        // Stores the reduction in measurement to thin teeth
        double chngM = (k * this.getTeethThin()) / Math.cos(this.getHelixAngle());
        
        // Stores the final measurement over wires
        double finalM = m - chngM;
        
        return finalM; 
    }
    
    /**
     * The main method
     */
    public static void main(String[] args){
        // Stores a number according to the type of gear that will be measured
        int gearType = 1;
        int redo = 0;
        
        // Stores the buttons for the JOptionPane
        String[] buttons = {"Cancel", "Spur", "Helical"};
        String[] done = {"New Calculation","Done"};

        while(redo == 0 && gearType != 0){
        gearType = JOptionPane.showOptionDialog(null, "Select the type of gear", "Gear Measurements", JOptionPane.INFORMATION_MESSAGE, 1, null, buttons, buttons[2]);
       
        // For spur gear
        if(gearType == 1){
            double n = Double.parseDouble(JOptionPane.showInputDialog("Enter the number of teeth (N)"));
            double p = Double.parseDouble(JOptionPane.showInputDialog("Enter the diametral pitch (P)"));
            double phi = Double.parseDouble(JOptionPane.showInputDialog("Enter the pressure angle (phi)"));
            double thin = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount by which the teeth will be thinned"));
            GearMeasurement myGear = new GearMeasurement((int)n, p, phi, thin);
            redo = JOptionPane.showOptionDialog(null, Double.toString(myGear.spurCalc()), "Gear Measurements", JOptionPane.INFORMATION_MESSAGE, 1, null, done, done[1]);
        }
        
        // For spur gear
        else if(gearType == 2){
            int n = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of teeth"));
            double p = Double.parseDouble(JOptionPane.showInputDialog("Enter the diametral pitch"));
            double phi = Double.parseDouble(JOptionPane.showInputDialog("Enter the pressure angle"));
            double helix = Double.parseDouble(JOptionPane.showInputDialog("Enter the helix angle"));
            double thin = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount by which the teeth will be thinned"));
            GearMeasurement myGear = new GearMeasurement(n, p, phi, helix, thin);
            redo = JOptionPane.showOptionDialog(null, Double.toString(myGear.helicalCalc()), "Gear Measurements", JOptionPane.INFORMATION_MESSAGE, 1, null, done, done[1]);
        }
        
        }
    }
    
    /**
     * Helper method to test the value of dB in the calculation method
     */
    public double getDB(){
        return this.getD() * Math.cos(this.getPressureAngle());
    }
    
    /**
     * Helper method to test the value of G in the calculation method
     */
    public double getG(){
        return 1.728 / this.getPitch();
    }
    
    /**
     * Helper method to test the value of invPhiW in the calculation method
     */
    public double getInvPhiW(){
        if(this.getHelixAngle() == 0)
            return (this.getTeeth() / this.getD()) + this.getInvPhi() + (this.getG() / this.getDB()) - Math.PI/numTeeth;
        else
            return (this.getGPrim() / this.getHDB()) + (Math.tan(this.getNewPhi()) - this.getNewPhi()) - (Math.PI / (2 * this.getNumTeeth()));  
    }
    
    /**
     * Helper method to test the value of phi in the calculation method
     */
    public double getPhi(){
        return (1.441 * Math.pow(this.getInvPhiW(), 0.3333333)) - (0.374 * this.getInvPhiW());
    }
    
    /**
     * Helper method to test the value of phiW in the calculation method
     */
    public double getPhiW(){
        return this.laskinApprox(this.getPhi(),0);
    }
    
    /**
     * Helper method to test the value of dW in the calculation method
     */
    public double getDW(){
        return this.getDB() / Math.cos(this.getPhiW());
    }
    
    /**
     * Helper method to test the value of m in the calculation method
     */
    public double getM(){
        double m;
        if(this.getNumTeeth() % 2 == 0)
            m = this.getDW() + this.getG();
        // For an odd tooth gear
        else 
            m = this.getDW() * Math.cos((90 * (Math.PI / 180)) / this.getNumTeeth()) + this.getG();
        return m;
    }
    
    /**
     * Helper method to test the value of kM in the calculation method
     */
    public double getKM(){
        return Math.cos(this.getPressureAngle()) / Math.sin(this.getPhiW());
    }
    
    /**
     * Helper method to test the value of change in m in the calculation method
     */
    public double getChangeM(){
        return this.getKM() * this.getTeethThin();
    }
    
    /**
     * Helper method to test the value of pN in the calculation method
     */
    public double getPN(){
        return this.getPitch() / Math.cos(this.getHelixAngle());
    }
    
    /**
     * Helper method to test the value of tN in the calculation method
     */
    public double getTN(){
        return Math.PI / (2 * this.getPN());
    }
    
    /**
     * Helper method to test the value of tanPhi in the calculation method
     */
    public double getTanPhi(){
        return Math.tan(this.getPressureAngle()) / Math.cos(this.getHelixAngle());
    }
    
    /**
     * Helper method to test the value of newPhi in the calculation method
     */
    public double getNewPhi(){
        return Math.atan(this.getTanPhi());
    }
    
    /**
     * Helper method to test the value of dB in the calculation method
     */
    public double getHDB(){
        return this.getD() * Math.cos(this.getNewPhi());
    }
    
    /**
     * Helper method to test the value of te helix angle at the base of the circle in the calculation method
     */
    public double getHelixAngBase(){
        return Math.atan(Math.tan(this.getHelixAngle()) * Math.cos(this.getNewPhi()));
    }
    
    /**
     * Helper method to test the value of G in the calculation method
     */
    public double getHelicG(){
        return 1.728/this.getPN();
    }
    
    /**
     * Helper method to test the value of G' in the calculation method
     */
    public double getGPrim(){
        return this.getHelicG() / Math.cos(this.getHelixAngBase()); 
    }
    
    /**
     * Helper method to test the value of the involute of phiW in the calculation method
     */
    public double getHInvPhiW(){
        return (this.getGPrim() / this.getHDB()) + (Math.tan(this.getNewPhi()) - this.getNewPhi()) - (Math.PI / (2 * this.getNumTeeth()));
    }
    
    /**
     * Helper method to test the value of phi in the calculation method
     */
    public double getHPhi(){
        return (1.441 * Math.pow(this.getHInvPhiW(), 0.3333333)) - (0.374 * this.getHInvPhiW());
    }
    
    /**
     * Helper method to test the value of phiW in the calculation method
     */
    public double getHPhiW(){
        return this.laskinApprox(this.getHPhi(),0);
    }
    
    /**
     * Helper method to test the value of dW in the calculation method
     */
    public double getHDW(){
        return this.getHDB() / Math.cos(this.getHPhiW());
    }
    
    /**
     * Helper method to test the value of m in the calculation method
     */
    public double getHM(){
        if(this.getNumTeeth() % 2 != 0)
            return (this.getHDW() + this.getHelicG()) / 2;
        else 
            return this.getHDW() + this.getHelicG();
    }
    
    /**
     * Helper method to test the value of k in the calculation method
     */
    public double getK(){
        return Math.cos(this.getNewPhi()) / Math.sin(this.getHPhiW());
    }
    
    /**
     * Helper method to test the value of chngM in the calculation method
     */
    public double getChngM(){
        return (this.getK() * this.getTeethThin()) / Math.cos(this.getHelixAngle());
    }
    

}