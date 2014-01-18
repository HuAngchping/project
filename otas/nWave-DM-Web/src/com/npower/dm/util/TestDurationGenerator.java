/**
 * $Header: /home/master/nWave-DM-Web/src/com/npower/dm/util/TestDurationGenerator.java,v 1.1 2008/06/18 12:32:31 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/18 12:32:31 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.util;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/18 12:32:31 $
 */
public class TestDurationGenerator extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testRandom() throws Exception {
    int total = 100000;
    int min = 10;
    int max = 20000;
    
    for (int i = 0; i < total; i++) {
        int result = DurationGenerator.random(min, max);
        assertTrue(min <= result && result < max);
    }

    total = 100000;
    min = 0;
    max = 20000;
    for (int i = 0; i < total; i++) {
      int result = DurationGenerator.random(min, max);
      assertTrue(min <= result && result < max);
    }

    total = 100000;
    min = 0;
    max = 0;
    for (int i = 0; i < total; i++) {
      int result = DurationGenerator.random(min, max);
      assertTrue(result == 0);
    }

    total = 100000;
    min = 200000;
    max = 20000;
    for (int i = 0; i < total; i++) {
      int result = DurationGenerator.random(min, max);
      assertTrue(result == 0);
    }

  }

}
