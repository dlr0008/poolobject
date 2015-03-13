/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

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

	private final int MAX_REUSABLES = 2;
	private ArrayList<Reusable> reusables = new ArrayList<Reusable>(
			MAX_REUSABLES);;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {

		ReusablePool r = ReusablePool.getInstance();

		for (int i = 0; i < MAX_REUSABLES; i++) {
			try {
				reusables.add(r.acquireReusable());
			} catch (NotFreeInstanceException e) {
			}
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() {

		ReusablePool rP = ReusablePool.getInstance();

		for (Reusable r : reusables) {
			rP.releaseReusable(r);
		}

		reusables.clear();
	}

	/**
	 * Test para el metodo
	 * {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}. Que comprueba
	 * que los objetos reusables se liberan correctamente.
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testAcquireReusable() throws NotFreeInstanceException {

		ReusablePool rP = ReusablePool.getInstance();

		for (int i = 0; i <= MAX_REUSABLES - 1; i++) {
			rP.releaseReusable(reusables.get(i));
			assertEquals("Los objetos reusable no son iguales",
					rP.acquireReusable(), reusables.get(i));
		}
	}

	/**
	 * Test para comprobar si el metodo
	 * {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}. lanza
	 * correctamente su excepci�n
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test(expected = NotFreeInstanceException.class)
	public void testAcquireReusableException() throws NotFreeInstanceException {

		ReusablePool rP = ReusablePool.getInstance();

		for (int i = 0; i <= MAX_REUSABLES; i++) {
			rP.acquireReusable();
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {

		ReusablePool r1 = ReusablePool.getInstance();
		ReusablePool r2 = ReusablePool.getInstance();

		assertNotNull("El objeto 1 es null", r1);
		assertNotNull("El objeto 2 es null", r2);
		assertEquals("Las 2 instancias no son iguales, error singleton", r1, r2);

	}

	/**
	 * Test para el metodo
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * Que comprueba que se liberan corretamente los objetos reusable.
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {

		ReusablePool rP = ReusablePool.getInstance();
		for (int i = 0; i <= MAX_REUSABLES - 1; i++) {
			rP.releaseReusable(reusables.get(i));
			assertEquals("Los objetos reusable no son iguales",
					rP.acquireReusable(), reusables.get(i));
		}

	}

	/**
	 * Test para el metodo
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * Que comprueba que no deja a�adir valores Nulos.
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testReleaseReusableAddNull() throws NotFreeInstanceException {

		ReusablePool rP = ReusablePool.getInstance();

		for (int i = 0; i <= MAX_REUSABLES - 1; i++) {
			rP.releaseReusable(reusables.get(i));
		}

		Reusable r = rP.acquireReusable();
		rP.releaseReusable(null);
		assertNotNull("El objeto reusable es nulo", rP.acquireReusable());

	}

}
