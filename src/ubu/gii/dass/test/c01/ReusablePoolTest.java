/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Roberto Miranda
 * @author Daniel Lozano
 *
 */
public class ReusablePoolTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp(){
		
		ReusablePool r = ReusablePool.getInstance();
		
		//Lo vaciamos
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {

		ReusablePool r1 = ReusablePool.getInstance();
		ReusablePool r2 = ReusablePool.getInstance();

		assertNotNull("El objeto 1 es null", r1);
		assertNotNull("El objeto 2 es null", r1);
		assertEquals("Las 2 instancias no son iguales, error singleton", r1, r2);

	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test(expected = NotFreeInstanceException.class)
	public void testAcquireReusable() {
		
		ReusablePool rP=ReusablePool.getInstance();
		
		try {
			rP.acquireReusable();
			rP.acquireReusable();
			rP.acquireReusable();
		} catch (NotFreeInstanceException e) {
			System.err.println(e);
		}
		
		
	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * .
	 */
	@Test
	public void testReleaseReusable() {
		
		ReusablePool rP=ReusablePool.getInstance();
		Reusable r;
		try {
			r = rP.acquireReusable();
			rP.releaseReusable(r);
			rP.releaseReusable(r);
			rP.releaseReusable(r);
		} catch (NotFreeInstanceException e) {
			System.err.println(e);
		}
	
	}

}
