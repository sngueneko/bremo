package berechnungsModule.wallHeatTransfer;




import berechnungsModule.Berechnung.Zone;
import misc.VectorBuffer;
import berechnungsModule.motor.Motor;
import berechnungsModule.motor.Motor_reciprocatingPiston;
import bremo.parameter.*;
import bremoExceptions.BirdBrainedProgrammerException;


public abstract class WallHeatTransfer {
//Abstrakte Klasse f�r alle Wandw�rmemodelle
	protected double T_cyl=Double.NaN;
	protected double T_piston=Double.NaN;
	protected double T_head=Double.NaN;
	protected double feuerstegMult;
	
	protected Motor motor;
	protected CasePara cp;	
	
	protected WallHeatTransfer(CasePara cp){				
		motor=cp.MOTOR;
		this.cp=cp;		
	}
	
	//Hiermit bekommt der Benutzer den Alpha-Wert in W/(m^2K)
	public abstract double get_WaermeUebergangsKoeffizient(double time, Zone[] zonen_IN, double fortschritt);
	
	/**
	 * Liefert die Flaeche die zur Berechznung des Wandwaermestroms verwendet wird. 
	 * @param time
	 * @return Oberflaeche des Brennraums in [m^2]
	 */
	public abstract double get_BrennraumFlaeche(double time);

	
	/**
	 * Berechnet die mittlere Brennraumtemperatur anhand der bestehenden Zonen
	 * @param Zone[] zonenIN
	 * @return double Tmb
	 */
	public double get_Tmb(Zone [] zonen_IN){
		double nenner=0;
		double zaehler=0;
		double T_i=0;
		double temp=0;
		for (int i=0; i<zonen_IN.length; i++){
			if(zonen_IN[i].get_m()>=cp.SYS.MINIMUM_ZONE_MASS){
				T_i=zonen_IN[i].get_T();
				temp=zonen_IN[i].get_m()*zonen_IN[i].get_gasMixtureZone().get_cv_mass(T_i);
				nenner+=temp;
				zaehler+=temp*T_i;
			}
		}
		
		return zaehler/nenner;
	}
	
	protected double get_R_brennraum(double time, Zone [] zonen_IN){
		double R_mix=0; //Gesamtgaskonstante der Zonen
		double R_i=0;	//Gaskonstante der Zonen
		double x_i=0;	//Massenbruch der Zonen
		double m_Ges=0;	//Gesamtmasse im Zylinder
		
		//TODO: Was ist, wenn Masse noch in der Fl�ssigphase ist???
		for (int i=0; i<zonen_IN.length; i++){
			m_Ges+=zonen_IN[i].get_m();
		}
		
		for (int i=0; i<zonen_IN.length; i++){
			R_i=zonen_IN[i].get_gasMixtureZone().get_R();
			x_i=zonen_IN[i].get_m()/m_Ges;
			R_mix += R_i*x_i;
		}
		
		return R_mix;	}

	//Modelle die auch fuer nciht Kolbenmotoren geeignet sind muessen diese Methode ueberschreiben
	public double get_wallHeatFlux(double time, Zone[] zonen_IN,
			double fortschritt, VectorBuffer tBuffer) {		
		double wht=0;
		double alpha=this.get_WaermeUebergangsKoeffizient(time, zonen_IN, fortschritt);
		if(motor.isHubKolbenMotor()){
			Motor_reciprocatingPiston hkm=((Motor_reciprocatingPiston)motor);
			double pistonSurf=hkm.get_Kolbenflaeche()+feuerstegMult*hkm.get_FeuerstegFlaeche();
			double headSurf=hkm.get_fireDeckArea();
			double cylWallSurf=hkm.get_CylinderLinerArea(time);

			if(Double.isNaN(T_cyl)||Double.isNaN(T_piston)||Double.isNaN(T_head))	{
				T_cyl=cp.get_T_Cyl();	
				T_piston=cp.get_T_Piston();	
				T_head=cp.get_T_Head();	
			}
			double T=get_Tmb(zonen_IN);
			wht=alpha*(pistonSurf*(T-T_piston)+headSurf*(T-T_head)+cylWallSurf*(T-T_cyl));

		}else{
			try{
				throw new BirdBrainedProgrammerException("WHT-Models " +
						"for non Piston engines must override this method!");					
			}catch(BirdBrainedProgrammerException bbpe){
				bbpe.stopBremo();
			}
		}
		return wht;
	}
	
	//...und hiermit den Wandw�rmestrom in W
//	public double get_WandWaermeStrom(double time, Zone[] zonen_IN, double fortschritt, VektorBuffer tBuffer){
//		double Brennraumflaeche = this.get_BrennraumFlaeche(time);		
//		double WWSD=get_WandWaermeStromDichte(time,zonen_IN, fortschritt, tBuffer)*Brennraumflaeche;	
//		return WWSD;
//	}	
	
	
	//...und hiermit die W�rmestromdichte in W/m^2
//	public double get_WandWaermeStromDichte(double time, Zone[] zonen_IN, double fortschritt, VektorBuffer tBuffer){
//		//wall temperature
//		if(Double.isNaN(T_wall))	
//			T_wall=cp.get_T_Wand();	
//		// Waermestromdichte in W/m^2 (W/m^2 x K)
//		double T=get_Tmb(zonen_IN);
//		//multiply with factor		
//		return get_WaermeUebergangsKoeffizient(time,zonen_IN, fortschritt) * (T- T_wall);
//	}
	
	
	
}