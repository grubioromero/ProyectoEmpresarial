package pe.edu.cibertec.proyemp.jpa.service;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import pe.edu.cibertec.proyemp.jpa.domain.Departamento;
import pe.edu.cibertec.proyemp.jpa.domain.Empleado;

public class JpaTest {

	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Rubio");

		EntityManager manager = factory.createEntityManager();

		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();

		tx.begin();

		//test.crearEmpleados();
		test.crearEmpleados2();
		test.modificarNombreDepartamento();
		
		tx.commit();

		test.listarEmpleados();

		System.out.println(".. done");
	}

	private void crearEmpleados2() {

		Departamento departamento = new Departamento("Java");	
	//	List<Empleado> empleados = new ArrayList<Empleado>();	
		Empleado bob = new Empleado("Bob");
		Empleado mike = new Empleado("Mike");
		
//		empleados.add(bob);
//		empleados.add(mike);
		departamento.setEmpleados(Arrays.asList(bob, mike));
		manager.persist(departamento);
		
		bob.setDepartamento(departamento);
		mike.setDepartamento(departamento);
	}

	private void modificarNombreDepartamento() {
//		Departamento dep = manager.
//				createQuery("from Departamento where id=1", 
//						Departamento.class).getSingleResult();
		
		Departamento dep = manager.find(Departamento.class, 
				new Long(1));
		
		dep.setNombre(".NET");
		//manager.persist(dep);
		
	}

	private void crearEmpleados() {
		int nroDeEmpleados = manager
				.createQuery("Select a From Empleado a", Empleado.class)
				.getResultList().size();

		if (nroDeEmpleados == 0) {
			System.out.println("creando empleados");
			Departamento departamento = new Departamento("Java");
			manager.persist(departamento);
			manager.persist(new Empleado("Bob", departamento));
			manager.persist(new Empleado("Mike", departamento));
		}
	}

	private void listarEmpleados() {
		List<Empleado> resultList = manager.createQuery(
				"Select a From Empleado a", Empleado.class).getResultList();

		System.out.println("nro de empleados:" + resultList.size());
		for (Empleado next : resultList) {
			System.out.println("siguiente empleado: " + next);
		}

	}

}
