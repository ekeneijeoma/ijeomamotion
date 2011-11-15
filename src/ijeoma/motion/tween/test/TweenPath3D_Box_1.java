/**
	  Title: complex 3d tween
	  
	  Author: Ekene Ijeoma / Bloom
	  Date: February 2010  
	  Description: An object follows a complex path 
 */

package ijeoma.motion.tween.test;

import processing.core.*;

import ijeoma.geom.Path3D;
import ijeoma.geom.path.*;
import ijeoma.motion.*;
import ijeoma.motion.tween.Path3DTween;
import ijeoma.motion.tween.path.*;

public class TweenPath3D_Box_1 extends PApplet {
	PVector[] points;

	Path3D p;
	Path3DTween tp;

	int HALF_SIZE = 100;
	
	int[][] path = { { HALF_SIZE, HALF_SIZE, HALF_SIZE },
			{ -HALF_SIZE, -HALF_SIZE, -HALF_SIZE } };

	public void setup() {
		size(400, 400, P3D);
		smooth();

		points = new PVector[path.length];

		for (int i = 0; i < path.length; i++)
			points[i] = new PVector(path[i][0], path[i][1], path[i][2]);

		p = new Path3D(this, points);

		Motion.setup(this);

		tp = new Path3DTween("tp", p, 0f, 1f, 300f);
		tp.repeat();
		tp.play();
	}

	public void draw() {
		background(255);

		translate(width / 2, height / 2, 0);
		rotateY(frameCount / 100.0f);
		rotateX(2.0f);
		rotateZ(frameCount / 200.0f);

		// This draws the large box
		noFill();
		stroke(200);
		box(HALF_SIZE * 2);

		// This draws the path
		noFill();
		stroke(100);
		p.draw();

		// This draws the small black box
		fill(0);
		pushMatrix();
		translate(tp.getX(), tp.getY(), tp.getZ());
		box(20);
		popMatrix();
	}

	public void keyPressed() {
		// Path3D(PApplet _parent, PVector[] _points, String _pathMode)
		// _pathMode is set to CUBIC by default but can also be set to LINEAR,
		// COSINE, HERMITE
		
		if (key == '1')
			p.setMode(Path3D.LINEAR);
		else if (key == '2')
			p.setMode(Path3D.COSINE);
		else if (key == '3')
			p.setMode(Path3D.CUBIC);
		else if (key == '4')
			p.setMode(Path3D.HERMITE);
		else 
			tp.play();
	}
}
