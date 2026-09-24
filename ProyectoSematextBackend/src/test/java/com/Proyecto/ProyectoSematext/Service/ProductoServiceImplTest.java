package com.Proyecto.ProyectoSematext.Service;

import com.Proyecto.ProyectoSematext.DTO.DTOProducto;
import com.Proyecto.ProyectoSematext.Entity.CategoriaEntity;
import com.Proyecto.ProyectoSematext.Entity.ProductoEntity;
import com.Proyecto.ProyectoSematext.Entity.UnidadMedidaEntity;
import com.Proyecto.ProyectoSematext.Repository.RepositorioCategoria;
import com.Proyecto.ProyectoSematext.Repository.RepositorioProducto;
import com.Proyecto.ProyectoSematext.Repository.RepositorioUnidadMedida;
import com.Proyecto.ProyectoSematext.Service.Impl.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private RepositorioProducto repositorioProducto;

    @Mock
    private RepositorioCategoria repositorioCategoria;

    @Mock
    private RepositorioUnidadMedida repositorioUnidadMedida;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private DTOProducto dtoValido() {
        DTOProducto dto = new DTOProducto();
        dto.setNombreProducto("Tornillo");
        dto.setDescripcion("Tornillo de acero");
        dto.setIdcategoria(1);
        dto.setIdunidadmedida(2);
        return dto;
    }

    private void assertBadRequest(DTOProducto dto, String mensajeEsperado) {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productoService.registrarProducto(dto));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals(mensajeEsperado, ex.getReason());
        verifyNoInteractions(repositorioProducto, repositorioCategoria, repositorioUnidadMedida);
    }

    @Test
    void registrarProducto_conCuerpoNulo_lanzaBadRequest() {
        assertBadRequest(null, "Los datos del producto son obligatorios");
    }

    @Test
    void registrarProducto_conNombreNulo_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setNombreProducto(null);
        assertBadRequest(dto, "El nombre del producto es obligatorio");
    }

    @Test
    void registrarProducto_conNombreVacio_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setNombreProducto("");
        assertBadRequest(dto, "El nombre del producto es obligatorio");
    }

    @Test
    void registrarProducto_conNombreSoloEspacios_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setNombreProducto("   ");
        assertBadRequest(dto, "El nombre del producto es obligatorio");
    }

    @Test
    void registrarProducto_conNombreDemasiadoLargo_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setNombreProducto("a".repeat(256));
        assertBadRequest(dto, "El nombre del producto no puede superar los 255 caracteres");
    }

    @Test
    void registrarProducto_conDescripcionNula_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setDescripcion(null);
        assertBadRequest(dto, "La descripcion del producto es obligatoria");
    }

    @Test
    void registrarProducto_conDescripcionVacia_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setDescripcion("");
        assertBadRequest(dto, "La descripcion del producto es obligatoria");
    }

    @Test
    void registrarProducto_conDescripcionSoloEspacios_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setDescripcion("   ");
        assertBadRequest(dto, "La descripcion del producto es obligatoria");
    }

    @Test
    void registrarProducto_conDescripcionDemasiadoLarga_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setDescripcion("a".repeat(256));
        assertBadRequest(dto, "La descripcion del producto no puede superar los 255 caracteres");
    }

    @Test
    void registrarProducto_sinCategoria_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setIdcategoria(null);
        assertBadRequest(dto, "La categoria es obligatoria");
    }

    @Test
    void registrarProducto_sinUnidadMedida_lanzaBadRequest() {
        DTOProducto dto = dtoValido();
        dto.setIdunidadmedida(null);
        assertBadRequest(dto, "La unidad de medida es obligatoria");
    }

    @Test
    void registrarProducto_conCategoriaInexistente_lanzaBadRequest() {
        when(repositorioCategoria.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productoService.registrarProducto(dtoValido()));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Categoria no encontrada", ex.getReason());
        verifyNoInteractions(repositorioProducto);
    }

    @Test
    void registrarProducto_conUnidadMedidaInexistente_lanzaBadRequest() {
        when(repositorioCategoria.findById(1)).thenReturn(Optional.of(new CategoriaEntity()));
        when(repositorioUnidadMedida.findById(2)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> productoService.registrarProducto(dtoValido()));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Unidad de medida no encontrada", ex.getReason());
        verifyNoInteractions(repositorioProducto);
    }

    @Test
    void registrarProducto_conDatosValidos_guardaYRetornaProducto() {
        when(repositorioCategoria.findById(1)).thenReturn(Optional.of(new CategoriaEntity()));
        when(repositorioUnidadMedida.findById(2)).thenReturn(Optional.of(new UnidadMedidaEntity()));
        when(repositorioProducto.save(any(ProductoEntity.class))).thenAnswer(inv -> {
            ProductoEntity p = inv.getArgument(0);
            p.setIdproducto(10);
            return p;
        });

        DTOProducto resultado = productoService.registrarProducto(dtoValido());

        assertEquals(10, resultado.getIdproducto());
        assertEquals("Tornillo", resultado.getNombreProducto());
        assertEquals("Tornillo de acero", resultado.getDescripcion());
        verify(repositorioProducto).save(any(ProductoEntity.class));
    }
}
