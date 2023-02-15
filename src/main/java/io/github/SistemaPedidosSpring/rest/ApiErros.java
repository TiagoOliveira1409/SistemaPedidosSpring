package io.github.SistemaPedidosSpring.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErros {

    @Getter
    List<String> erros;

    public ApiErros(String MsgErro){
        this.erros = Arrays.asList(MsgErro);
    }

    public ApiErros(List<String> MsgList){
        this.erros = MsgList;
    }
}
