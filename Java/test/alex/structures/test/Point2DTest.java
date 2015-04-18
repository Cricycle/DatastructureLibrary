package alex.structures.test;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import alex.structures.Point2D;
import alex.structures.Turn;

public class Point2DTest
{
	@Test
	public void Point2DAddTest()
	{
		Point2D p0 = new Point2D(5, 6);
		Point2D p1 = new Point2D(1.2, 4.5);

		Point2D sum = p0.add(p1);
		Point2D expectedSum = new Point2D(6.2, 10.5);
		Assert.assertEquals(sum, expectedSum);
	}

	@Test
	public void Point2DSubtractTest()
	{
		Point2D p0 = new Point2D(5, 6);
		Point2D p1 = new Point2D(1.2, 4.999);

		Point2D diff1 = p0.subtract(p1);
		Point2D diff2 = p1.subtract(p0);
		Point2D expectedDiff1 = new Point2D(3.8, 1.001);
		Point2D expectedDiff2 = new Point2D(-3.8, -1.001);

		Assert.assertEquals(diff1, expectedDiff1);
		Assert.assertEquals(diff2, expectedDiff2);
	}

	/**
	 * Tests that two points are equal at appropriate times, and that points which are equal also have the same hashcode
	 */
	@Test
	public void Point2DEqualityTest()
	{
		Point2D[] points = {
			new Point2D(0, 0),
			new Point2D(10, 5),
			new Point2D(1.4567e99, 2.4567e99),
			new Point2D(1.1e-50, 1.2e-50),
			new Point2D(-5.1e-49, 1.4e-40),
			new Point2D(10 + Point2D.EPS, 5),
			new Point2D(10 + Point2D.EPS * 1.1, 5),
			new Point2D(10 + Point2D.EPS / 1.1, 5),
			new Point2D(10, 5 + Point2D.EPS),
			new Point2D(10, 5 + Point2D.EPS * 1.1),
			new Point2D(10, 5 + Point2D.EPS / 1.1) };

		String[] equality = {
			"10011000000",
			"01000001001",
			"00100000000",
			"10011000000",
			"10011000000",
			"00000111000",
			"00000111000",
			"01000111001",
			"00000000111",
			"00000000111",
			"01000001111" };

		assert points.length == equality.length;

		for (int i = 0; i < points.length; ++i)
		{
			assert equality[i].length() == points.length;
			for (int j = 0; j < points.length; ++j)
			{
				Assert.assertEquals(points[i].equals(points[j]), equality[i].charAt(j) == '1', "i=" + i + ",j=" + j);
			}
		}
	}

	@Test
	public void Point2DWrongObjectEqualityTest()
	{
		String mystery = "mysterystring";
		Point2D point = new Point2D(0, 0);

		assert !point.equals(mystery);
	}

	@Test
	public void Point2DCrossProductTest()
	{
		Point2D[][] pointSets = {
			{ new Point2D(0, 0), new Point2D(1000, 151231.121) },
			{ new Point2D(1, 5), new Point2D(3, -1) },
			{ new Point2D(5, 1), new Point2D(1, 5) },
			{ new Point2D(1, 5), new Point2D(-1, 5) },
			{ new Point2D(-5, -1), new Point2D(-1, 5) },
			{ new Point2D(4, 4), new Point2D(-3, -3) },
			{ new Point2D(-2, 3), new Point2D(-4, 6) },
			{ new Point2D(5, 5), new Point2D(5, 5) } };

		double[] expectedCrossProduct = { 0.0, -16.0, 24.0, 10.0, -26.0, 0.0, 0.0, 0.0 };
		assert pointSets.length == expectedCrossProduct.length;

		for (int i = 0; i < pointSets.length; ++i)
		{
			assert pointSets[i].length == 2;
			Point2D p0 = pointSets[i][0];
			Point2D p1 = pointSets[i][1];

			double crossProduct = p0.crossProduct(p1);
			double negCrossProduct = p1.crossProduct(p0);

			String message = String.format("i=%d, crossProduct=%.3f, expectedCrossProduct=%.3f", i, crossProduct,
				expectedCrossProduct[i]);
			Assert.assertTrue(Math.abs(crossProduct - expectedCrossProduct[i]) < Point2D.EPS, message);
			assert Math.abs(crossProduct + negCrossProduct) < Point2D.EPS;
		}
	}

	@Test
	public void Point2DTurnTest()
	{
		Point2D[][] pointSets = {
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(40, 40) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(17, 17) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(5, 5) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(-1, -1) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(40, 41) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(15, 99) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(6, 7) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(-1, 0) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(-12, 40) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(-400000, -399999) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(-4000000, -4000000) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(-40000000, -40000001) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(-50, -55) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(0, -1) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(0, -1231231) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(5, 4.99999) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(6, 0) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(17, 16.99999) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(18, 17.9999) },
			{ new Point2D(5, 5), new Point2D(17, 17), new Point2D(50000000, 49999999) } };

		Turn[] expectedTurns = {
			Turn.Collinear,
			Turn.Collinear,
			Turn.Collinear,
			Turn.Collinear,
			Turn.Left,
			Turn.Left,
			Turn.Left,
			Turn.Left,
			Turn.Left,
			Turn.Left,
			Turn.Collinear,
			Turn.Right,
			Turn.Right,
			Turn.Right,
			Turn.Right,
			Turn.Right,
			Turn.Right,
			Turn.Right,
			Turn.Right,
			Turn.Right };

		assert pointSets.length == expectedTurns.length;

		for (int i = 0; i < pointSets.length; ++i)
		{
			Assert.assertEquals(pointSets[i].length, 3, "i = " + i);
			Point2D p0 = pointSets[i][0];
			Point2D p1 = pointSets[i][1];
			Point2D p2 = pointSets[i][2];

			Turn result = p0.getTurn(p1, p2);
			Assert.assertEquals(result, expectedTurns[i], "i = " + i);
		}

	}

	/**
	 * Even though it is not recommended to use Point2D in hashing, test that it at least works, if slow.
	 */
	@Test
	public void Point2DHashcodeTest()
	{
		HashMap<Point2D, String> map = new HashMap<>();
		String value = "specialValue";
		map.put(new Point2D(5, 17), value);

		String output = map.get(new Point2D(5, 17));
		Assert.assertEquals(output, value);
	}
}
