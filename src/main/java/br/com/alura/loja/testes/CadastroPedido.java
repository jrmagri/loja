package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroPedido {
    public static void main(String[] args) {
        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDAO = new ProdutoDAO(em);
        ClienteDAO clienteDAO = new ClienteDAO(em);

        Produto produto = produtoDAO.buscarPorId(1l);
        Produto produto2 = produtoDAO.buscarPorId(2l);
        Cliente cliente = clienteDAO.buscarPorId(1l);
        Cliente cliente2 = clienteDAO.buscarPorId(2l);


        em.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        Pedido pedido2 = new Pedido(cliente2);
        pedido.adicionarItem(new ItemPedido(10,pedido, produto));
        pedido2.adicionarItem(new ItemPedido(5,pedido, produto2));


        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.cadastrar(pedido);
        pedidoDAO.cadastrar(pedido2);


        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
        System.out.println("valor total vendido : "+ totalVendido);

        List<RelatorioVendas> relatorio = pedidoDAO.relatorioDeVendas();
        relatorio.forEach(System.out::println);

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
