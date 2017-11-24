package Geometries;

import java.util.List;

import Color.RGB;
import Math.Vec3D;

public class Triangle {
	public Vec3D p1, p2, p3;
	public RGB color;
	public Vec3D normal;
	public float shininess;

	public Triangle(Vec3D pp1, Vec3D pp2, Vec3D pp3, RGB col, float sh) {
		p1 = pp1;
		p2 = pp2;
		p3 = pp3;
		color = col;
		shininess = sh;
		Vec3D e1 = p2.minus(p1), e2 = p3.minus(p1);
		normal = e1.cross(e2);
		normal.normalize();
	}
	
	public static void addCube(List<Triangle> triangles, int x, int y, int z, int w, int h, int d, RGB c, float sh) { // front
		triangles.add(new Triangle(new Vec3D(x, y, z), new Vec3D(x + w, y, z), new Vec3D(x, y + h, z), c, sh));
		triangles.add(new Triangle(new Vec3D(x + w, y, z), new Vec3D(x + w, y + h, z), new Vec3D(x, y + h, z), c, sh));
		// left
		triangles.add(new Triangle(new Vec3D(x, y, z + d), new Vec3D(x, y, z), new Vec3D(x, y + h, z), c, sh));
		triangles.add(new Triangle(new Vec3D(x, y + h, z), new Vec3D(x, y + h, z + d), new Vec3D(x, y, z + d), c, sh));
		// right
		triangles.add(
				new Triangle(new Vec3D(x + w, y, z), new Vec3D(x + w, y, z + d), new Vec3D(x + w, y + h, z), c, sh));
		triangles.add(new Triangle(new Vec3D(x + w, y + h, z), new Vec3D(x + w, y, z + d),
				new Vec3D(x + w, y + h, z + d), c, sh));
		// top
		triangles.add(new Triangle(new Vec3D(x + w, y + h, z), new Vec3D(x + w, y + h, z + d), new Vec3D(x, y + h, z),
				c, sh));
		triangles.add(new Triangle(new Vec3D(x, y + h, z), new Vec3D(x + w, y + h, z + d), new Vec3D(x, y + h, z + d),
				c, sh));
		// bottom
		triangles.add(new Triangle(new Vec3D(x + w, y, z), new Vec3D(x, y, z), new Vec3D(x, y, z + d), c, sh));
		triangles.add(new Triangle(new Vec3D(x, y, z + d), new Vec3D(x + w, y, z + d), new Vec3D(x + w, y, z), c, sh));
		// back
		triangles.add(
				new Triangle(new Vec3D(x, y, z + d), new Vec3D(x, y + h, z + d), new Vec3D(x + w, y, z + d), c, sh));
		triangles.add(new Triangle(new Vec3D(x + w, y, z + d), new Vec3D(x, y + h, z + d),
				new Vec3D(x + w, y + h, z + d), c, sh));

	}
}
