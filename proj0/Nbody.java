public class NBody{

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];
		Planet[] Planets = readPlanets(fileName);
		double radiusUniverse = readRadius(fileName);

		StdDraw.setScale(-1 * radiusUniverse, radiusUniverse);
		StdDraw.enableDoubleBuffering();

		for (int time = 0; time < T; time += dt){
			double[] xForces = new double[Planets.length];
			double[] yForces = new double[Planets.length];

			for (int i = 0; i < Planets.length; i += 1){
				xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
				yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
			}

			for (int i = 0; i < Planets.length; i += 1){
				Planets[i].update(dt, xForces[i], yForces[i]);
			}

			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg"); 
			for (Planet p: Planets){
				p.draw();
			}

			StdDraw.show();
			StdDraw.pause(10);
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
		                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
}
	}

	/** 
	 * Read the radius of the universe
	 */
	public static double readRadius(String fileName){
		In in = new In(fileName);

		in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	/**
	 * Return an array of all the planets in the universe
	 */
	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);

		int totalPlanets = in.readInt();
		in.readDouble();


		Planet[] Planets = new Planet[totalPlanets];
		for (int i = 0; i < totalPlanets; i += 1){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			Planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);		       }
		
		return Planets;
	}

}
