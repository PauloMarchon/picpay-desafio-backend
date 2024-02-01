package com.paulomarchon.picpay.transferencia;

import com.paulomarchon.picpay.transferencia.payload.RequisicaoDeTransferenciaDto;
import com.paulomarchon.picpay.transferencia.payload.TransferenciaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transferencia")
@Tag(name = "Transferencia")
public class TransferenciaController {
    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @Operation(
            description = "Endpoint POST para realizar transferencia entre usuarios",
            summary = "Realiza transferencia entre usuarios",
            responses = {
                @ApiResponse(responseCode = "200", description = "Transferencia realizada com sucesso"),
                @ApiResponse(responseCode = "400", description = "Erro ao tentar realizar a transferencia")
            }
    )
    @PostMapping
    public ResponseEntity<TransferenciaResponse> realizaTransferencia(@RequestBody @Valid RequisicaoDeTransferenciaDto requisicaoTransferencia){
        TransferenciaResponse response = transferenciaService.realizarTransferencia(requisicaoTransferencia);

        if(response.status() == TransferenciaStatus.FALHA) {
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        return ResponseEntity.ok()
                .body(response);
    }
}
