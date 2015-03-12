/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

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

	private ArrayList<Reusable> reusables = new ArrayList<Reusable>(2);;
	private final int MAX_REUSABLES = 2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() {

		ReusablePool r = ReusablePool.getInstance();

		for (int i = 0; i < 2; i++) {
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
	 * Test para el metodo {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 * Que comprueba que se implementa correctamente el patrón Singleton
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
			assertEquals("Los objetos reusable no son iguales",
					rP.acquireReusable(), reusables.get(i));
		}
	}

	/**
	 * Test para comprobar si el metodo
	 * {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}. lanza
	 * correctamente su excepción
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
	 * Test para el metodo
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * Que comprueba que no deja añadir valores Nulos.
	 * 
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testReleaseReusableAddNull() throws NotFreeInstanceException {

		ReusablePool rP = ReusablePool.getInstance();
		Reusable r = null;

		r = rP.acquireReusable();
		rP.releaseReusable(null);
		assertNotNull(rP.acquireReusable());

	}

	/**
	 * Test para el metodo
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}
	 * Que comprueba que se liberan corretamente los objetos reusable.
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException {

		ReusablePool rP = ReusablePool.getInstance();
		Reusable r = null;

		r = rP.acquireReusable();
		rP.releaseReusable(r);
		assertEquals("El objeto", r, rP.acquireReusable());

	}

}
