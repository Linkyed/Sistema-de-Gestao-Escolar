package app.modelo.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import app.modelo.Funcionalidades;

public class DAO<E> {
	private static EntityManagerFactory emf;
	EntityManager em;
	Class<E> classe;
	
	static {
		emf = Persistence.createEntityManagerFactory("Sistema-de-Gestao-Escolar");		
	}
	
	public DAO() {
		this(null);
	}
	
	public DAO(Class<E> classe) {
		this.classe = classe;
		em = emf.createEntityManager();
	}
	
	DAO<E> abrirTransacao(){
		em.getTransaction().begin();
		return this;
	}
	
	DAO<E> mergeMudanca(E entidade){
		em.merge(entidade);
		return this;
	}
	
	DAO<E> mergeAtomico(E entidade){
		abrirTransacao().mergeMudanca(entidade).fecharTransacao();
		return this;
	}
	
	DAO<E> fecharTransacao(){
		em.getTransaction().commit();
		return this;
	}
	
	DAO<E> incluir(E entidade){
		em.persist(entidade);
		return this;
	}
	
	DAO<E> remove(E entidade){
		em.remove(entidade);
		return this;
	}
	
	DAO<E> removerEntidade(E entidade) {
		this.abrirTransacao().remove(entidade).fecharTransacao();	
		return this;
	}
	
	public DAO<E> incluirAtomico(E entidade) {
		return this.abrirTransacao().incluir(entidade).fecharTransacao();
	}
	
	public List<E> obterTodos(int quantidade, int deslocamento){
		Funcionalidades.testarObjetoNulo.apply(classe);
		String jpql = "select e from " + classe.getName() + " e";
		TypedQuery<E> query =  em.createQuery(jpql, classe).setFirstResult(deslocamento).setMaxResults(quantidade);
		
		return query.getResultList();
	}
	
	
	public List<E> obter(){
		return obterTodos(10, 0);
	}
	
	public E obterUltimo(){
		Funcionalidades.testarObjetoNulo.apply(classe);
		String jpql = "select e from " + classe.getName() + " e order by id desc";
		E entidade = em.createQuery(jpql, classe).setMaxResults(1).getSingleResult();
		
		return entidade;
	}
	
	public E obterPrimeiro(){
		Funcionalidades.testarObjetoNulo.apply(classe);
		String jpql = "select e from " + classe.getName() + " e";
		E entidade = em.createQuery(jpql, classe).setMaxResults(1).getSingleResult();
		
		return entidade;
	}
	
	public void fechar() {
		em.close();
	}
}
