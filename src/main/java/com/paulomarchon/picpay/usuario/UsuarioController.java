package com.paulomarchon.picpay.usuario;

import com.paulomarchon.picpay.usuario.payload.CadastroUsuarioDto;
import com.paulomarchon.picpay.usuario.payload.UsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario")
@Tag(name = "Usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            description = "Endpoint GET referente a API de usuarios",
            summary = "Retorna todos os usuarios cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso ao buscar todos os usuarios"),
                    @ApiResponse(responseCode = "404", description = "Erro ao buscar usuarios, recurso nao encontrado")
            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> buscarTodosUsuarios() {
        List<UsuarioDto> usuarios = usuarioService.buscarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
            description = "Endpoint POST referente a API de usuarios",
            summary = "Realiza o cadastro um usuario no sistema, caso seja valido, e retorna o usuario cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sucesso ao cadastrar novo usuario"),
                    @ApiResponse(responseCode = "409", description = "Dados informados ja foram cadastrado no sistema"),
                    @ApiResponse(responseCode = "400", description = "Os Dados informados possuem formato invalido")
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioDto> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDto cadastroUsuarioDto) {
        UsuarioDto novoUsuario = usuarioService.cadastrarUsuario(cadastroUsuarioDto);

        URI uriNovoUsuario = UriComponentsBuilder.fromPath("usuario/{identidade}").buildAndExpand(novoUsuario.identidade()).toUri();

        return ResponseEntity.created(uriNovoUsuario)
                .body(novoUsuario);
    }
}
