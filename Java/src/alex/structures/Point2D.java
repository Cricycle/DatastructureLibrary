package alex.structures;

import java.util.Comparator;

public class Point2D
{
	/**
	 * Sorts Point2Ds by PolarAngle. If two Point2Ds are at the same angle,
	 * then we sort smallest magnitude first.
	 * 
	 * @author Alex
	 *
	 */
	public static class PolarAngleSort implements Comparator<Point2D>
	{
		public static final PolarAngleSort Instance = new PolarAngleSort();

		private PolarAngleSort()
		{
		}

		public int compare(Point2D left, Point2D right)
		{
			int leftQuad = left.quadrant();
			int rightQuad = right.quadrant();

			// If the points are in different quadrants, easy to sort
			if (leftQuad - rightQuad != 0)
				return leftQuad - rightQuad;

			Turn t = left.getTurn(right);

			if (t == Turn.Right)
				return 1;
			else if (t == Turn.Left)
				return -1;

			double magnitudeDiff = left.magnitude2() - right.magnitude2();
			if (isCloseToZero(magnitudeDiff))
			{
				return 0;
			}
			else if (magnitudeDiff > 0)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
	}

	public static final double EPS = 1e-9;
	public final double x;
	public final double y;

	public Point2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Point2D add(Point2D p2)
	{
		return new Point2D(x + p2.x, y + p2.y);
	}

	public Point2D subtract(Point2D p2)
	{
		return new Point2D(x - p2.x, y - p2.y);
	}

	public double crossProduct(Point2D p2)
	{
		return x * p2.y - y * p2.x;
	}

	public double magnitude()
	{
		return Math.sqrt(magnitude2());
	}

	public double magnitude2()
	{
		return x * x + y * y;
	}

	/**
	 * Answers the question: if we go this --> p1, and then p1 ---> p2, what turn did we make at p1?
	 * Answers the question: if we have this ---> p1, and this ---> p2, then where is v2 relative to v1?
	 * 
	 * @param p1 The end of the vector this ---> p1
	 * @param p2 Either the end of the vector this ---> p2 or the end of the vector p1 ---> p2.
	 * @return A Turn indicating which type of Turn has taken place
	 */
	public Turn getTurn(Point2D p1, Point2D p2)
	{
		return p1.subtract(this).getTurn(p2.subtract(this));
	}

	/**
	 * Answers the question: if we go (0,0) ---> this and this ---> p2, what turn did we make at this?
	 * Answers the question: if we go (0,0) ---> this and (0,0) ---> p2, where is p2 relative to this?
	 * 
	 * @param p2 The second point in the turn comparison
	 * @return A turn indicating which type of Turn has taken place
	 */
	public Turn getTurn(Point2D p2)
	{
		return interpretCrossProduct(crossProduct(p2));
	}

	public int quadrant()
	{
		if (isCloseToZero(x) && isCloseToZero(y))
			return 1;
		else if (y >= 0 && x > 0)
			return 1;
		else if (x <= 0 && y > 0)
			return 2;
		else if (x < 0) // y <= 0
			return 3;
		else
			// (x >= 0 && y < 0)
			return 4;

	}

	private Turn interpretCrossProduct(double crossProduct)
	{
		if (isCloseToZero(crossProduct))
			return Turn.Collinear;
		else if (crossProduct > 0)
			return Turn.Left;
		else
			return Turn.Right;
	}

	private static boolean isCloseToZero(double value)
	{
		return Math.abs(value) < EPS;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof Point2D))
		{
			return false;
		}

		Point2D p = (Point2D) o;
		return isCloseToZero(p.x - x) && isCloseToZero(p.y - y);
	}

	/**
	 * The code for equality dictates that the hashCode for everything must be the same
	 * Probably shouldn't be used.
	 */
	public int hashCode()
	{
		return 0;
	}

	public String toString()
	{
		return String.format("{%.6f,%.6f}", x, y);
	}
}
