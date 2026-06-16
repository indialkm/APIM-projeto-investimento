package com.investimento.app.controller;
import com.investimento.app.dto.request.AtivoRequestDTO;
import com.investimento.app.dto.response.AtivoResponseDTO;
import com.investimento.app.model.Ativo;
import com.investimento.app.service.AtivoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/ativos")
@RequiredArgsConstructor
public class AtivoController {

    private final AtivoService ativoService;
    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<AtivoResponseDTO> criar(@RequestBody @Valid AtivoRequestDTO requestDTO) {
       
        Ativo ativo = ativoService.criar(requestDTO);
        AtivoResponseDTO response = mapper.map(ativo, AtivoResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivoResponseDTO> buscarPorId(@PathVariable Long id) {
        Ativo ativo = ativoService.buscarPorId(id);
        AtivoResponseDTO response = mapper.map(ativo, AtivoResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/ticker/{ticker}")
    public ResponseEntity<AtivoResponseDTO> buscarPorTicker(@PathVariable String ticker) {
        Ativo ativo = ativoService.buscarPorTicker(ticker);
        AtivoResponseDTO response = mapper.map(ativo, AtivoResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<AtivoResponseDTO>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Ativo> ativosPage = ativoService.listarTodos(pageable);
        Page<AtivoResponseDTO> responsePage = ativosPage.map(ativo -> mapper.map(ativo, AtivoResponseDTO.class));
        return ResponseEntity.ok(responsePage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ativoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        ativoService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        ativoService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}