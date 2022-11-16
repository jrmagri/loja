package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class BuscarParametrosComCriteria {
    public static void main(String[] args) {

        popularBancoDeDados();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        produtoDAO.buscarPorParametrosComCriteria("Xiaomi Redmi", null, null);



        em.close();


    }

    public static void popularBancoDeDados(){
        Categoria celulares = new Categoria("CELULARES");
        Categoria informatica = new Categoria("INFORMATICA");
        Categoria moveis = new Categoria("MOVEIS");

        Produto celular = new Produto("Xiaomi Redmi", "Muito bom", new BigDecimal("2000"), celulares);
        Produto teclado = new Produto("Teclado Logitech", "Preto com Led color", new BigDecimal("999"), informatica);
        Produto monitor = new Produto("monitor acer", "Preto 244hz", new BigDecimal("3999"), informatica);
        Produto comoda = new Produto("comoda", "branca", new BigDecimal("1999"), moveis);

        Cliente cliente = new Cliente("Rodrigo", "1234523");
        Cliente cliente2 = new Cliente("Rafael", "3244523");
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10,pedido, teclado));
        Pedido pedido2 = new Pedido(cliente2);
        pedido.adicionarItem(new ItemPedido(15,pedido, monitor));


        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        em.getTransaction().begin();

        produtoDAO.cadastrar(celular);
        produtoDAO.cadastrar(teclado);
        produtoDAO.cadastrar(monitor);
        produtoDAO.cadastrar(comoda);
        clienteDAO.cadastrar(cliente);

        categoriaDAO.cadastrar(celulares);
        categoriaDAO.cadastrar(informatica);
        categoriaDAO.cadastrar(moveis);
        em.flush();
        em.clear();

        em.getTransaction().commit();
        em.close();
    }

}

