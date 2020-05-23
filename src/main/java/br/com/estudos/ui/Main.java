package br.com.estudos.ui;

import java.util.List;
import java.util.Scanner;

import br.com.estudos.jpa.model.Pessoa;
import br.com.estudos.jpa.service.impl.PessoaService;
import br.com.estudos.jpa.service.interfaces.CrudService;
import br.com.estudos.jpa.service.interfaces.PessoaBuscaPorNome;

public class Main {

	public static Scanner SCR = new Scanner(System.in);

	public static void main(String[] args) {

		int option = 0;
		while (option != 6) {
			System.out.println("\n Escolha uma ação; ");
			System.out.println("1 - Listar pessoas");
			System.out.println("2 - Inserir pessoas");
			System.out.println("3 - Atualizar pessoas");
			System.out.println("4 - Excluir pessoas");
			System.out.println("5 - Pesquisar pessoa por nome");
			System.out.println("6 - Sair");
			System.out.print("Sua opção: ");

			option = SCR.nextInt();
			SCR.nextLine();
			switch (option) {
			case 1:
				listarPessoas();
				break;
			case 2:
				inserirPessoa();
			case 3:
				atualizarPessoas();
			case 4:
				excluirPessoas();
				break;
			case 5:
				pesquisarPessoaPorNome();
				break;
			case 6:
				System.out.println("Até mais!");
				break;

			default:
				System.out.println("Opção inválida");
				break;
			}

		}

	}

	private static void pesquisarPessoaPorNome() {
		System.out.println("Pesquisa de pessoa por nome");
		System.out.println("Digite o nome da pessoa a ser pesquisado");
		String nomePesquisado = SCR.nextLine();
		PessoaBuscaPorNome pessoaService = new PessoaService();
		pessoaService.pessoaByName(nomePesquisado).forEach(pessoa -> {
			System.out.println(String.format("(%d) %s %s %d ", pessoa.getId(), pessoa.getNome(), pessoa.getSobrenome(), pessoa.getIdade()));
		});

	}

	private static void excluirPessoas() {
		System.out.println("Excluir pessoas");
		System.out.println("Digite o Id da pessoa a ser removida");
		int idPessoaRemovida = SCR.nextInt();
		SCR.nextLine();
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		pessoaService.deletarById(idPessoaRemovida);
		System.out.println("Pessoa removida com sucesso!");

	}

	private static void atualizarPessoas() {
		System.out.println("Atualizando pessoas\n");
		System.out.print("Digite o Id da pessoa a ser atualizada: ");
		int idPessoa = SCR.nextInt();
		SCR.nextLine();
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		Pessoa pessoaAtual = pessoaService.byId(idPessoa);
		if (pessoaAtual != null) {
			System.out.println("Pessoa encontrada");
			System.out.println(String.format("Nome: %s", pessoaAtual.getNome()));
			System.out.println(String.format("Sobrenome: %s", pessoaAtual.getSobrenome()));
			System.out.println(String.format("Idade: %d", pessoaAtual.getIdade()));
			SCR.nextLine();
			System.out.println("Novo nome: ");
			pessoaAtual.setNome(SCR.nextLine());
			System.out.println("Novo Sobrenome");
			pessoaAtual.setSobrenome(SCR.nextLine());
			System.out.println("Nova idade; ");
			pessoaAtual.setIdade(SCR.nextInt());
			pessoaService.update(pessoaAtual);
		} else {
			System.out.println("Não existem pessoas com esse Id.");
		}

	}

	private static void inserirPessoa() {
		System.out.println("\nInclusão de pessoas");

		Pessoa novaPessoa = new Pessoa();

		System.out.print("Nome: ");
		novaPessoa.setNome(SCR.nextLine());
		System.out.print("Sobrenome: ");
		novaPessoa.setSobrenome(SCR.nextLine());
		System.out.print("Idade: ");
		novaPessoa.setIdade(SCR.nextInt());

		CrudService<Pessoa, Integer> pessoaService = new PessoaService();
		pessoaService.inserir(novaPessoa);
		System.out.println("Pessoa inserida com sucesso!");

	}

	private static void listarPessoas() {
		CrudService<Pessoa, Integer> pessoaService = new PessoaService();

		System.out.println("Gerenciamento de Pessoas");
		System.out.println("Lista de pessoas cadastradas");

		try {
			List<Pessoa> pessoas = pessoaService.all();
			pessoas.forEach(p -> {
				System.out.println(
						String.format("(%d) %s %s %d ", p.getId(), p.getNome(), p.getSobrenome(), p.getIdade()));
			});
			if (pessoas.isEmpty()) {
				System.out.println("Não existem pessoas cadastradas");
			}
		} catch (Exception e) {
			System.out.println("Houve um erro ao utilizar a JPA. " + e.getMessage());
		}
	}
}
