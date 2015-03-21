package alex.structures;

import java.util.*;

class RangeQueryTree1D<T>
{
  private ArrayList<T> m_tree;
  private int m_initialSize;
  private int m_pow2;
  private RangeCombine<T> m_combiner;
  
  public RangeQueryTree1D(T[] inputArr, RangeCombine<T> combiner)
  {
    m_initialSize = inputArr.length;
    m_combiner = combiner;
    
    // In order for the recursion to work when querying, we need to have our initial array
    // begin at the left-most edge of a complete binary tree
    m_pow2 = 1;
    while (m_pow2 < m_initialSize)
    {
      m_pow2 <<= 1;
    }
    
    m_tree = new ArrayList<T>(m_initialSize + m_pow2);
    System.arraycopy(inputArr, 0, m_tree, m_pow2, m_initialSize);
    Initialize();
  }
  
  /**
   *  @param index This index is relative to the original array
   */
  public void Update(int index, T value)
  {
    m_tree.set(index + m_pow2, value);
    ReUpdate((index + m_pow2) / 2);
  }
  
  /**
   *  Inputs 'Left' and 'Right' are relative to the initial input array
   * @param left The left index of the query range
   * @param right The right index of the query range
   * @return The value of the query over the range [left, right]
   */
  public T Query(int left, int right)
  {
    if (left > right || left < 0 || right >= m_initialSize)
    {
      throw new ArrayIndexOutOfBoundsException(String.format("Inputs left=%d, right=%d, ArraySize=%d", left, right, m_initialSize));
    }
    
    return Query(left, right, 1, 0, m_pow2 - 1);
  }
  
  /**
   * We are recursively attempting to evaluate the query on [left, right]
   * Calling this method implies that treeIdx covers the range [treeLeft, treeRight]
   * It must be the case that [left, right] is a subset of [treeLeft, treeRight]
   *
   * At this point, we assume that all input values are valid.
   *
   * @param left The left index of the query range
   * @param right The right index of the query range
   * @param treeIdx The index in m_tree that represents the query on [treeLeft, treeRight]
   * @param treeLeft The left index of the query represented by treeIdx
   * @param treeRight The right index of the query represented by treeIdx
   * @return The value of the query on [left, right]
   */
  private T Query(int left, int right, int treeIdx, int treeLeft, int treeRight)
  {
    // If the current treeIdx matches the query we are looking for exactly, return the
    // query value
    if (left == treeLeft && right == treeRight)
    {
      return m_tree.get(treeIdx);
    }
    
    // Else, we need to split the query up further.
    int leftTreeIdx = treeIdx*2;
    int leftTreeLeft = treeLeft;
    int leftTreeRight = (treeRight + treeLeft)/2;
    
    int rightTreeIdx = treeIdx*2 + 1;
    int rightTreeLeft = leftTreeRight+1;
    int rightTreeRight = treeRight;
    
    // Target query is completely contained in the left tree
    if (right <= leftTreeRight)
    {
      return Query(left, right, leftTreeIdx, leftTreeLeft, leftTreeRight);
    }
    
    // Target query is completely contained in the right tree
    if (left >= rightTreeLeft)
    {
      return Query(left, right, rightTreeIdx, rightTreeLeft, rightTreeRight);
    }
    
    // Else the target query overlaps both trees
    T leftValue = Query(left, leftTreeRight, leftTreeIdx, leftTreeLeft, leftTreeRight);
    T rightValue = Query(rightTreeLeft, right, rightTreeIdx, rightTreeLeft, rightTreeRight);
    return m_combiner.combine(leftValue, rightValue);
  }
  
  /**
   *  This method initializes all the subquery values
   */
  private void Initialize()
  {
    for (int i = m_pow2-1; i > 0; --i)
    {
      Update(i);
    }
  }
  
  /**
   *  This method re-evaluates the m_tree[treeIndex] subquery value
   */
  private void Update(int treeIndex)
  {
      int left = treeIndex*2;
      int right = treeIndex*2+1;
      
      T value = null;
      if (left >= m_tree.size()) {}
      else if (right >= m_tree.size())
      {
        value = m_tree.get(left);
      }
      else
      {
        T leftValue = m_tree.get(left);
        T rightValue = m_tree.get(right);
        if (leftValue == null)
        {
          value = null;
        }
        else if (rightValue == null)
        {
          value = leftValue;
        }
        else
        {
          value = m_combiner.combine(leftValue, rightValue);
        }
      }
      
      m_tree.set(treeIndex, value);
  }
  
  private void ReUpdate(int treeIndex)
  {
    while (treeIndex > 0)
    {
      Update(treeIndex);
      treeIndex /= 2;
    }
  }
}

interface RangeCombine<T>
{
  /**
   *  This method combines the left object with one to its right.
   *  It is expected that the returned object is a new object entirely.
   *  It is expected that the inputs remain unmodified
   */
  T combine(T left, T right);
}