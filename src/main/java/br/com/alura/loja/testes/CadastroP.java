package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class CadastroP {
    public static void main(String[] args) {
        cadastrarProduto();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        Produto p = produtoDAO.buscarPorId(74l);
        System.out.println(p);

        List<Produto> produtos = produtoDAO.buscarPorNomeDaCategoria("CELULARES");
        produtos.forEach(p2 -> System.out.println(p2));

        BigDecimal precoProduto = produtoDAO.buscarPorPrecoComNome("monitor acer");
        System.out.println("Preço do produto:"+ precoProduto);


    }

    public static void cadastrarProduto(){
        Categoria celulares = new Categoria("CELULARES");
        Categoria informatica = new Categoria("INFORMATICA");
        Categoria moveis = new Categoria("MOVEIS");

        Produto celular = new Produto("Xiaomi Redmi", "Muito bom", new BigDecimal("2000"), celulares);
        Produto teclado = new Produto("Teclado Logitech", "Preto com Led color", new BigDecimal("999"), informatica);
        Produto monitor = new Produto("monitor acer", "Preto 244hz", new BigDecimal("3999"), informatica);
        Produto comoda = new Produto("comoda", "branca", new BigDecimal("1999"), moveis);

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);

        em.getTransaction().begin();

        produtoDAO.cadastrar(celular);
        produtoDAO.cadastrar(teclado);
        produtoDAO.cadastrar(monitor);
        produtoDAO.cadastrar(comoda);

        categoriaDAO.cadastrar(celulares);
        categoriaDAO.cadastrar(informatica);
        categoriaDAO.cadastrar(moveis);
        em.flush();
        em.clear();

        em.getTransaction().commit();
        em.close();
    }
}
