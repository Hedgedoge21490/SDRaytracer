package Geometries;

import java.util.List;
import java.awt.*;

import Color.Light;
import Color.RGB;
import Math.Vec3D;

public class Ray {
	public Vec3D start = new Vec3D(0, 0, 0);
	public Vec3D dir = new Vec3D(0, 0, 0);
	static RGB black = new RGB(0.0f, 0.0f, 0.0f);

	public void setStart(float x, float y, float z) {
		start = new Vec3D(x, y, z);
	}

	public void setDir(float dx, float dy, float dz) {
		dir = new Vec3D(dx, dy, dz);
	}

	public void normalize() {
		dir.normalize();
	}

	// see Möller&Haines, page 305
	public IPoint intersect(Triangle t) {
		float epsilon = IPoint.epsilon;
		Vec3D e1 = t.p2.minus(t.p1);
		Vec3D e2 = t.p3.minus(t.p1);
		Vec3D p = dir.cross(e2);
		float a = e1.dot(p);
		if ((a > -epsilon) && (a < epsilon))
			return new IPoint(null, null, -1);
		float f = 1 / a;
		Vec3D s = start.minus(t.p1);
		float u = f * s.dot(p);
		if ((u < 0.0) || (u > 1.0))
			return new IPoint(null, null, -1);
		Vec3D q = s.cross(e1);
		float v = f * dir.dot(q);
		if ((v < 0.0) || (u + v > 1.0))
			return new IPoint(null, null, -1);
		// intersection point is u,v
		float dist = f * e2.dot(q);
		if (dist < epsilon)
			return new IPoint(null, null, -1);
		Vec3D ip = t.p1.mult(1 - u - v).add(t.p2.mult(u)).add(t.p3.mult(v));
		// DEBUG.debug("Intersection point: "+ip.x+","+ip.y+","+ip.z);
		return new IPoint(t, ip, dist);
	}
	
	public static IPoint hitObject(Ray ray, List<Triangle> triangles) {
		IPoint isect = new IPoint(null, null, -1);
		float idist = -1;
		for (Triangle t : triangles) {
			IPoint ip = ray.intersect(t);
			if (ip.dist != -1)
				if ((idist == -1) || (ip.dist < idist)) { // save that intersection
					idist = ip.dist;
					isect.ipoint = ip.ipoint;
					isect.dist = ip.dist;
					isect.triangle = t;
				}
		}
		return isect; // return intersection point and normal
	}
	
	public static RGB rayTrace(Ray ray, int rec, List<Triangle> triangles, int maxRec, Light[] lights, RGB ambient_color) {
		if (rec > maxRec)
			return black;
		IPoint ip = Ray.hitObject(ray, triangles); // (ray, p, n, triangle);
		if (ip.dist > IPoint.epsilon)
			return lighting(ray, ip, rec, lights, triangles, ambient_color, maxRec);
		else
			return black;
	}
	
	public static RGB lighting(Ray ray, IPoint ip, int rec, Light[] lights, List<Triangle> triangles, RGB ambient_color, int maxRec) {
		Vec3D point = ip.ipoint;
		Triangle triangle = ip.triangle;
		RGB color = RGB.addColors(triangle.color, ambient_color, 1);
		Ray shadow_ray = new Ray();
		for (Light light : lights) {
			shadow_ray.start = point;
			shadow_ray.dir = light.position.minus(point).mult(-1);
			shadow_ray.dir.normalize();
			IPoint ip2 = Ray.hitObject(shadow_ray, triangles);
			if (ip2.dist < IPoint.epsilon) {
				float ratio = Math.max(0, shadow_ray.dir.dot(triangle.normal));
				color = RGB.addColors(color, light.color, ratio);
			}
		}
		Ray reflection = new Ray();
		// R = 2N(N*L)-L) L ausgehender Vektor
		Vec3D L = ray.dir.mult(-1);
		reflection.start = point;
		reflection.dir = triangle.normal.mult(2 * triangle.normal.dot(L)).minus(L);
		reflection.dir.normalize();
		RGB rcolor = Ray.rayTrace(reflection, rec + 1, triangles, maxRec, lights, ambient_color);
		float ratio = (float) Math.pow(Math.max(0, reflection.dir.dot(L)), triangle.shininess);
		color = RGB.addColors(color, rcolor, ratio);
		return (color);
	}
}