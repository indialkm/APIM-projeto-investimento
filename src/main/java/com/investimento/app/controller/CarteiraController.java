package com.investimento.app.controller;


import com.investimento.app.dto.request.CarteiraAtivoRequestDTO;
import com.investimento.app.dto.request.CarteiraRequestDTO;
import com.investimento.app.dto.response.CarteiraAtivoResponseDTO;
import com.investimento.app.dto.response.CarteiraResponseDTO;
import com.investimento.app.dto.update.CarteiraUpdateDTO;
import com.investimento.app.model.Carteira;
import com.investimento.app.model.CarteiraAtivo;
import com.investimento.app.service.CarteiraAtivoService;
import com.investimento.app.service.CarteiraService;
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
@RequestMapping("/api/carteiras")
@RequiredArgsConstructor
public class CarteiraController {

    private final CarteiraService carteiraService;
    private final ModelMapper mapper;
    private final CarteiraAtivoService carteiraAtivoService;

    @PostMapping
    public ResponseEntity<CarteiraResponseDTO> criar(@RequestBody @Valid CarteiraRequestDTO requestDTO) {
      
        Carteira carteira = carteiraService.criar(requestDTO);
        CarteiraResponseDTO response = mapper.map(carteira, CarteiraResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarteiraResponseDTO> buscarPorId(@PathVariable Long id) {
        Carteira carteira = carteiraService.buscarPorId(id);
        CarteiraResponseDTO response = mapper.map(carteira, CarteiraResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<CarteiraResponseDTO>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        Page<Carteira> carteirasPage = carteiraService.listarTodos(pageable);
        
       
        Page<CarteiraResponseDTO> responsePage = carteirasPage.map(carteira -> mapper.map(carteira, CarteiraResponseDTO.class));
        
        return ResponseEntity.ok(responsePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarteiraResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CarteiraUpdateDTO updateDTO) {
        Carteira carteiraAtualizada = carteiraService.atualizar(id, updateDTO);
        CarteiraResponseDTO response = mapper.map(carteiraAtualizada, CarteiraResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carteiraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        carteiraService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        carteiraService.desativar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{carteiraId}/ativos")
    public ResponseEntity<CarteiraAtivoResponseDTO> adicionarAtivo(
            @PathVariable Long carteiraId, 
            @RequestBody @Valid CarteiraAtivoRequestDTO requestDTO) {
        
      
        requestDTO.setCarteiraId(carteiraId);
        
        CarteiraAtivo resultado = carteiraAtivoService.adicionarAtivo(requestDTO);
        CarteiraAtivoResponseDTO response = mapper.map(resultado, CarteiraAtivoResponseDTO.class);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
