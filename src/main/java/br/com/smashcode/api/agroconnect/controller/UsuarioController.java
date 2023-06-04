package br.com.smashcode.api.agroconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.service.usuario.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    PagedResourcesAssembler<Usuario> assembler;


    @GetMapping
    public PagedModel<EntityModel<Usuario>> search(@PageableDefault(size=5) Pageable pageable, @RequestParam(required = false) String search) {
        Page<Usuario> page = usuarioService.searchAll(pageable, search);
        return assembler.toModel(page);
    }

    @PostMapping("signup")
    public ResponseEntity<EntityModel<Usuario>> create(@RequestBody @Valid Usuario usuario, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Usuario>> show(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.findByIdOrElseThrowBadRequestExcepetion(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Usuario>> update(@PathVariable String id, @RequestBody @Valid Usuario usuario,  BindingResult result) {
        return ResponseEntity.ok(usuarioService.updateByIdOrElseThrowBadRequestException(id, usuario));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        usuarioService.deleteByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }

}
