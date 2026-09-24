package com.Proyecto.ProyectoSematext.DTO.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Envoltorio estándar de respuesta JSON para los endpoints de la API.
 *
 * Toda respuesta debe tener la forma { success, data, error }: en caso de éxito
 * "data" contiene el resultado y "error" es null; en caso de error "data" es null
 * y "error" contiene un mensaje seguro para el cliente (nunca detalles internos
 * como stack traces o mensajes de SQL/JPA).
 *
 * @param <T> tipo del contenido retornado en caso de éxito.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T>
{
    private boolean success;
    private T data;
    private String error;

    public static <T> ApiResponse<T> ok(T data)
    {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> fail(String error)
    {
        return new ApiResponse<>(false, null, error);
    }
}
