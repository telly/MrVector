/*
 * Copyright (C) Telly, Inc. and other contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.support.v4.util;

import java.util.Arrays;

import static android.support.v4.util.ContainerHelpers.binarySearch;
import static android.support.v4.util.ContainerHelpers.idealIntArraySize;

public class IntSet implements Cloneable {
  private static final int DELETED = 0;
  private int[] mElements;
  private int mSize;
  private boolean mContainsDeleted;

  /**
   * Creates a new ResArray containing no mappings.
   */
  public IntSet() {
    this(10);
  }

  /**
   * Creates a new ResArray containing no mappings that will not
   * require any additional memory allocation to store the specified
   * number of mappings.  If you supply an initial capacity of 0, the
   * sparse array will be initialized with a light-weight representation
   * not requiring any additional array allocations.
   */
  public IntSet(int initialCapacity) {
    if (initialCapacity == 0) {
      mElements = ContainerHelpers.EMPTY_INTS;
    } else {
      mElements = new int[idealIntArraySize(initialCapacity)];
    }
    mSize = 0;
  }

  @Override
  public IntSet clone() {
    IntSet clone = null;
    try {
      clone = (IntSet) super.clone();
      clone.mElements = mElements.clone();
    } catch (CloneNotSupportedException cnse) {
            /* ignore */
    }
    return clone;
  }

  public void remove(int element) {
    if (element == DELETED) {
      mContainsDeleted = false;
      return;
    }

    int i = binarySearch(mElements, mSize, element);

    if (i >= 0) {
      mElements[i] = DELETED;
    }
  }

  public void add(int element) {
    if (element == DELETED) {
      mContainsDeleted = true;
      return;
    }

    if (contains(element)) {
      return; // already there
    } else {
      mElements = append(mElements, mSize, element);
      mSize++;
    }
  }

  public void addAll(int[] elements) {
    if (elements == null) {
      return;
    }
    for (int element : elements) {
      add(element);
    }
  }

  public boolean contains(int element) {
    if (element == DELETED) {
      return mContainsDeleted;
    }
    return binarySearch(mElements, mSize, element) >= 0;
  }

  static int[] append(int[] array, int currentSize, int element) {
    assert currentSize <= array.length;

    if (currentSize + 1 > array.length) {
      int[] newArray = new int[idealIntArraySize(currentSize)];
      System.arraycopy(array, 0, newArray, 0, currentSize);
      array = newArray;
    }
    array[currentSize] = element;
    return array;
  }

  public int size() {
    return mSize;
  }

  public void clear() {
    mSize = 0;
    Arrays.fill(mElements, DELETED);
  }

  @Override
  public String toString() {
    if (size() <= 0) {
      return "{}";
    }

    StringBuilder buffer = new StringBuilder(mSize * 28);
    buffer.append('{');
    for (int i = 0; i < mSize; i++) {
      if (i > 0) {
        buffer.append(", ");
      }

      int element = mElements[i];
      buffer.append(element);
    }
    buffer.append('}');
    return buffer.toString();
  }
}
