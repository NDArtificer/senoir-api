package br.com.senior.domain.enums;

public enum Type {

	PRODUTO("produto"), SERVICO("Servico");

	private String descricao;

	Type(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
