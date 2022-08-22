package br.com.ccs.sicred.domain.entity;

import lombok.Getter;

@Getter
public class Eleitor {

    private String status;
    private String cpf;
    boolean ableToVote = false;

    public void setStatus(String status) {
        this.status = status;

        if (this.status.equals("ABLE_TO_VOTE")) {
            ableToVote = true;
        }
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        formatarCpf();
    }

    private void formatarCpf() {

        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        cpf = cpf.trim();
    }
}
