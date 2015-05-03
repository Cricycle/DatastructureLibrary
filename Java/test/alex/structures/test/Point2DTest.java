package alex.structures.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

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

	@Test
	public void Point2DMagnitudeTest()
	{
		Point2D[] points = {
			new Point2D(0, 0),
			new Point2D(1, 1),
			new Point2D(-1, -1),
			new Point2D(6, 8),
			new Point2D(15, 8) };

		double[][] magnitudes = {
			{ 0.0, 0.0 },
			{ 1.414213562373095, 2.0 },
			{ 1.414213562373095, 2.0 },
			{ 10.0, 100.0 },
			{ 17.0, 289.0 } };

		Assert.assertEquals(points.length, magnitudes.length, "Input and Expected arrays are not same length.");

		for (int i = 0; i < points.length; ++i)
		{
			double magnitude = points[i].magnitude();
			double magnitude2 = points[i].magnitude2();

			Assert.assertTrue(Math.abs(magnitude - magnitudes[i][0]) < Point2D.EPS,
				String.format("i=%d,actual=%.9f,expected=%.9f", i, magnitude, magnitudes[i][0]));
			Assert.assertTrue(Math.abs(magnitude2 - magnitudes[i][1]) < Point2D.EPS,
				String.format("i=%d,actual=%.9f,expected=%.9f", i, magnitude2, magnitudes[i][1]));
		}
	}

	@Test
	public void Point2DQuadrantTest()
	{
		Point2D[] points = {
			new Point2D(0, 0),
			new Point2D(0.000000000001f, -0.00000000000001f),
			new Point2D(1, 0),
			new Point2D(1, 1),
			new Point2D(0, 1),
			new Point2D(-1, 1),
			new Point2D(-1, 0),
			new Point2D(-1, -1),
			new Point2D(0, -1),
			new Point2D(1, -1) };

		int[] quadrants = { 1, 1, 1, 1, 2, 2, 3, 3, 4, 4 };

		Assert.assertEquals(points.length, quadrants.length, "Input and Expected arrays are not the same length.");

		for (int i = 0; i < points.length; ++i)
		{
			Assert.assertEquals(quadrants[i], points[i].quadrant(),
				String.format("i=%d,actual=%d,expected=%d", i, points[i].quadrant(), quadrants[i]));
		}
	}

	/**
	 * Tests that if all the elements are different directions with non-zero magnitude, the sorting is correct
	 */
	@Test
	public void Point2DPolarSortTest1()
	{
		Point2D[] expectedSort = {
			new Point2D(1, 0),
			new Point2D(1, 1),
			new Point2D(0, 1),
			new Point2D(-1, 1),
			new Point2D(-1, 0),
			new Point2D(-1, -1),
			new Point2D(0, -1),
			new Point2D(1, -1) };

		testPermutationSorting(expectedSort, Point2D.PolarAngleSort.Instance);
	}

	/**
	 * Tests that if all the elements have the same direction, any magnitude, the sorting is correct,
	 * and that the sorting can distinguish between values which are close
	 */
	@Test
	public void Point2DPolarSortTest2()
	{
		Point2D[] expectedSort = {
			new Point2D(0, 0),
			new Point2D(1, 1),
			new Point2D(2, 2),
			new Point2D(2.49999999, 2.49999999),
			new Point2D(2.5, 2.5),
			new Point2D(2.50000001, 2.50000001),
			new Point2D(3, 3) };

		testPermutationSorting(expectedSort, Point2D.PolarAngleSort.Instance);
	}

	/**
	 * Tests that sorting a large array happens in a reasonable amount of time.
	 * At the time of writing this test, it runs in about 1.8s to sort 1 million points randomly distributed in
	 * [-1,1] x [-1,1]
	 * Sorting 1 million randomly generated integers took < .5s
	 */
	@Test(timeOut = 3000)
	public void Point2DPolarSortTest3()
	{
		int arraySize = 1000000;
		long seed = 174716263123L;
		Random r = new Random(seed);
		Point2D[] pointList = new Point2D[arraySize];
		for (int i = 0; i < pointList.length; ++i)
		{
			double x = r.nextDouble() * 2.0 - 1.0;
			double y = r.nextDouble() * 2.0 - 1.0;
			pointList[i] = new Point2D(x, y);
		}

		Arrays.sort(pointList, Point2D.PolarAngleSort.Instance);
		for (int i = 0; i < pointList.length - 1; ++i)
		{
			Assert.assertTrue(Point2D.PolarAngleSort.Instance.compare(pointList[i], pointList[i + 1]) <= 0,
				"Something went wrong at index = " + i);
		}
	}

	/**
	 * Tests sorting when there are multiple instances of the same element
	 */
	@Test
	public void Point2DPolarSortTest4()
	{
		Point2D[] expectedSort = {
			new Point2D(1, 1),
			new Point2D(1, 2),
			new Point2D(1, 2),
			new Point2D(-1, 2),
			new Point2D(-1, 2),
			new Point2D(-1, 1),
			new Point2D(-1, -1),
			new Point2D(1, -1) };

		testPermutationSorting(expectedSort, Point2D.PolarAngleSort.Instance);
	}

	// Do not input an array of size more than 9
	private <T> void testPermutationSorting(T[] expectedSort, Comparator<T> comparator)
	{
		Assert.assertTrue(expectedSort.length < 10, "Input array length of " + expectedSort.length + " is too large.");
		int targetCount = 1;
		for (int arraySize = 1; arraySize <= expectedSort.length; ++arraySize)
		{
			int iterationCount = 0;
			targetCount *= arraySize;

			ArrayList<T> expectedSubarray = new ArrayList<T>(arraySize);
			for (int i = 0; i < arraySize; ++i)
				expectedSubarray.add(expectedSort[i]);

			int[] permutation = new int[arraySize];
			for (int i = 0; i < permutation.length; ++i)
				permutation[i] = i;
			do
			{
				++iterationCount;
				ArrayList<T> input = new ArrayList<T>(expectedSubarray.size());
				for (int i = 0; i < expectedSubarray.size(); ++i)
				{
					input.add(expectedSubarray.get(permutation[i]));
				}

				Collections.sort(input, comparator);
				Assert.assertEquals(input, expectedSubarray, "Permutation = " + Arrays.toString(permutation));
			}
			while (getNextPermutation(permutation));

			Assert.assertEquals(iterationCount, targetCount);
		}
	}

	/**
	 * Made for use in testing sorting.
	 * 
	 * @param array
	 * @return True if we rearranged array to the next permutation
	 */
	private boolean getNextPermutation(int[] array)
	{
		// step 1: Find largest index k such that a[k] < a[k+1]
		int k = array.length - 2;
		while (k >= 0 && array[k] >= array[k + 1])
			--k;

		// We are at the last permutation, lexicographically.
		if (k < 0)
			return false;

		// step 2: Find the largest index j > k such that a[k] < a[j]
		int j = array.length - 1;
		while (j > k && array[k] >= array[j])
			--j;

		// step 3: Swap a[k], a[j]
		int temp = array[k];
		array[k] = array[j];
		array[j] = temp;

		// step 4: Reverse a[k+1...]
		for (int left = k + 1, right = array.length - 1; left < right; ++left, --right)
		{
			temp = array[left];
			array[left] = array[right];
			array[right] = temp;
		}

		return true;
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
