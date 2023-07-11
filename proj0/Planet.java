public class Planet{
	/** This is a class simulates the Planet. */
	static final double G = 6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV,
			double yV, double m, String img){
		/** Construction function of a Planet. */
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}
	
	public Planet(Planet p){
		/** Make a copy of the given Planet. */
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet supplied){
		/** Return the distance between the supplied Planet 
		 * and the Planet itself. */
		double xxDistance;
		double yyDistance;
		xxDistance = supplied.xxPos - this.xxPos;
		yyDistance = supplied.yyPos - this.yyPos;
		return Math.sqrt(xxDistance * xxDistance + yyDistance * yyDistance);
	}

	public double calcForceExertedBy(Planet anotherPlanet){
		double force;
		double distance;
		distance = this.calcDistance(anotherPlanet);
		force = (G * this.mass * anotherPlanet.mass) / (distance * distance);
		return force;
	}

	public double calcForceExertedByX(Planet anotherPlanet){
		double xxDistance = anotherPlanet.xxPos - this.xxPos;
		double totalForce = this.calcForceExertedBy(anotherPlanet);
		double distance = this.calcDistance(anotherPlanet);
		return totalForce * xxDistance / distance;
	}
	
	public double calcForceExertedByY(Planet anotherPlanet){
		double yyDistance = anotherPlanet.yyPos - this.yyPos;
		double distance = this.calcDistance(anotherPlanet);
		double totalForce = this.calcForceExertedBy(anotherPlanet);
		return totalForce * yyDistance / distance;
	}

	public double calcNetForceExertedByX(Planet[] otherPlanets){
		double xxNetForce = 0;
		for (Planet p: otherPlanets){
			if (p.equals(this)){
				continue;
			} else {
				xxNetForce += this.calcForceExertedByX(p);
			}
		}
		return xxNetForce;
	}
	
	public double calcNetForceExertedByY(Planet[] otherPlanets){
		double yyNetForce = 0;
		for (Planet p: otherPlanets){
			if (p.equals(this)){
				continue;
			} else {
				yyNetForce += this.calcForceExertedByY(p);
			}
		}
		return yyNetForce;
	}

	public void update(double dt, double xxNetForce, double yyNetForce){
		double axNet = xxNetForce / this.mass;
		double ayNet = yyNetForce / this.mass;
		this.xxVel += axNet * dt;
		this.yyVel += ayNet * dt;
		this.xxPos += this.xxVel * dt;
		this.yyPos += this.yyVel * dt;
	}

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);
	}
}
	

