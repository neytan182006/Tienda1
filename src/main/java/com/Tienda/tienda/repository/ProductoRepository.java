package com.tienda.tienda.repository;

import com.tienda.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();
    
    // Clase 8 
    //Consulta derivada que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioInf, double precioSup);

    //Consulta JPQL que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    @Query(value = "SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL (@Param ("precioInf")double precioInf, double precioSup);

    //Consulta SQL que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    @Query(nativeQuery = true,
            value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(@Param ("precioInf")double precioInf, double precioSup);
        //Práctica #2 - Consulta ampliada: extiende la consulta por rango de precio
    //agregando JOIN con categoría (evita N+1), filtro de activos y doble orden
    @Query(value = "SELECT p FROM Producto p JOIN FETCH p.categoria c "
            + "WHERE p.precio BETWEEN :precioInf AND :precioSup "
            + "AND p.activo = true "
            + "ORDER BY c.descripcion ASC, p.precio ASC")
    public List<Producto> consultaAmpliada(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
}
