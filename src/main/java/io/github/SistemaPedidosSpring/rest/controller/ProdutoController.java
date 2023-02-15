package io.github.SistemaPedidosSpring.rest.controller;

import io.github.SistemaPedidosSpring.domain.entity.Produto;
import io.github.SistemaPedidosSpring.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {

    Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @GetMapping(value = "{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return produtos
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(
                                NOT_FOUND,
                                "Produto Não encontrado.")
                );
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto saveProdutoByDescricao(@RequestBody @Valid Produto produto){
        return produtos.save(produto);
    }

    @DeleteMapping(value="{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtos
                .findById(id)
                .map(produto ->{
                    produtos.delete(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        "Produto Não encontrado."));

    }

    @PutMapping(value = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody @Valid Produto produto){

        produtos
                .findById(id)
                .map(produtoAntigo ->{
                    produto.setId(produtoAntigo.getId());
                    produtos.save(produto);
                    return produtoAntigo;
                }).orElseThrow(() ->
                        new ResponseStatusException(
                                NOT_FOUND,
                                "Produto Não encontrado.")
                );
    }

    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,matcher);
        return produtos.findAll(example);
    }
}
