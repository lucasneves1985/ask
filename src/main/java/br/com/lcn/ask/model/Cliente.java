package br.com.lcn.ask.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cliente {
    private Integer id;
    private String name;
    private String endereco;

}
