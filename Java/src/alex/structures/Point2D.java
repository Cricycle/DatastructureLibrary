package alex.structures;

public class Point2D
{
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

	private Turn interpretCrossProduct(double crossProduct)
	{
		if (isCloseToZero(crossProduct))
			return Turn.Collinear;
		else if (crossProduct > 0)
			return Turn.Left;
		else
			return Turn.Right;
	}

	private boolean isCloseToZero(double value)
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
}
