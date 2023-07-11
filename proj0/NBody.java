public class NBody{

	/** the main working loop */
	public static void main(String[] args){
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];
		Planet[] planets = readplanets(fileName);
		double radius = readRadius(fileName);

		StdDraw.setScale(-1 * radius, radius);
		StdDraw.enableDoubleBuffering();

		for (int time = 0; time < T; time += dt){
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];

			for (int i = 0; i < planets.length; i += 1){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}

			for (int i = 0; i < planets.length; i += 1){
				planets[i].update(dt, xForces[i], yForces[i]);
			}

			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg"); 
			for (Planet p: planets){
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
	public static Planet[] readplanets(String fileName){
		In in = new In(fileName);

		int totalplanets = in.readInt();
		in.readDouble();


		Planet[] planets = new Planet[totalplanets];
		for (int i = 0; i < totalplanets; i += 1){
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);		       }
		
		return planets;
	}

}
